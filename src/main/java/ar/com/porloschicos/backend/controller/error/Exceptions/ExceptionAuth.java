package ar.com.porloschicos.backend.controller.error.Exceptions;

public class ExceptionAuth extends Exception {

    public static final int ERROR_CODE_DUPLICATE_USER_ENTRY = 100;
    public static final int ERROR_CODE_INCOMPLETE_REQUEST   = 200;

    public ExceptionAuth(String message, Throwable cause) {
        super(message, cause);
    }

}
