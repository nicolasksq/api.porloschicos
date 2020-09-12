package ar.com.porloschicos.backend.controller.error;

import ar.com.porloschicos.backend.controller.error.Exceptions.ExceptionAuth;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        GlobalErrorResponse errors = new GlobalErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage()
        );
        return new ResponseEntity(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ExceptionAuth.class)
    public ResponseEntity<GlobalErrorResponse> handleExceptionAuthDuplicateEntry(ExceptionAuth ex, WebRequest request) {
        GlobalErrorResponse errors = new GlobalErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_ACCEPTABLE.value(),
                ex.getMessage(),
                ExceptionAuth.ERROR_CODE_DUPLICATE_USER_ENTRY
        );

        return new ResponseEntity<>(errors, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<GlobalErrorResponse> handleExceptionAuthBadRequest(NullPointerException ex, WebRequest request) {
        GlobalErrorResponse errors = new GlobalErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "RESQUEST_MUST_BE_COMPLETE",
                ExceptionAuth.ERROR_CODE_INCOMPLETE_REQUEST
        );

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


}
