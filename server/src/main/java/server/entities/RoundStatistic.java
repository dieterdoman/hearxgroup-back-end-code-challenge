package server.entities;

import lombok.Data;

@Data
public class RoundStatistic
{
    private boolean correct;
    private int responseTime;
    private String playerName;
    private int roundNumber;
}
