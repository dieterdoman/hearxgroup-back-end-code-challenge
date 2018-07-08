package server.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import server.dao.GameDao;
import server.dao.PlayerDao;
import server.entities.*;
import server.services.GameService;
import server.services.ScoreService;

import java.util.*;

@RestController
@RequestMapping
@Slf4j
public class GameController
{
    @Autowired
    private GameService gameService;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private GameDao gameDao;
    @Autowired
    private PlayerDao playerDao;

    @PostMapping(value = "/game/create")
    public ResponseEntity<Void> createGame(@RequestBody MultiValueMap<String, String> body)
    {
        String name = body.getFirst("gameName");
        if (gameService.createGame(name)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @RequestMapping(value = "/games")
    public ResponseEntity<List<String>> listGames()
    {
        return ResponseEntity.ok(gameService.getGamesByStatus(GameStatus.LOBBY));
    }

    @PostMapping(value = "/game/register/player")
    public ResponseEntity<Void> registerPlayerToGame(@RequestBody MultiValueMap<String, String> body)
    {
        String gameName = body.getFirst("gameName");
        String playerName = body.getFirst("playerName");
        Optional<Game> gameOptional = gameDao.findById(gameName);
        Optional<Player> playerOptional = playerDao.findById(playerName);

        if (!gameOptional.isPresent()) {
            logger.error("{0} game does not exist.", gameName);
            return ResponseEntity.notFound().build();
        } else if (!playerOptional.isPresent()) {
            logger.error("{0} player does not exist.", playerName);
            return ResponseEntity.notFound().build();
        } else {
            if (gameService.registerPlayerToGame(playerOptional.get(), gameOptional.get())) {
                return ResponseEntity.ok().build();
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

    @PostMapping(value = "/game/save/score")
    public ResponseEntity<Void> saveScore(@RequestBody MultiValueMap<String, String> body)
    {
        String gameName = body.getFirst("gameName");
        String playerName = body.getFirst("playerName");
        Optional<Game> gameOptional = gameDao.findById(gameName);
        Optional<Player> playerOptional = playerDao.findById(playerName);

        if (!gameOptional.isPresent()) {
            logger.error("{0} game does not exist.", gameName);
            return ResponseEntity.notFound().build();
        } else if (!playerOptional.isPresent()) {
            logger.error("{0} player does not exist.", playerName);
            return ResponseEntity.notFound().build();
        } else {
            if (scoreService.saveScore(Integer.parseInt(body.getFirst("time")), Integer.parseInt(body.getFirst("answer")), Integer.parseInt(body.getFirst("round")), playerOptional.get(), gameOptional.get())) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping("/game/{gameName}/score")
    public ResponseEntity<List<Score>> getScoresOfGame(@PathVariable String gameName)
    {
        Optional<Game> gameOptional = gameDao.findById(gameName);
        return gameOptional.map(game -> ResponseEntity.ok(scoreService.getScoresForGame(game))).orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
