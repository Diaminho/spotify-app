package diaminho.spotify.mapper;

import diaminho.spotify.dto.SongDto;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Util class to Convert Spotify playlist to Yandex Import playlist String
 */
@Component
public class YandexPlaylistStringMapper {
    private static final String YANDEX_SONG_FORMAT = "%s - %s\n";

    public Mono<String> songsDtoToYandexPlaylistString(Flux<SongDto> songs) {
        return songs
                .map(song -> String.format(YANDEX_SONG_FORMAT, song.getAuthor(), song.getName()))
                .reduce("", String::concat);
    }
}
