package spotify.mapper;

import diaminho.spotify.dto.SongDto;
import diaminho.spotify.mapper.YandexPlaylistStringMapper;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class YandexPlaylistStringMapperTest {
    private final YandexPlaylistStringMapper mapper = new YandexPlaylistStringMapper();

    @Test
    public void correctStringConvertingTest() {
        String defaultAuthor = "Test author";
        String defaultName = "Test name";

        String expected = defaultAuthor + " - " + defaultName + "\n";

        List<SongDto> songs = List.of(new SongDto(defaultAuthor, defaultName));

        /*String actual = mapper.songsDtoToYandexPlaylistString(songs);

        assertEquals(actual, expected);*/
    }
}
