package srg.api.app;

/**
 * Created by Sergey on 13.10.2016.
 */
public class ResponseParsingException extends Exception {
    public ResponseParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResponseParsingException(String message) {
        super(message);
    }
}
