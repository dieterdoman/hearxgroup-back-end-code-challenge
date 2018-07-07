package server.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "rounds")
@Getter
@Setter
public class Round
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="game_name")
    @JsonIgnore
    private Game game;

    private int number1;
    private int number2;
    private int answer;

    @Enumerated(EnumType.STRING)
    private GameStatus roundStatus;
    private int roundNumber;

    @OneToMany(mappedBy="round")
    @JsonIgnore
    private Set<Score> scores;
}
