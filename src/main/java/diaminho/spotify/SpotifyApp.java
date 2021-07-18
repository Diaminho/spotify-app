package diaminho.spotify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = "diaminho.spotify.config")
public class SpotifyApp {

    public static void main(String[] args) {
        SpringApplication.run(SpotifyApp.class, args);
    }
}
