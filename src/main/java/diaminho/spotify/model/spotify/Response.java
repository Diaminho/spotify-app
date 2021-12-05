package diaminho.spotify.model.spotify;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "Spotify get playlist response object")
@Data
public class Response {
    private List<Item> items;
    private long limit;
    private String next;
    private int offset;
    private String previous;
    private int total;
}
