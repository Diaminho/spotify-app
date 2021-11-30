package diaminho.spotify.mapper;

import diaminho.spotify.dto.SongDto;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Util class to Convert Spotify playlist to Yandex Import playlist String
 */
@Component
public class YandexPlaylistStringMapper {
    private static final String YANDEX_SONG_FORMAT = "%s - %s\n";

    public String songsDtoToYandexPlaylistString(List<SongDto> songs) {
        StringBuilder yandexString = new StringBuilder();

        songs.forEach(song ->
                yandexString
                        .append(String.format(YANDEX_SONG_FORMAT, song.getAuthor(), song.getName()))
        );

        return yandexString.toString();
    }
}
