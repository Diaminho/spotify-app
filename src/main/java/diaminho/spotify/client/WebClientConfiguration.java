package diaminho.spotify.client;

import diaminho.spotify.config.spotify.AuthProperties;
import diaminho.spotify.config.spotify.ClientProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Base64;

@Configuration
@Slf4j
public class WebClientConfiguration {
    private static final String AUTH_PREFIX = "Basic ";
    private static final String BASIC_AUTH_SEPARATOR = ":";

    //16 KB
    private static final int CODEC_IN_MEMORY_SIZE = 16 * 1024 * 1024;

    /**
     * Client for spotify API
     * @param clientProperties Base ur;
     * @return WebClint for spotify API
     */
    @Bean("spotifyHttpClient")
    public WebClient spotifyClient(ClientProperties clientProperties) {
        return WebClient.builder()
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer
                                .defaultCodecs()
                                .maxInMemorySize(CODEC_IN_MEMORY_SIZE))
                        .build())
                .baseUrl(clientProperties.getBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    /**
     * Auth client for spotify API
     * @param authProperties client credentials and oauth url
     * @return WebClient for auth
     */
    @Bean("spotifyAuthHttpClient")
    public WebClient spotifyAuthClient(AuthProperties authProperties) {
        String basicAuthKey = generateBasicAuthKey(authProperties);

        return WebClient.builder()
                .baseUrl(authProperties.getBaseUrl())
                .defaultHeader(HttpHeaders.AUTHORIZATION, basicAuthKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    private String generateBasicAuthKey(AuthProperties authProperties) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(AUTH_PREFIX);

        String base64Info = authProperties.getClientId() +
                BASIC_AUTH_SEPARATOR +
                authProperties.getClientSecret();
        String encoded = Base64.getEncoder().encodeToString(base64Info.getBytes());

        stringBuilder.append(encoded);

        return stringBuilder.toString();
    }
}
