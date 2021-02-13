package diaminho.spotify.service;

import diaminho.spotify.dto.PlaylistDto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class SpotifyService {
    private static final String PLAYLIST_URI = "/v1/playlists/%s/tracks";

    private final WebClient spotifyClient;

    public SpotifyService(@Qualifier("spotifyHttpClient") WebClient spotifyClient) {
        this.spotifyClient = spotifyClient;
    }

    public PlaylistDto getPlaylistTracks(String playlistId) {
        String playlistUri = String.format(PLAYLIST_URI, playlistId);
        Mono<String> result = spotifyClient
                .get()
                .uri(playlistUri)
                .retrieve()
                .bodyToMono(String.class);

        log.info("Response: "  + result.block());

        return null;
    }
}
