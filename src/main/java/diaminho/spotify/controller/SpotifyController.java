package diaminho.spotify.controller;

import diaminho.spotify.dto.PlaylistDto;
import diaminho.spotify.service.SpotifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("spotify")
@Slf4j
public class SpotifyController {
    private final SpotifyService spotifyService;

    public SpotifyController(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    @GetMapping("export/{playlistId}")
    public PlaylistDto exportPlaylist(@PathVariable("playlistId") String playlistId) {
        spotifyService.getPlaylistTracks(playlistId);
        return new PlaylistDto();
    }
}
