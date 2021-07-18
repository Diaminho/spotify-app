package diaminho.spotify.exception;

public class ApiSpotifyException extends RuntimeException{

    public ApiSpotifyException(String message) {
        super(message);
    }

    public ApiSpotifyException(String message, Throwable cause) {
        super(message, cause);
    }
}
