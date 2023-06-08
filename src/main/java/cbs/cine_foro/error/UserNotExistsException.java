package cbs.cine_foro.error;

public class UserNotExistsException extends Exception {
    private final static String MESSAGE = "User does not exist in the database";

    public UserNotExistsException(final String message) {
        super(message);
    }

    public UserNotExistsException() {
        super(MESSAGE);
    }
    
}
