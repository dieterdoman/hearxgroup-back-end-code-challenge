package client.urlGenerator;

import client.ServerProperties;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class GameUrlGenerator extends ServerUrlGenerator
{
    private static final String GAME = "game";

    public GameUrlGenerator(ServerProperties serverProperties)
    {
        super(serverProperties);
    }

    public String getOpenGames()
    {
        return MessageFormat.format("{0}{1}:{2}/{3}s",
                HTTP,
                serverProperties.getIp(),
                serverProperties.getPort(),
                GAME);
    }

    public String registerForGame()
    {
        return MessageFormat.format("{0}{1}:{2}/{3}/register/player",
                HTTP, serverProperties.getIp(),
                serverProperties.getPort(),
                GAME);
    }

    public String getGameRounds(String gameName)
    {
        return MessageFormat.format("{0}{1}:{2}/{3}/state/{4}",
                HTTP, serverProperties.getIp(),
                serverProperties.getPort(),
                GAME,
                gameName);
    }

    public String createGame()
    {
        return MessageFormat.format("{0}{1}:{2}/{3}/create",
                HTTP, serverProperties.getIp(),
                serverProperties.getPort(),
                GAME);
    }

    public String postScore()
    {
        return MessageFormat.format("{0}{1}:{2}/{3}/save/score",
                HTTP,
                serverProperties.getIp(),
                serverProperties.getPort(),
                GAME);
    }

    public String getLeaderboard(String gameName)
    {
        return MessageFormat.format("{0}{1}:{2}/{3}/{4}/score",
                HTTP,
                serverProperties.getIp(),
                serverProperties.getPort(),
                GAME,
                gameName);
    }
}
