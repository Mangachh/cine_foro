package cbs.cine_foro.error;

public class VeredictMovieExistsException extends Exception{
    private final static String MESSAGE = "A Veredict by this user to this movie already exist in the database";

    public VeredictMovieExistsException() {
        super(MESSAGE);
    }

    public VeredictMovieExistsException(final String message) {
        super(message);
    }
}
