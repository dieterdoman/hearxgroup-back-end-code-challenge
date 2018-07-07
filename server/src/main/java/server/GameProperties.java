package server;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("game")
@Component
@Data
public class GameProperties
{
    private int players;
    private int time;
    private int rounds;
}
