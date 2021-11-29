package diaminho.spotify.model.spotify;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

//TODO
@Schema(description = "")
@Data
public class Item {
    @Schema(description = "Spotify Track object")
    private Track track;
}
