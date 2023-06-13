package cbs.cine_foro.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> userAlreadyExistsException(UserAlreadyExistsException exception,
            WebRequest request) {

        ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.badRequest().body(message);
    }

    @ExceptionHandler(UserNotExistsException.class)
    public ResponseEntity<ErrorMessage> userNotExistsException(UserNotExistsException exception,
            WebRequest request) {

        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(message.getStatus()).body(message);
    }

    @ExceptionHandler(MovieNotExistsException.class)
    public ResponseEntity<ErrorMessage> moveNotExistsException(MovieNotExistsException exception,
            WebRequest request) {

        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(message.getStatus()).body(message);
    }

    @ExceptionHandler(NationalityNotExistsException.class)
    public ResponseEntity<ErrorMessage> nationalityNotExistsException(NationalityNotExistsException exception,
            WebRequest request) {
        
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(message.getStatus()).body(message);
    }
}
