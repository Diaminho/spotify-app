package diaminho.spotify.model.spotify;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "Simplified spotify Album object")
@Data
public class Album {

    private String id;

    @Schema(description = "Album's title")
    private String name;

    @JsonProperty("album_type")
    private String albumType;

    @Schema(description = "Album's author list")
    private List<Artist> artists;

    private String type;
}
