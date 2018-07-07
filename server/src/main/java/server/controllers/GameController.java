package server.controllers;

import lombok.extern.slf4j.Slf4j;
import org.cloudifysource.restDoclet.annotations.PossibleResponseStatus;
import org.cloudifysource.restDoclet.annotations.PossibleResponseStatuses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.GameProperties;
import server.dao.GameDao;
import server.dao.PlayerDao;
import server.entities.Game;
import server.entities.GameStatus;
import server.entities.Player;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/game")
@Slf4j
public class GameController
{
    @Autowired
    private GameProperties gameProperties;
    @Autowired
    private GameDao gameDao;
    @Autowired
    private PlayerDao playerDao;

    @PossibleResponseStatuses(responseStatuses = {
            @PossibleResponseStatus(code = 200, description = "Game was successfully created."),
            @PossibleResponseStatus(code = 400, description = "Game already exists with current name.")
    })
    @RequestMapping(value = "/create/{name}")
    public ResponseEntity<Void> createGame(@PathVariable String name)
    {
        Optional<Game> game = gameDao.findById(name);
        if (game.isPresent()) {
            logger.error(MessageFormat.format("{0} already exist", game.get().getName()));
            return ResponseEntity.badRequest().build();
        }
        gameDao.saveAndFlush(new Game(name));
        return ResponseEntity.ok().build();
    }

    @PossibleResponseStatuses(responseStatuses = {
            @PossibleResponseStatus(code = 200, description = "List of game names returned.")
    })
    @RequestMapping(value = "/list")
    public ResponseEntity<List<String>> listGames()
    {
        return ResponseEntity.ok(gameDao.findAll().stream().map(Game::getName).collect(Collectors.toList()));
    }

    @PossibleResponseStatuses(responseStatuses = {
            @PossibleResponseStatus(code = 200, description = "Player successfully registered to game."),
            @PossibleResponseStatus(code = 400, description = "Player already registered for game or game not in lobby state or game is full."),
            @PossibleResponseStatus(code = 404, description = "Player or game does not exist.")
    })
    @RequestMapping(value = "/register/{gameName}/player/{playerName}")
    public ResponseEntity<Void> registerPlayerToGame(@PathVariable String gameName, @PathVariable String playerName)
    {
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
}
