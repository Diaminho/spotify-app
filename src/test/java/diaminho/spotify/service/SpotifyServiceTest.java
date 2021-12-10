package diaminho.spotify.service;


import diaminho.spotify.dto.SongDto;
import diaminho.spotify.mapper.SongDtoMapper;
import diaminho.spotify.mapper.YandexPlaylistStringMapper;
import diaminho.spotify.model.auth.Token;
import diaminho.spotify.model.spotify.Artist;
import diaminho.spotify.model.spotify.Item;
import diaminho.spotify.model.spotify.Response;
import diaminho.spotify.model.spotify.Track;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;
import java.util.function.Predicate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
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
    public void getPlaylistTracksTest() {
        String playlistId = "test";
        String accessToken = "Bearer test";

        var token = new Token();
        token.setAccessToken(accessToken);

        var monoToken = Mono.just(token);

        when(authService.getToken()).thenReturn(monoToken);

        var headerSpec = mock(WebClient.RequestHeadersUriSpec.class);
        when(spotifyClient.get()).thenReturn(headerSpec);

        var uriSpec = mock(WebClient.RequestBodyUriSpec.class);
        when(headerSpec.uri(anyString())).thenReturn(uriSpec);

        var bodySpec = mock(WebClient.RequestBodySpec.class);
        when(uriSpec.header(any(), any())).thenReturn(bodySpec);

        var responseSpec = mock(WebClient.ResponseSpec.class);
        when(bodySpec.retrieve()).thenReturn(responseSpec);

        when(responseSpec.onStatus(any(Predicate.class), any())).thenReturn(responseSpec);

        String trackName = "Test track";

        var track = new Track();
        track.setName(trackName);

        String artistName = "Test Singer";

        var artist = new Artist();
        artist.setName(artistName);
        track.setArtists(Collections.singletonList(artist));

        var item = new Item();
        item.setTrack(track);

        var response = new Response();
        response.setItems(Collections.singletonList(item));

        when(responseSpec.bodyToMono(eq(Response.class)))
                .thenReturn(Mono.just(response));

        var expectedDto = new SongDto();
        expectedDto.setAuthor(artistName);
        expectedDto.setName(trackName);

        when(songDtoMapper.itemToSongDto(eq(item))).thenReturn(expectedDto);

        Flux<SongDto> songFlux = spotifyService.getPlaylistTracks(playlistId);

        StepVerifier
                .create(songFlux)
                .expectNextMatches(song -> song.equals(expectedDto))
                .verifyComplete();
    }

    @Test
    public void getPlaylistTracksAsStringTest() {
        String playlistId = "test";
        String accessToken = "Bearer test";

        var token = new Token();
        token.setAccessToken(accessToken);

        var monoToken = Mono.just(token);

        when(authService.getToken()).thenReturn(monoToken);

        String trackName = "Test track";

        var track = new Track();
        track.setName(trackName);

        String artistName = "Test Singer";

        var artist = new Artist();
        artist.setName(artistName);
        track.setArtists(Collections.singletonList(artist));

        var item = new Item();
        item.setTrack(track);

        var response = new Response();
        response.setItems(Collections.singletonList(item));

        var expectedDto = new SongDto();
        expectedDto.setAuthor(artistName);
        expectedDto.setName(trackName);

        String expectedPlaylist = artistName + " - " + trackName + "\n";
        when(yandexPlaylistStringMapper.songsDtoToYandexPlaylistString(any(Flux.class))).thenReturn(Mono.just(expectedPlaylist));

        Mono<String> playlistMono = spotifyService.getPlaylistAsString(playlistId);

        StepVerifier
                .create(playlistMono)
                .expectNextMatches(playlist -> playlist.equals(expectedPlaylist))
                .verifyComplete();
    }

}
