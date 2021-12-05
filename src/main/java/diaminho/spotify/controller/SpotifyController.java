package diaminho.spotify.controller;

import diaminho.spotify.dto.SongDto;
import diaminho.spotify.service.SpotifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("spotify")
@Slf4j
public class SpotifyController {
    private final SpotifyService spotifyService;

    public SpotifyController(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    @GetMapping("export/{playlistId}")
    public Flux<SongDto> exportPlaylist(@PathVariable("playlistId") String playlistId) {
        return spotifyService.getPlaylistTracks(playlistId);
    }

    /*
    @GetMapping("export-as-string/{playlistId}")
    public Mono<String> exportAsPlaylist(@PathVariable("playlistId") String playlistId) {
        return spotifyService.getPlaylistAsString(playlistId);
    }*/
}
