package server.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Games")
@Getter
@Setter
public class Game
{
    @Id
    @Column(name = "game_name")
    private String name;

    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "games_players",
            joinColumns = { @JoinColumn(name = "game_name") },
            inverseJoinColumns = { @JoinColumn(name = "player_name") }
    )
    @JsonIgnore
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
