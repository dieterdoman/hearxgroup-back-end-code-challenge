package server.controllers;

import lombok.extern.slf4j.Slf4j;
import org.cloudifysource.restDoclet.annotations.PossibleResponseStatus;
import org.cloudifysource.restDoclet.annotations.PossibleResponseStatuses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PossibleResponseStatuses(responseStatuses = {
            @PossibleResponseStatus(code = 200, description = "Player was successfully created."),
            @PossibleResponseStatus(code = 400, description = "Player already exists with current name.")
    })
    @RequestMapping(value = "/create/{name}")
    public ResponseEntity<Void> createPlayer(@PathVariable String name)
    {
        Optional<Player> player = playerDao.findById(name);
        if (player.isPresent()) {
            logger.error(MessageFormat.format("{0} already exist", player.get().getName()));
            return ResponseEntity.badRequest().build();
        }
        playerDao.saveAndFlush(new Player(name));
        return ResponseEntity.ok().build();
    }
}
