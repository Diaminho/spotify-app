package diaminho.spotify.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "Simplified Spotify API Playlist Object")
@Data
public class PlaylistDto {

    @Schema(description = "List of songs in playlist")
    private List<SongDto> songs;
}
