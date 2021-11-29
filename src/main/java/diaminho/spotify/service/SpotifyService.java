package diaminho.spotify.service;

import diaminho.spotify.dto.SongDto;
import diaminho.spotify.exception.ApiSpotifyException;
import diaminho.spotify.mapper.SongDtoMapper;
import diaminho.spotify.mapper.YandexPlaylistStringMapper;
import diaminho.spotify.model.auth.Token;
import diaminho.spotify.model.spotify.Item;
import diaminho.spotify.model.spotify.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SpotifyService {
    private static final String PLAYLIST_URI = "/v1/playlists/%s/tracks";
    private static final String TOKEN_PREFIX = "Bearer ";

    private final WebClient spotifyClient;
    private final AuthService authService;
    private final SongDtoMapper songDtoMapper;
    private final YandexPlaylistStringMapper yandexPlaylistStringMapper;

    public SpotifyService(@Qualifier("spotifyHttpClient") WebClient spotifyClient, AuthService authService, SongDtoMapper songDtoMapper, YandexPlaylistStringMapper yandexPlaylistStringMapper) {
        this.spotifyClient = spotifyClient;
        this.authService = authService;
        this.songDtoMapper = songDtoMapper;
        this.yandexPlaylistStringMapper = yandexPlaylistStringMapper;
    }

    public List<SongDto> getPlaylistTracks(String playlistId) {
        Token token = authService.getAccessToken();

        String accessToken = TOKEN_PREFIX + token.getAccessToken();

        String playlistUri = String.format(PLAYLIST_URI, playlistId);

        List<Item> items = new ArrayList<>();
        Response response = getTracks(items, playlistUri, accessToken);

        return response
                .getItems()
                .stream()
                .map(songDtoMapper::itemToSongDto)
                .collect(Collectors.toList());
    }

    public String getPlaylistAsString(String playlistId) {
        return yandexPlaylistStringMapper.songsDtoToYandexPlaylistString(getPlaylistTracks(playlistId));
    }


    private Response getTracks(List<Item> previousItems, String playlistUri, String token) {
        //TODO try parallel stream instead of recursion
        Mono<Response> responseMono = spotifyClient
                .get()
                .uri(playlistUri)
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> response.createException()
                        .flatMap(error -> Mono.error(new ApiSpotifyException("Cannot receive playlist info: " + response.releaseBody()))))
                .bodyToMono(Response.class);

        Response response = responseMono.block();
        previousItems.addAll(response.getItems());

        String next = response.getNext();

        if (next != null) {
            getTracks(previousItems, next, token);
        }

        response.getItems().addAll(previousItems);
        return response;
    }
}
