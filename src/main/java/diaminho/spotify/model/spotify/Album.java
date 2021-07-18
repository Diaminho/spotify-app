package diaminho.spotify.model.spotify;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Album {
    private String id;

    private String name;

    @JsonProperty("album_type")
    private String albumType;

    private List<Artist> artists;

    private String type;
}
