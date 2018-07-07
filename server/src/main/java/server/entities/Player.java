package server.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "Players")
public class Player
{
    @Id
    private String name;
    @ManyToMany(mappedBy = "players")
    private Set<Game> games = new HashSet<>();

    public Player()
    {

    }

    public Player(String name)
    {
        this.name = name;
    }
}
