package diaminho.spotify.exception;

/**
 * Exception for Spotify API interaction troubles via
 */
public class ApiSpotifyException extends RuntimeException {

    public ApiSpotifyException(String message) {
        super(message);
    }

    public ApiSpotifyException(String message, Throwable cause) {
        super(message, cause);
    }
}
