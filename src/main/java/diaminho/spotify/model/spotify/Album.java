package diaminho.spotify.model.spotify;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "Simplified spotify Album object")
@Data
public class Album {
    //TODO
    private String id;

    @Schema(description = "Album's title")
    private String name;

    //TODO
    @JsonProperty("album_type")
    private String albumType;

    @Schema(description = "Album's author list")
    private List<Artist> artists;

    //TODO
    private String type;
}
