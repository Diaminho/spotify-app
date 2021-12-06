package diaminho.spotify.service;

import diaminho.spotify.dto.SongDto;
import diaminho.spotify.exception.ApiSpotifyException;
import diaminho.spotify.mapper.SongDtoMapper;
import diaminho.spotify.mapper.YandexPlaylistStringMapper;
import diaminho.spotify.model.auth.Token;
import diaminho.spotify.model.spotify.Item;
import diaminho.spotify.model.spotify.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Service for Spotify Api interaction
 */
@Service
@Slf4j
public class SpotifyService {
    private static final String PLAYLIST_URI = "/v1/playlists/%s/tracks";
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final int LIMIT = 100;

    private final WebClient spotifyClient;
    private final AuthService authService;
    private final SongDtoMapper songDtoMapper;
    private final YandexPlaylistStringMapper yandexPlaylistStringMapper;

    public SpotifyService(@Qualifier("spotifyHttpClient") WebClient spotifyClient,
                          AuthService authService,
                          SongDtoMapper songDtoMapper,
                          YandexPlaylistStringMapper yandexPlaylistStringMapper) {
        this.spotifyClient = spotifyClient;
        this.authService = authService;
        this.songDtoMapper = songDtoMapper;
        this.yandexPlaylistStringMapper = yandexPlaylistStringMapper;
    }

    /**
     * Method to receive all tracks from spotify playlist
     * @param playlistId spotify playlist id
     * @return List of songs from playlist
     */
    public Flux<SongDto> getPlaylistTracks(String playlistId) {
        Token token = authService.getAccessToken();
        String accessToken = TOKEN_PREFIX + token.getAccessToken();

        String playlistUri = String.format(PLAYLIST_URI, playlistId);
        Flux<Item> items = getTracks(playlistUri, accessToken);

        return items.map(songDtoMapper::itemToSongDto);
    }


    /**
     * Receive all songs from Spotify playlist as String formatted to Yandex playlist importer
     * @param playlistId Spotify playlist Id
     * @return Formatted string for Yandex playlist importer
     */

    public Mono<String> getPlaylistAsString(String playlistId) {
        return Mono
                .from(yandexPlaylistStringMapper.songsDtoToYandexPlaylistString(getPlaylistTracks(playlistId)));
    }

    private Flux<Item> getTracks(String playlistUri, String token) {
        return getTrack(playlistUri, token)
                .flatMapMany(response ->
                        Flux.fromIterable(generatePlaylistUris(response.getTotal(), playlistUri))
                                .flatMap(pageUri -> getTrack(pageUri, token))
                                .map(Response::getItems)
                                .flatMap(Flux::fromIterable)
                                .mergeWith(Flux.fromIterable(response.getItems()))
                );
    }

    private Mono<Response> getTrack(String playlistUri, String token) {
        return spotifyClient
                .get()
                .uri(playlistUri)
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> response.createException()
                        .flatMap(error -> Mono.error(new ApiSpotifyException("Cannot receive playlist info: " + response.releaseBody()))))
                .bodyToMono(Response.class);
    }

    private List<String> generatePlaylistUris(int total, String playlistId) {
        long count = (long) Math.ceil((double) total / (LIMIT)) - 1;

        if (count < 1) {
            return Collections.emptyList();
        }

        List<String> playlistUris = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            playlistUris.add(playlistId + "?limit=" + LIMIT + "&offset=" + (i + 1) * LIMIT);
        }

        return playlistUris;
    }
}
