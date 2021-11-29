package diaminho.spotify.model.spotify;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Simplified Spotify Artist object")
@Data
public class Artist {
    @Schema(description = "Internal spotify id")
    private String id;

    @Schema(description = "Artist's name")
    private String name;

    //TODO
    private String type;

    @Schema(description = "Spotify uri to artis")
    private String uri;
}
