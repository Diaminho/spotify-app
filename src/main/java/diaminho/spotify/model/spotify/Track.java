package diaminho.spotify.model.spotify;

import lombok.Data;

import java.util.List;

@Data
public class Track {
    private Album album;
    private List<Artist> artists;
    private String name;
}
