package server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import server.entities.Game;

public interface GameDao extends JpaRepository<Game, String>
{
}
