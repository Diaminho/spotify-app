package diaminho.spotify.dto;

import lombok.Data;

import java.util.List;

@Data
public class PlaylistDto {
    List<SongDto> songs;
}
