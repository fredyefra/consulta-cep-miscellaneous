package br.com.main.exception;

public class NegocioException extends RuntimeException {

    //int status;

    public NegocioException() {

    }

    public NegocioException(String message) {

        super(message);
        //this.status = status;
    }
}