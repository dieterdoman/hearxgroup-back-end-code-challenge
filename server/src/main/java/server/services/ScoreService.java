package server.services;

import org.springframework.stereotype.Service;
import server.dao.RoundDao;
import server.dao.ScoreDao;
import server.entities.Game;
import server.entities.Player;
import server.entities.Round;
import server.entities.Score;

import java.util.List;
import java.util.Optional;

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

    public List<Score> getScoresForGame(Game game)
    {
        return scoreDao.findAllByRound(roundDao.findByGame(game));
    }

}
