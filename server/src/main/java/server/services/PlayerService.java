package server.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import server.dao.PlayerDao;
import server.entities.Player;

import java.text.MessageFormat;
import java.util.Optional;

@Service
@Slf4j
public class PlayerService
{
    private PlayerDao playerDao;

    public PlayerService(PlayerDao playerDao)
    {
        this.playerDao = playerDao;
    }

    public boolean createPlayer(String name)
    {
        Optional<Player> player = playerDao.findById(name);
        if (player.isPresent()) {
            logger.error(MessageFormat.format("{0} already exist", player.get().getName()));
            return false;
        }
        playerDao.saveAndFlush(new Player(name));
        return true;
    }
}
