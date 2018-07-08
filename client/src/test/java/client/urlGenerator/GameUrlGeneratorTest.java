package client.urlGenerator;

import client.ServerProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameUrlGeneratorTest
{
    private GameUrlGenerator gameUrlGenerator;

    @BeforeEach
    void setupTest()
    {
        ServerProperties serverProperties = Mockito.mock(ServerProperties.class);
        Mockito.when(serverProperties.getPort()).thenReturn("8080");
        Mockito.when(serverProperties.getIp()).thenReturn("localhost");
        gameUrlGenerator = new GameUrlGenerator(serverProperties);
    }

    @Test
    void urlGeneratorTest()
    {
        assertEquals("http://localhost:8080/games", gameUrlGenerator.getOpenGames());
        assertEquals("http://localhost:8080/game/create", gameUrlGenerator.createGame());
        assertEquals("http://localhost:8080/game/save/score", gameUrlGenerator.postScore());
        assertEquals("http://localhost:8080/game/state/game1", gameUrlGenerator.getGameRounds("game1"));
        assertEquals("http://localhost:8080/game/game1/score", gameUrlGenerator.getLeaderboard("game1"));
        assertEquals("http://localhost:8080/game/register/player", gameUrlGenerator.registerForGame());
    }
}
