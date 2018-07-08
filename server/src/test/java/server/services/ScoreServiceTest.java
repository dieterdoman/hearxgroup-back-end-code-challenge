package server.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import server.dao.RoundDao;
import server.dao.ScoreDao;
import server.entities.*;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScoreServiceTest
{
    private ScoreDao scoreDao;
    private RoundDao roundDao;
    private ScoreService scoreService;
    private Game game;
    private Player player;

    @BeforeEach
    void setupTests()
    {
        scoreDao = Mockito.mock(ScoreDao.class);
        roundDao = Mockito.mock(RoundDao.class);
        scoreService = new ScoreService(scoreDao, roundDao);
        player = Mockito.mock(Player.class);
        game = Mockito.mock(Game.class);
    }

    @Test
    void saveScoreSuccessTest()
    {
        Round round = Mockito.mock(Round.class);
        Mockito.when(roundDao.findByRoundNumberAndGame(1, game)).thenReturn(Optional.of(round));
        Mockito.when(scoreDao.findByRoundAndPlayer(round, player)).thenReturn(Optional.empty());
        assertTrue(scoreService.saveScore(1, 1, 1, player, game));
    }

    @Test
    void saveScoreFailTest()
    {
        Mockito.when(roundDao.findByRoundNumberAndGame(1, game)).thenReturn(Optional.empty());
        assertFalse(scoreService.saveScore(1, 1, 1, player, game));
    }

    @Test
    void getScoresForGameTest()
    {
        Round round = Mockito.mock(Round.class);
        Mockito.when(roundDao.findByGame(game)).thenReturn(Collections.singletonList(round));
        Score score = Mockito.mock(Score.class);
        Mockito.when(scoreDao.findByRound(round)).thenReturn(Optional.of(score));
        Mockito.when(score.getPlayer()).thenReturn(player);
        Mockito.when(player.getName()).thenReturn("dieter");
        Mockito.when(score.getRound()).thenReturn(round);
        Mockito.when(round.getRoundNumber()).thenReturn(1);
        Mockito.when(score.getScore()).thenReturn(2);
        Mockito.when(round.getAnswer()).thenReturn(2);
        Mockito.when(score.getResponseTime()).thenReturn(3);

        RoundStatistic roundStatistic = new RoundStatistic();
        roundStatistic.setPlayerName("dieter");
        roundStatistic.setRoundNumber(1);
        roundStatistic.setCorrect(true);
        roundStatistic.setResponseTime(3);
        assertEquals(Collections.singletonList(roundStatistic), scoreService.getScoresForGame(game));
    }
}
