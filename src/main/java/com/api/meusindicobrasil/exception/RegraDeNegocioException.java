package com.api.meusindicobrasil.exception;

public class RegraDeNegocioException extends RuntimeException{
    private String message;

    public RegraDeNegocioException(String message) {
        super(message);
    }
}
