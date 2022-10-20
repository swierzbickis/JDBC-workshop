package jdbc;

public class DatabaseActionException extends RuntimeException {

    public DatabaseActionException(final Throwable cause) {
        super(cause);
    }
}