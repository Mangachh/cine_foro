package cbs.cine_foro.error;

public class UserAlreadyExistsException extends Exception {
    private final static String MESSAGE = "The user already exists in the database";

    public UserAlreadyExistsException(final String message) {
        super(message);
    }

    public UserAlreadyExistsException() {
        super(MESSAGE);
    }
}
