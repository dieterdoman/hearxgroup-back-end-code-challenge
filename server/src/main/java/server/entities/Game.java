package server.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "Games")
public class Game
{
    @Id
    private String name;
    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus;
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "games_players",
            joinColumns = { @JoinColumn(name = "game_id") },
            inverseJoinColumns = { @JoinColumn(name = "player_id") }
    )
    private Set<Player> players = new HashSet<>();

    public Game()
    {

    }

    public Game(String name)
    {
        this.name = name;
        this.gameStatus = GameStatus.LOBBY;
    }

    public void addPlayer(Player player)
    {
        players.add(player);
    }
}
