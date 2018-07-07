package client;

import lombok.Data;

@Data
public class GameRound
{
    private int id;
    private int number1;
    private int number2;
    private int roundNumber;
    private int answer;
    private GameStatus roundStatus;
}
