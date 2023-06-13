package cbs.cine_foro.error;

public class NationalityNotExistsException extends Exception {
    private final static String MESSAGE = "Nationality not exists in the database";

    public NationalityNotExistsException(final String message) {
        super(message);
    }

    public NationalityNotExistsException() {
        super(MESSAGE);
    }
}
