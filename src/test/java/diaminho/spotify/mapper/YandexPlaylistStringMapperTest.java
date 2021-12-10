package diaminho.spotify.mapper;

import diaminho.spotify.dto.SongDto;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import java.util.List;

public class YandexPlaylistStringMapperTest {
    private final YandexPlaylistStringMapper mapper = new YandexPlaylistStringMapper();

    @Test
    public void correctStringConvertingTest() {
        String defaultAuthor = "Test author";
        String defaultName = "Test name";

        String expected = defaultAuthor + " - " + defaultName + "\n";

        List<SongDto> songs = List.of(new SongDto(defaultAuthor, defaultName));

        Mono<String> playlistMono = mapper.songsDtoToYandexPlaylistString(Flux.fromIterable(songs));

        StepVerifier
                .create(playlistMono)
                .expectNextMatches(p -> p.equals(expected))
                .verifyComplete();
    }
}
