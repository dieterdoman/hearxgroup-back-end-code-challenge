package server;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:game-settings.properties")
@Data
public class GameProperties
{
    private int players;
    private int time;
    private int rounds;
}
