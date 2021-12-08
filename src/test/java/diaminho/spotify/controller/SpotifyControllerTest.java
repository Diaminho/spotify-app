package diaminho.spotify.controller;

import diaminho.spotify.dto.SongDto;
import diaminho.spotify.service.SpotifyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(SpotifyController.class)
public class SpotifyControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    private SpotifyService spotifyService;

    @Test
    public void getSongsFromPlaylistTest() {
        String playlistId = "test";
        var songDto = new SongDto();

        Flux<SongDto> songDtoFlux = Flux.just(songDto);

        when(spotifyService.getPlaylistTracks(eq(playlistId))).thenReturn(songDtoFlux);

        webTestClient.get()
                .uri("/spotify/export/" + playlistId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(SongDto.class)
                .hasSize(1)
                .contains(songDto);
    }

    @Test
    public void getPlaylistAsStringTest() {
        String playlistId = "test";

        String playlistAsString = "Test Author - Song\nTest2 Author2 - Song2\n";

        Mono<String> playlist = Mono.just(playlistAsString);

        when(spotifyService.getPlaylistAsString(eq(playlistId))).thenReturn(playlist);

        webTestClient.get()
                .uri("/spotify/export-as-string/" + playlistId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .isEqualTo(playlistAsString);
    }
}

