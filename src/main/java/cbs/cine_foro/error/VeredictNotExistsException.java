package cbs.cine_foro.error;

public class VeredictNotExistsException extends Exception {
    private final static String MESSAGE = "Veredict not exists in the database";

    public VeredictNotExistsException() {
        super(MESSAGE);
    }

    public VeredictNotExistsException(final String message) {
        super(message);
    }
}
