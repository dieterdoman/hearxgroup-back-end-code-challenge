package server.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "scores")
@Getter
@Setter
public class Score
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private int score;
    private int responseTime;

    @ManyToOne
    @JoinColumn(name="player_name")
    @JsonIgnore
    private Player player;

    @ManyToOne
    @JoinColumn(name="round")
    @JsonIgnore
    private Round round;
}
