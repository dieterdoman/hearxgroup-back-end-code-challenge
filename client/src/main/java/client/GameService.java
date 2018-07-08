package client;

import client.urlGenerator.GameUrlGenerator;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@Service
public class GameService
{
    private RestTemplate restTemplate;
    private AnswerInput answerInput;
    private GameUrlGenerator gameUrlGenerator;
    private OutputLeaderboard outputLeaderboard;

    public GameService(RestTemplate restTemplate, AnswerInput answerInput, GameUrlGenerator gameUrlGenerator, OutputLeaderboard outputLeaderboard)
    {
        this.restTemplate = restTemplate;
        this.answerInput = answerInput;
        this.gameUrlGenerator = gameUrlGenerator;
        this.outputLeaderboard = outputLeaderboard;
    }

    public void runGame(String gameName, String playerName) throws IOException
    {
        List<LinkedHashMap<String, Object>> rounds = restTemplate.getForObject(gameUrlGenerator.getGameRounds(gameName), List.class);
        rounds.sort(Comparator.comparing(round -> Integer.parseInt(round.get("roundNumber").toString())));
        for (LinkedHashMap<String, Object> gameRound : rounds) {
            GameRoundResult gameRoundResult = answerInput.getAnswer(Integer.parseInt(gameRound.get("number1").toString()), Integer.parseInt(gameRound.get("number2").toString()));
            MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
            parameters.add("gameName", gameName);
            parameters.add("playerName", playerName);
            parameters.add("answer", Integer.toString(gameRoundResult.getAnswer()));
            parameters.add("time", Integer.toString(gameRoundResult.getTime()));
            parameters.add("round", Integer.toString(Integer.parseInt(gameRound.get("roundNumber").toString())));

            restTemplate.postForEntity(gameUrlGenerator.postScore(), parameters, String.class);

            outputLeaderboard
                    .outputLeaderboard(restTemplate.getForObject(gameUrlGenerator.getLeaderboard(gameName), List.class))
                    .forEach(System.out::println);


        }
    }

    public List<String> listOpenGames()
    {
        List gamesNames = restTemplate.getForObject(gameUrlGenerator.getOpenGames(), List.class);
        List<String> list = new ArrayList<>();
        if (gamesNames != null) {
            gamesNames.forEach(gameName -> list.add(gameName.toString()));
        }
        return list;
    }
}
