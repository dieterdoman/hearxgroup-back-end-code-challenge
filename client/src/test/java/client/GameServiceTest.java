package client;

import client.urlGenerator.GameUrlGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class GameServiceTest
{
    private GameService gameService;
    private RestTemplate restTemplate;
    private OutputLeaderboard outputLeaderboard;

    @BeforeEach
    void setupTests() throws IOException
    {
        restTemplate = Mockito.mock(RestTemplate.class);
        AnswerInput answerInput = Mockito.mock(AnswerInput.class);
        GameUrlGenerator gameUrlGenerator = Mockito.mock(GameUrlGenerator.class);
        Mockito.when(gameUrlGenerator.getOpenGames()).thenReturn("openGames");
        Mockito.when(gameUrlGenerator.getGameRounds("game")).thenReturn("gameRounds");
        Mockito.when(gameUrlGenerator.postScore()).thenReturn("postScore");
        GameRoundResult gameRoundResult = Mockito.mock(GameRoundResult.class);
        Mockito.when(gameRoundResult.getAnswer()).thenReturn(1);
        Mockito.when(gameRoundResult.getTime()).thenReturn(1);
        Mockito.when(answerInput.getAnswer(any(Integer.class), any(Integer.class))).thenReturn(gameRoundResult);
        outputLeaderboard = Mockito.mock(OutputLeaderboard.class);
        gameService = new GameService(restTemplate, answerInput, gameUrlGenerator, outputLeaderboard);
    }

    @Test
    void runGameTest() throws IOException
    {
        LinkedHashMap<String, Object> rounds = Mockito.mock(LinkedHashMap.class);
        Mockito.when(restTemplate.getForObject("gameRounds", List.class)).thenReturn(Collections.singletonList(rounds));
        Mockito.when(rounds.get("roundNumber")).thenReturn(0);
        Mockito.when(rounds.get("number1")).thenReturn(1);
        Mockito.when(rounds.get("number2")).thenReturn(1);
        Mockito.when(outputLeaderboard.outputLeaderboard(Collections.singletonList(rounds))).thenReturn(Collections.emptyList());
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("gameName", "game");
        parameters.add("playerName", "dieter");
        parameters.add("answer", "1");
        parameters.add("time", "1");
        parameters.add("round", "0");
        gameService.runGame("game", "dieter");
        verify(restTemplate).postForEntity("postScore", parameters, String.class);
    }

    @Test
    void listOpenGamesTest()
    {
        Mockito.when(restTemplate.getForObject("openGames", List.class)).thenReturn(Collections.singletonList("game"));
        assertEquals(Collections.singletonList("game"), gameService.listOpenGames());
    }
}
