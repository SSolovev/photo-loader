package srg.api.app.exceptions;


public class CreateAlbumException extends Exception {
    public CreateAlbumException() {
    }

    public CreateAlbumException(String message) {
        super(message);
    }

    public CreateAlbumException(String message, Throwable cause) {
        super(message, cause);
    }
}
