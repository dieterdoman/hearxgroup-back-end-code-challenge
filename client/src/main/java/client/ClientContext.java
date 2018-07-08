package client;

import jline.console.ConsoleReader;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@SpringBootConfiguration
@EnableConfigurationProperties
public class ClientContext
{
    @Bean
    public RestTemplate getRestTemplate()
    {
        return new RestTemplate();
    }

    @Bean
    public ConsoleReader getConsoleReader() throws IOException
    {
        return new ConsoleReader();
    }
}
