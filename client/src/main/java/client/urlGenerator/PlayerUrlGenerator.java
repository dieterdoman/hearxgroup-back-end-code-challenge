package client.urlGenerator;

import client.ServerProperties;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class PlayerUrlGenerator extends ServerUrlGenerator
{
    private static final String PLAYER = "player";

    public PlayerUrlGenerator(ServerProperties serverProperties)
    {
        super(serverProperties);
    }

    public String getPlayers()
    {
        return MessageFormat.format("{0}{1}:{2}/{3}s",
                HTTP,
                serverProperties.getIp(),
                serverProperties.getPort(),
                PLAYER);
    }

    public String createPlayer()
    {
        return MessageFormat.format("{0}{1}:{2}/{3}/create",
                HTTP,
                serverProperties.getIp(),
                serverProperties.getPort(),
                PLAYER);
    }
}
