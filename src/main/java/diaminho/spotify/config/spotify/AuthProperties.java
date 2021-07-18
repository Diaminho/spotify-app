package diaminho.spotify.config.spotify;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spotify.auth")
@Data
public class AuthProperties {
    private String baseUrl;
    private String clientId;
    private String clientSecret;
}
