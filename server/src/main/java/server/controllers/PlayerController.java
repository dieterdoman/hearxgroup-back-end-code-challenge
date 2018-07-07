package server.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.dao.PlayerDao;
import server.entities.Player;

import java.text.MessageFormat;
import java.util.Optional;

@RestController
@RequestMapping(value = "/player")
@Slf4j
public class PlayerController
{
    @Autowired
    private PlayerDao playerDao;

    @RequestMapping(value = "/create/{name}")
    public boolean createPlayer(@PathVariable String name)
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
