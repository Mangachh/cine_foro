package cbs.cine_foro.error;

public class UserAlreadyExistsException extends Exception {
    
    public UserAlreadyExistsException(final String message) {
        super(message);
    }
}
