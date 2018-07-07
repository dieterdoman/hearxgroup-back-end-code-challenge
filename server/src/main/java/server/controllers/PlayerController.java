package server.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import server.dao.PlayerDao;
import server.entities.Player;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping
@Slf4j
public class PlayerController
{
    @Autowired
    private PlayerDao playerDao;

    @PostMapping(value = "/player/create")
    public ResponseEntity<Void> createPlayer(@RequestBody MultiValueMap<String, String> body)
    {
        String name = body.getFirst("playerName");
        Optional<Player> player = playerDao.findById(name);
        if (player.isPresent()) {
            logger.error(MessageFormat.format("{0} already exist", player.get().getName()));
            return ResponseEntity.badRequest().build();
        }
        playerDao.saveAndFlush(new Player(name));
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/players")
    public ResponseEntity<List<String>> listPlayers()
    {
        return ResponseEntity.ok(playerDao.findAll().stream()
                .map(Player::getName)
                .collect(Collectors.toList()));
    }
}
