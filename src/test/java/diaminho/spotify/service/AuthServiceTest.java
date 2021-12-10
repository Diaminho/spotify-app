package diaminho.spotify.service;

import diaminho.spotify.model.auth.Token;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.function.Predicate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    @Qualifier("spotifyAuthHttpClient")
    private WebClient spotifyClient;

    @InjectMocks
    private AuthService authService;


    @Test
    public void getTokenTest() {

        var uriSpec = mock(WebClient.RequestBodyUriSpec.class);
        when(spotifyClient.post()).thenReturn(uriSpec);

        var requestHeaderSpec = mock(WebClient.RequestHeadersSpec.class);
        when(uriSpec.body(any(BodyInserter.class))).thenReturn(requestHeaderSpec);

        var requestBodyUriSpec = mock(WebClient.RequestBodyUriSpec.class);
        when(requestHeaderSpec.header(any(), any())).thenReturn(requestBodyUriSpec);

        var responseSpec = mock(WebClient.ResponseSpec.class);
        when(requestBodyUriSpec.retrieve()).thenReturn(responseSpec);

        when(responseSpec.onStatus(any(Predicate.class), any())).thenReturn(responseSpec);

        var token = new Token();
        token.setAccessToken("Bearer test");

        when(responseSpec.bodyToMono(eq(Token.class)))
                .thenReturn(Mono.just(token));


        Mono<Token> tokenMono = authService.getToken();

        StepVerifier
                .create(tokenMono)
                .expectNextMatches(t -> t.equals(token))
                .verifyComplete();
    }

}
