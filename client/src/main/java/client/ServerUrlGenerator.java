package client;

import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class ServerUrlGenerator
{
    private static final String HTTP = "http://";
    private ServerProperties serverProperties;

    public ServerUrlGenerator(ServerProperties serverProperties)
    {
        this.serverProperties = serverProperties;
    }

    public String getOpenGames()
    {
        return MessageFormat.format("{0}{1}:{2}/games", HTTP, serverProperties.getIp(), serverProperties.getPort());
    }

    public String getPlayers()
    {
        return MessageFormat.format("{0}{1}:{2}/players", HTTP, serverProperties.getIp(), serverProperties.getPort());
    }

    public String registerForGame()
    {
        return MessageFormat.format("{0}{1}:{2}/game/register/player", HTTP, serverProperties.getIp(), serverProperties.getPort());
    }

    public String getGameRound(String gameName)
    {
        return MessageFormat.format("{0}{1}:{2}/game/state/{3}", HTTP, serverProperties.getIp(), serverProperties.getPort(), gameName);
    }

    public String createGame()
    {
        return MessageFormat.format("{0}{1}:{2}/game/create", HTTP, serverProperties.getIp(), serverProperties.getPort());
    }

    public String createPlayer()
    {
        return MessageFormat.format("{0}{1}:{2}/player/create", HTTP, serverProperties.getIp(), serverProperties.getPort());
    }
}
