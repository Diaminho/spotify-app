package diaminho.spotify.service;


import diaminho.spotify.dto.SongDto;
import diaminho.spotify.mapper.SongDtoMapper;
import diaminho.spotify.mapper.YandexPlaylistStringMapper;
import diaminho.spotify.model.auth.Token;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SpotifyServiceTest {
    @Mock
    @Qualifier("spotifyHttpClient")
    private WebClient spotifyClient;

    @Mock
    private AuthService authService;

    @Mock
    private SongDtoMapper songDtoMapper;

    @Mock
    private YandexPlaylistStringMapper yandexPlaylistStringMapper;

    @InjectMocks
    private SpotifyService spotifyService;


    @Test
    public void test() {
        String playlistId = "test";

        String accessToken = "Bearer test";

        var token = new Token();
        token.setAccessToken(accessToken);

        //when(authService.getAccessToken()).thenReturn(token);


        //when(spotifyClient.get()).thenReturn(spotifyClient);

        Flux<SongDto> result = spotifyService.getPlaylistTracks(playlistId);
    }

}
