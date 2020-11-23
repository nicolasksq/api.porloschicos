package ar.com.porloschicos.backend.controller.Content.Exceptions;

public class ExceptionContent extends Exception {

    public static final int ERROR_CODE_CONTENT_NOT_FOUND = 101;
    public static final String ERROR_MESSAGE_CONTENT_NOT_FOUND = "ERROR_CODE_CONTENT_NOT_FOUND";

    public ExceptionContent(String message) {
        super(message);
    }

    public ExceptionContent(String message, Throwable cause) {
        super(message, cause);
    }

}


