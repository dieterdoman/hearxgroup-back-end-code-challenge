package server.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "/create/{name}")
    public boolean createGame(@PathVariable String name)
    {
        Optional<Game> game = gameDao.findById(name);
        if (game.isPresent()) {
            logger.error(MessageFormat.format("{0} already exist", game.get().getName()));
            return false;
        }
        gameDao.saveAndFlush(new Game(name));
        return true;
    }

    @RequestMapping(value = "/list")
    public List<Game> listGames()
    {
        return gameDao.findAll();
    }

    @RequestMapping(value = "/register/{gameName}/player/{playerName}")
    public boolean registerPlayerToGame(@PathVariable String gameName, @PathVariable String playerName)
    {
        Optional<Game> gameOptional = gameDao.findById(gameName);
        Optional<Player> playerOptional = playerDao.findById(playerName);

        if(!gameOptional.isPresent() || !playerOptional.isPresent()) {
            logger.error("{0} game or {1} player does not exist.", gameName, playerName);
            return false;
        } else {
            Game game = gameOptional.get();
            Player player = playerOptional.get();
            if (game.getPlayers().size() < gameProperties.getPlayers() && game.getGameStatus() == GameStatus.LOBBY) {
                game.addPlayer(player);
            }
            gameDao.saveAndFlush(game);
            return true;
        }
    }
}
