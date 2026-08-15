package com.nuriabravo.repon.domain.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("auth.invalidCredentials");
    }
}