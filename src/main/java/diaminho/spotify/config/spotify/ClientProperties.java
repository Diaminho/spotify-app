package diaminho.spotify.config.spotify;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spotify.api")
@Data
public class ClientProperties {
    private String baseUrl;
}
