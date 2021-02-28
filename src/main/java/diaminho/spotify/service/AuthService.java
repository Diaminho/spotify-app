package diaminho.spotify.service;

import diaminho.spotify.model.auth.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
@Slf4j
public class AuthService {
    @Value("${spotify.auth.clientId}")
    private String clientId;
    @Value("${spotify.auth.clientSecret}")
    private String clientSecret;

    private static final List<String> SCOPES = List.of("playlist-read-collaborative");

    private final WebClient spotifyClient;

    public AuthService(@Qualifier("spotifyAuthHttpClient") WebClient spotifyClient) {
        this.spotifyClient = spotifyClient;
    }

    public String getAccessToken() {
        String authHeader = buildAuthorizationHeader();

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        body.add("scope", "playlist-read-collaborative");



        Mono<Token> result = spotifyClient
                .post()
                .body(BodyInserters.fromFormData(body))
                .header(HttpHeaders.AUTHORIZATION, authHeader)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> response.bodyToMono(String.class) // error body as String or other class
                        .flatMap(error -> Mono.error(new RuntimeException(error)))) // throw a
                .bodyToMono(Token.class);

        Token token =  result.block();

        log.info("Response: "  + token);
        return token.getAccessToken();
    }

    private String buildAuthorizationHeader() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Basic ");

        String base64Info = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());
        stringBuilder.append(base64Info);

        return stringBuilder.toString();
    }
}
