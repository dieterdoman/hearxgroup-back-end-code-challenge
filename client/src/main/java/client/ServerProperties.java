package client;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("server")
@Component
@Data
public class ServerProperties
{
    private String ip;
    private String port;
}
