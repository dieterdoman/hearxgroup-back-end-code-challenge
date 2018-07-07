package client;

import com.google.common.base.Stopwatch;
import jline.console.ConsoleReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@ShellComponent
@ShellCommandGroup("game")
public class ClientCli
{
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ServerUrlGenerator serverUrlGenerator;

    @ShellMethod("list open games")
    public void listOpenGames() throws IOException
    {
        List gamesNames = restTemplate.getForObject(serverUrlGenerator.getOpenGames(), List.class);
        System.out.println("Open Games: ");
        for (Object gameName: gamesNames) {
            System.out.println(gameName);
        }
    }

    @ShellMethod("create game")
    public void createGame(String gameName)
    {
        MultiValueMap<String, String> parts = new LinkedMultiValueMap<>();
        parts.add("gameName", gameName);
        try {
            restTemplate.postForLocation(serverUrlGenerator.createGame(), parts, String.class);
            System.out.println("Successfully created game.");
        }
        catch (HttpClientErrorException e)
        {
            if(e.getRawStatusCode() == HttpStatus.BAD_REQUEST.value())
            {
                System.out.println("Already registered that game");
            }
        }
    }

    @ShellMethod("create player")
    public void createPlayer(String playerName)
    {
        MultiValueMap<String, String> parts = new LinkedMultiValueMap<>();
        parts.add("playerName", playerName);
        try {
            restTemplate.postForLocation(serverUrlGenerator.createPlayer(), parts, String.class);
            System.out.println("Successfully created a player");
        }
        catch (HttpClientErrorException e)
        {
            if(e.getRawStatusCode() == HttpStatus.BAD_REQUEST.value())
            {
                System.out.println("Already registered that player");
            }
        }
    }

    @ShellMethod("list players")
    public void listPlayers()
    {
        List gamesNames = restTemplate.getForObject(serverUrlGenerator.getPlayers(), List.class);
        System.out.println("Players: ");
        for (Object gameName: gamesNames) {
            System.out.println(gameName);
        }
    }

    @ShellMethod("join game lobby")
    public void joinGameLobby(String gameName, String playerName) throws IOException
    {
        MultiValueMap<String, String> parts = new LinkedMultiValueMap<>();
        parts.add("gameName", gameName);
        parts.add("playerName", playerName);

        try {
            restTemplate.postForEntity(serverUrlGenerator.registerForGame(), parts, String.class);
        }
        catch (HttpClientErrorException e)
        {
            if(e.getRawStatusCode() == HttpStatus.BAD_REQUEST.value())
            {
                System.out.println("Already registered");
            } else {
                System.out.println("Player or game not found.");
                return;
            }
        }
        List<LinkedHashMap<String, Object>> rounds = restTemplate.getForObject(serverUrlGenerator.getGameRound(gameName), List.class);
        for (LinkedHashMap<String, Object> gameRound: rounds) {
            GameRoundResult gameRoundResult = getPassword(Integer.parseInt(gameRound.get("number1").toString()), Integer.parseInt(gameRound.get("number2").toString()));

            System.out.println(gameRoundResult.getTime());
        }
    }

    private GameRoundResult getPassword(Integer number1, Integer number2) throws IOException
    {
        ConsoleReader cr = new ConsoleReader();
        cr.setPrompt(MessageFormat.format("{0} x {1} = ", number1, number2));
        Stopwatch stopwatch = Stopwatch.createStarted();
        int answer = Integer.parseInt(cr.readLine());
        stopwatch.stop();
        GameRoundResult gameRoundResult = new GameRoundResult();
        gameRoundResult.setAnswer(answer);
        gameRoundResult.setTime((int) (stopwatch.elapsed(MILLISECONDS) / 1000) % 60 );
        return gameRoundResult;
    }
}
