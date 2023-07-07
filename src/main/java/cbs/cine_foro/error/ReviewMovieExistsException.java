package cbs.cine_foro.error;

public class ReviewMovieExistsException extends Exception{
    private final static String MESSAGE = "A Veredict by this user to this movie already exist in the database";

    public ReviewMovieExistsException() {
        super(MESSAGE);
    }

    public ReviewMovieExistsException(final String message) {
        super(message);
    }
}
