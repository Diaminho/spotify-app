package diaminho.spotify.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Simplified Spotify Song object")
@Data
public class SongDto {

    @Schema(description = "Song's author")
    private String author;

    @Schema(description = "Song's name")
    private String name;
}
