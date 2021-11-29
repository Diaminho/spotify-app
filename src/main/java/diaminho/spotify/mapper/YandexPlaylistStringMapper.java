package diaminho.spotify.mapper;

import diaminho.spotify.dto.SongDto;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Util class to Convert Spotify playlist to Yandex Import playlist String
 */
@Component
public class YandexPlaylistStringMapper {
    private static final String AUTHOR_NAME_SEPARATOR = " - ";
    private static final String SONG_SEPARATOR = "\n";

    public String songsDtoToYandexPlaylistString(List<SongDto> songs) {
        StringBuilder yandexString = new StringBuilder();

        songs.forEach(song ->
                yandexString
                        .append(song.getAuthor())
                        .append(AUTHOR_NAME_SEPARATOR)
                        .append(song.getName())
                        .append(SONG_SEPARATOR)
        );

        return yandexString.toString();
    }
}
