package client;

import client.urlGenerator.GameUrlGenerator;
import client.urlGenerator.PlayerUrlGenerator;
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
import java.util.List;

@ShellComponent
@ShellCommandGroup("game")
public class ClientCli
{
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private PlayerUrlGenerator playerUrlGenerator;
    @Autowired
    private GameUrlGenerator gameUrlGenerator;
    @Autowired
    private GameService gameService;
    @Autowired
    private OutputLeaderboard outputLeaderboard;

    @ShellMethod("list open games")
    public void listOpenGames()
    {
        gameService.listOpenGames().forEach(System.out::println);
    }

    @ShellMethod("create game")
    public void createGame(String gameName)
    {
        MultiValueMap<String, String> parts = new LinkedMultiValueMap<>();
        parts.add("gameName", gameName);
        try {
            restTemplate.postForLocation(gameUrlGenerator.createGame(), parts, String.class);
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
            restTemplate.postForLocation(playerUrlGenerator.createPlayer(), parts, String.class);
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
        List gamesNames = restTemplate.getForObject(playerUrlGenerator.getPlayers(), List.class);
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
            restTemplate.postForEntity(gameUrlGenerator.registerForGame(), parts, String.class);
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
        gameService.runGame(gameName, playerName);
    }

    @ShellMethod("view game result")
    public void viewGameResult(String gameName)
    {
        outputLeaderboard
                .outputLeaderboard(restTemplate.getForObject(gameUrlGenerator.getLeaderboard(gameName), List.class))
                .forEach(System.out::println);
    }
}
