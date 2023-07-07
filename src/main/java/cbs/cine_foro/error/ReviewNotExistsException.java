package cbs.cine_foro.error;

public class ReviewNotExistsException extends Exception {
    private final static String MESSAGE = "Veredict not exists in the database";

    public ReviewNotExistsException() {
        super(MESSAGE);
    }

    public ReviewNotExistsException(final String message) {
        super(message);
    }
}
