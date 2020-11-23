package ar.com.porloschicos.backend.controller.Authentication.Exceptions;

public class ExceptionAuthInvalidSignature extends Exception {

    public static final int ERROR_CODE_INVALID_SIGNATURE = 300;

    public ExceptionAuthInvalidSignature(String message, Throwable cause) {
        super(message, cause);
    }


}
