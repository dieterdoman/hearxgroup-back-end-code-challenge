package server.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import server.dao.PlayerDao;
import server.entities.Player;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerServiceTest
{
    private PlayerService playerService;
    private PlayerDao playerDao;

    @BeforeEach
    void setupTests()
    {
        playerDao = Mockito.mock(PlayerDao.class);
        playerService = new PlayerService(playerDao);
    }

    @Test
    void createPlayerSuccessTest()
    {
        Mockito.when(playerDao.findById("dieter")).thenReturn(Optional.empty());
        assertTrue(playerService.createPlayer("dieter"));
    }

    @Test
    void createPlayerFailTest()
    {
        Player player = Mockito.mock(Player.class);
        Mockito.when(player.getName()).thenReturn("dieter");
        Mockito.when(playerDao.findById("dieter")).thenReturn(Optional.of(player));
        assertFalse(playerService.createPlayer("dieter"));
    }
}
