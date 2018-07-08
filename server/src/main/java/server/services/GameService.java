package server.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import server.GameProperties;
import server.dao.GameDao;
import server.entities.*;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GameService
{
    private GameDao gameDao;
    private RoundService roundService;
    private GameProperties gameProperties;

    public GameService(GameDao gameDao, RoundService roundService, GameProperties gameProperties)
    {
        this.gameDao = gameDao;
        this.roundService = roundService;
        this.gameProperties = gameProperties;
    }

    public boolean createGame(String name)
    {
        Optional<Game> optionalGame = gameDao.findById(name);
        if (optionalGame.isPresent()) {
            logger.error(MessageFormat.format("{0} already exist", optionalGame.get().getName()));
            return false;
        }
        Game game = new Game(name);
        game.setRounds(roundService.generateNewRounds(game));
        gameDao.saveAndFlush(game);
        return true;
    }

    public List<String> getGamesByStatus(GameStatus gameStatus)
    {
        return gameDao.findAll()
                .stream()
                .filter(game -> game.getGameStatus() == gameStatus)
                .map(Game::getName)
                .collect(Collectors.toList());
    }

    public boolean registerPlayerToGame(Player player, Game game)
    {
        if (game.getPlayers().size() < gameProperties.getPlayers() && game.getGameStatus() == GameStatus.LOBBY) {
            if (!game.getPlayers().contains(player)) {
                game.addPlayer(player);
                gameDao.saveAndFlush(game);
                return true;
            }
        }
        return false;
    }
}
