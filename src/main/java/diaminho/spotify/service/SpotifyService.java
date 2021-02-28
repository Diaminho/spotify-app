package diaminho.spotify.service;

import diaminho.spotify.dto.PlaylistDto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class SpotifyService {
    private static final String PLAYLIST_URI = "/v1/playlists/%s/tracks";

    private final WebClient spotifyClient;
    private final AuthService authService;

    public SpotifyService(@Qualifier("spotifyHttpClient") WebClient spotifyClient, AuthService authService) {
        this.spotifyClient = spotifyClient;
        this.authService = authService;
    }

    public PlaylistDto getPlaylistTracks(String playlistId) {
        String accessToken = "Bearer " + authService.getAccessToken();

        String playlistUri = String.format(PLAYLIST_URI, playlistId);
        Mono<String> result = spotifyClient
                .get()
                .uri(playlistUri)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> response.bodyToMono(String.class) // error body as String or other class
                        .flatMap(error -> Mono.error(new RuntimeException(error)))) // throw a
                .bodyToMono(String.class);

        log.info("Response: "  + result.block());

        return null;
    }
}
