package server.services;

import org.springframework.stereotype.Service;
import server.dao.RoundDao;
import server.dao.ScoreDao;
import server.entities.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScoreService
{
    private ScoreDao scoreDao;
    private RoundDao roundDao;

    public ScoreService(ScoreDao scoreDao, RoundDao roundDao)
    {
        this.scoreDao = scoreDao;
        this.roundDao = roundDao;
    }

    public boolean saveScore(int time, int answer, int roundNumber, Player player, Game game)
    {
        Optional<Round> round = roundDao.findByRoundNumberAndGame(roundNumber, game);
        if (round.isPresent()) {
            Optional<Score> optionalScore = scoreDao.findByRoundAndPlayer(round.get(), player);
            if (!optionalScore.isPresent()) {
                Score score = new Score();
                score.setPlayer(player);
                score.setResponseTime(time);
                score.setRound(round.get());
                score.setScore(answer);
                scoreDao.saveAndFlush(score);
                return true;
            }
        }
        return false;
    }

    public List<RoundStatistic> getScoresForGame(Game game)
    {
        List<Optional<Score>> scores = new ArrayList<>();
        roundDao.findByGame(game).stream()
                .map(round -> scoreDao.findByRound(round))
        .forEach(scores::add);
        return scores.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(this::convertToStatistics)
                .collect(Collectors.toList());
    }

    private RoundStatistic convertToStatistics(Score score)
    {
        RoundStatistic roundStatistic = new RoundStatistic();
        roundStatistic.setPlayerName(score.getPlayer().getName());
        roundStatistic.setRoundNumber(score.getRound().getRoundNumber());
        roundStatistic.setCorrect(score.getScore() == score.getRound().getAnswer());
        roundStatistic.setResponseTime(score.getResponseTime());
        return roundStatistic;
    }

}
