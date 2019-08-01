package com.akg.lang;

/**
 * Exception thrown when a key signature is invalid.
 */
public class SigningKeyInvalidException extends Exception {

    public SigningKeyInvalidException() {
    }

    public SigningKeyInvalidException(String s) {
        super(s);
    }

    public SigningKeyInvalidException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public SigningKeyInvalidException(Throwable throwable) {
        super(throwable);
    }

    public SigningKeyInvalidException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }

}
