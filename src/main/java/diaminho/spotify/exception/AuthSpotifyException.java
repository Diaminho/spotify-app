package diaminho.spotify.exception;

/**
 * Exception for Auth troubles via Spotify Auth client
 */
public class AuthSpotifyException extends RuntimeException{

    public AuthSpotifyException(String message) {
        super(message);
    }

    public AuthSpotifyException(String message, Throwable cause) {
        super(message, cause);
    }
}
