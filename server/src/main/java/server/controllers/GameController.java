package server.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import server.GameProperties;
import server.dao.GameDao;
import server.dao.PlayerDao;
import server.dao.RoundDao;
import server.entities.*;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping
@Slf4j
public class GameController
{
    @Autowired
    private GameProperties gameProperties;
    @Autowired
    private GameDao gameDao;
    @Autowired
    private PlayerDao playerDao;
    @Autowired
    private RoundDao roundDao;

    @PostMapping(value = "/game/create")
    public ResponseEntity<Void> createGame(@RequestBody MultiValueMap<String, String> body)
    {
        String name = body.getFirst("gameName");
        Optional<Game> optionalGame = gameDao.findById(name);
        if (optionalGame.isPresent()) {
            logger.error(MessageFormat.format("{0} already exist", optionalGame.get().getName()));
            return ResponseEntity.badRequest().build();
        }
        Game game = new Game(name);
        game.setRounds(generateNewRounds(game));
        System.out.println(game.getRounds());
        gameDao.saveAndFlush(game);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/games")
    public ResponseEntity<List<String>> listGames()
    {
        return ResponseEntity.ok(gameDao.findAll()
                .stream()
                .filter(game -> game.getGameStatus() == GameStatus.LOBBY)
                .map(Game::getName)
                .collect(Collectors.toList()));
    }

    @PostMapping(value = "/game/register/player")
    public ResponseEntity<Void> registerPlayerToGame(@RequestBody MultiValueMap<String, String> body)
    {
        String gameName = body.getFirst("gameName");
        String playerName = body.getFirst("playerName");
        Optional<Game> gameOptional = gameDao.findById(gameName);
        Optional<Player> playerOptional = playerDao.findById(playerName);

        if(!gameOptional.isPresent()) {
            logger.error("{0} game does not exist.", gameName);
            return ResponseEntity.notFound().build();
        }else if(!playerOptional.isPresent()){
            logger.error("{0} player does not exist.", playerName);
            return ResponseEntity.notFound().build();
        } else {
            Game game = gameOptional.get();
            Player player = playerOptional.get();
            if (game.getPlayers().size() < gameProperties.getPlayers() && game.getGameStatus() == GameStatus.LOBBY) {
                if (!game.getPlayers().contains(player))
                {
                    game.addPlayer(player);
                    gameDao.saveAndFlush(game);
                    return ResponseEntity.ok().build();
                }
            }
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "/game/state/{gameName}")
    public ResponseEntity<Set<Round>> getGameState(@PathVariable String gameName)
    {
        Optional<Game> optionalGame = gameDao.findById(gameName);
        return optionalGame.map(game -> ResponseEntity.ok(game.getRounds()))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    private Set<Round> generateNewRounds(Game game)
    {
        Set<Round> rounds = new HashSet<>();
        for (int i = 0; i < gameProperties.getRounds() - 1; i++)
        {
            Round round = new Round();
            round.setRoundNumber(i);
            round.setNumber1(new Random().nextInt(9) + 1);
            round.setNumber2(new Random().nextInt(9) + 1);
            round.setRoundStatus(GameStatus.BUSY);
            round.setGame(game);
            rounds.add(round);
        }

        Round round = new Round();
        round.setNumber1(new Random().nextInt(9) + 1);
        round.setNumber2(new Random().nextInt(9) + 1);
        round.setRoundStatus(GameStatus.FINISHED);
        round.setGame(game);
        round.setRoundNumber(gameProperties.getRounds() - 1);
        rounds.add(round);
        return rounds;
    }
}
