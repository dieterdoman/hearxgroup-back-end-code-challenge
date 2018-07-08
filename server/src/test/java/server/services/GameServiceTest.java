package server.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import server.GameProperties;
import server.dao.GameDao;
import server.entities.Game;
import server.entities.GameStatus;
import server.entities.Player;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

public class GameServiceTest
{
    private GameService gameService;
    private GameDao gameDao;
    private Game game;
    private Player player;

    @BeforeEach
    void setupTests()
    {
        gameDao = Mockito.mock(GameDao.class);
        RoundService roundService = Mockito.mock(RoundService.class);
        Mockito.when(roundService.generateNewRounds(any(Game.class))).thenReturn(Collections.emptySet());
        GameProperties gameProperties = Mockito.mock(GameProperties.class);
        Mockito.when(gameProperties.getPlayers()).thenReturn(2);
        gameService = new GameService(gameDao, roundService, gameProperties);
        game = Mockito.mock(Game.class);
        player = Mockito.mock(Player.class);
        Mockito.when(game.getPlayers()).thenReturn(Collections.emptySet());
    }

    @Test
    void createGameSuccessTest()
    {
        Mockito.when(gameDao.findById("game")).thenReturn(Optional.empty());
        assertTrue(gameService.createGame("game"));
    }

    @Test
    void createGameFailTest()
    {
        Mockito.when(game.getName()).thenReturn("game");
        Mockito.when(gameDao.findById("game")).thenReturn(Optional.of(game));
        assertFalse(gameService.createGame("game"));
    }

    @Test
    void getGamesByStatusTest()
    {
        Mockito.when(gameDao.findAll()).thenReturn(Collections.singletonList(game));
        Mockito.when(game.getGameStatus()).thenReturn(GameStatus.LOBBY);
        Mockito.when(game.getName()).thenReturn("game");
        assertEquals(Collections.singletonList("game"), gameService.getGamesByStatus(GameStatus.LOBBY));
    }

    @Test
    void registerPlayerToGameSuccessTest()
    {
        Mockito.when(game.getGameStatus()).thenReturn(GameStatus.LOBBY);
        assertTrue(gameService.registerPlayerToGame(player, game));
    }

    @Test
    void registerPlayerToGameFailTest()
    {
        Mockito.when(game.getGameStatus()).thenReturn(GameStatus.BUSY);
        assertFalse(gameService.registerPlayerToGame(player, game));
    }

}
