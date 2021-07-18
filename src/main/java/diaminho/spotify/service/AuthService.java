package diaminho.spotify.service;

import diaminho.spotify.model.auth.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class AuthService {
    private final WebClient spotifyClient;

    public AuthService(@Qualifier("spotifyAuthHttpClient") WebClient spotifyClient) {
        this.spotifyClient = spotifyClient;
    }

    public Token getAccessToken() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        body.add("scope", "playlist-read-collaborative");

        Mono<Token> result = spotifyClient
                .post()
                .body(BodyInserters.fromFormData(body))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> response.bodyToMono(String.class)
                        .flatMap(error -> Mono.error(new RuntimeException(error))))
                .bodyToMono(Token.class);

        return result.block();
    }
}
