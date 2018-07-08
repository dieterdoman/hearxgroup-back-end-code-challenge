package server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.entities.Game;
import server.entities.Round;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoundDao extends JpaRepository<Round, Integer>
{
    Optional<Round> findByRoundNumberAndGame(Integer roundNumber, Game game);

    List<Round> findByGame(Game game);
}
