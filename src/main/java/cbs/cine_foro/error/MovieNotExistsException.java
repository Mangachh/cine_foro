package cbs.cine_foro.error;

public class MovieNotExistsException extends Exception {
    private final static String MESSAGE = "Movie not exists in the database";

    public MovieNotExistsException(final String message) {
        super(message);
    }

    public MovieNotExistsException() {
        super(MESSAGE);
    }
}
