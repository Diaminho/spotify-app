package diaminho.spotify.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Simplified Spotify Song object")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongDto {

    @Schema(description = "Song's author")
    private String author;

    @Schema(description = "Song's name")
    private String name;
}
