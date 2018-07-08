package client.urlGenerator;

import client.ServerProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerUrlGeneratorTest
{
    private PlayerUrlGenerator playerUrlGenerator;

    @BeforeEach
    void setupTests()
    {
        ServerProperties serverProperties = Mockito.mock(ServerProperties.class);
        Mockito.when(serverProperties.getPort()).thenReturn("8080");
        Mockito.when(serverProperties.getIp()).thenReturn("localhost");
        playerUrlGenerator = new PlayerUrlGenerator(serverProperties);
    }

    @Test
    void urlGeneratorTest()
    {
        assertEquals("http://localhost:8080/player/create", playerUrlGenerator.createPlayer());
        assertEquals("http://localhost:8080/players", playerUrlGenerator.getPlayers());
    }
}
