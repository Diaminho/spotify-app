package diaminho.spotify.model.spotify;

import lombok.Data;

import java.util.List;

@Data
public class Response {
    private List<Item> items;
    private long limit;
    private String next;
    private long offset;
    private String previous;
    private long total;
}
