package cbs.cine_foro.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> userAlreadyExistsException(UserAlreadyExistsException exception,
            WebRequest request) {

        return this.createResponseEntity(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(UserNotExistsException.class)
    public ResponseEntity<ErrorMessage> userNotExistsException(UserNotExistsException exception,
            WebRequest request) {

        return this.createResponseEntity(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(MovieNotExistsException.class)
    public ResponseEntity<ErrorMessage> moveNotExistsException(MovieNotExistsException exception,
            WebRequest request) {

        return this.createResponseEntity(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(NationalityNotExistsException.class)
    public ResponseEntity<ErrorMessage> nationalityNotExistsException(NationalityNotExistsException exception,
            WebRequest request) {

        return this.createResponseEntity(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(VeredictNotExistsException.class)
    public ResponseEntity<ErrorMessage> veredictNotExistsException(VeredictNotExistsException exception,
            WebRequest request) {

        return this.createResponseEntity(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(VeredictMovieExistsException.class)
    public ResponseEntity<ErrorMessage> veredictMovieExistsExeption(VeredictMovieExistsException exception,
            WebRequest request) {

        return this.createResponseEntity(HttpStatus.BAD_REQUEST, exception.getMessage());
    }
    
    private ResponseEntity<ErrorMessage> createResponseEntity(final HttpStatus status, final String message) {
        ErrorMessage error = new ErrorMessage(status, message);
        return ResponseEntity.status(error.getStatus()).body(error);
    }
}
