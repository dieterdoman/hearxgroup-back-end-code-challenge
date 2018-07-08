package server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.entities.Player;
import server.entities.Round;
import server.entities.Score;

import java.util.Optional;

@Repository
public interface ScoreDao extends JpaRepository<Score, Integer>
{
    Optional<Score> findByRoundAndPlayer(Round round, Player player);

    Optional<Score> findByRound(Round round);
}