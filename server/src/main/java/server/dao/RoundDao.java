package server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.entities.Player;

@Repository
public interface RoundDao extends JpaRepository<Player, Integer>
{
}
