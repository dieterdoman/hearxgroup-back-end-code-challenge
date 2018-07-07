package server.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Players")
@Getter
@Setter
public class Player
{
    @Id
    @Column(name = "player_name")
    private String name;

    @ManyToMany(mappedBy = "players")
    @JsonIgnore
    private Set<Game> games = new HashSet<>();

    @OneToMany(mappedBy="player")
    @JsonIgnore
    private Set<Score> scores;

    public Player()
    {

    }

    public Player(String name)
    {
        this.name = name;
    }
}
