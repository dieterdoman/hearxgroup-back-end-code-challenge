package server.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import server.dao.PlayerDao;
import server.entities.Player;
import server.services.PlayerService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
@Slf4j
public class PlayerController
{
    @Autowired
    private PlayerDao playerDao;
    @Autowired
    private PlayerService playerService;

    @PostMapping(value = "/player/create")
    public ResponseEntity<Void> createPlayer(@RequestBody MultiValueMap<String, String> body)
    {
        String name = body.getFirst("playerName");
        if (playerService.createPlayer(name)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @RequestMapping(value = "/players")
    public ResponseEntity<List<String>> listPlayers()
    {
        return ResponseEntity.ok(playerDao.findAll().stream()
                .map(Player::getName)
                .collect(Collectors.toList()));
    }
}
