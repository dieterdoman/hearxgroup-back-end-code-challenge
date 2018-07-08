package server.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import server.GameProperties;
import server.entities.Game;
import server.entities.Round;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RoundServiceTest
{
    private RoundService roundService;
    private Game game;

    @BeforeEach
    void setupTest()
    {
        GameProperties gameProperties = Mockito.mock(GameProperties.class);
        Mockito.when(gameProperties.getRounds()).thenReturn(1);
        roundService = new RoundService(gameProperties);
        game = Mockito.mock(Game.class);
    }

    @Test
    void generateNewRoundsTest()
    {
        Set<Round> rounds = roundService.generateNewRounds(game);
        Optional<Round> optionalRound = rounds.stream().findFirst();
        assertTrue(optionalRound.isPresent());
        Round round = optionalRound.get();
        assertEquals(0, round.getRoundNumber());
        assertEquals(round.getNumber1() * round.getNumber2(), round.getAnswer());
        assertEquals(game, round.getGame());
    }
}
