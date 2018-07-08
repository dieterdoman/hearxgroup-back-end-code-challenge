package server.services;

import org.springframework.stereotype.Service;
import server.GameProperties;
import server.entities.Game;
import server.entities.Round;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
public class RoundService
{
    private GameProperties gameProperties;

    public RoundService(GameProperties gameProperties)
    {
        this.gameProperties = gameProperties;
    }

    public Set<Round> generateNewRounds(Game game)
    {
        Set<Round> rounds = new HashSet<>();
        for (int i = 0; i < gameProperties.getRounds(); i++) {
            Round round = new Round();
            round.setRoundNumber(i);
            round.setNumber1(new Random().nextInt(9) + 1);
            round.setNumber2(new Random().nextInt(9) + 1);
            round.setGame(game);
            rounds.add(round);
        }
        return rounds;
    }
}
