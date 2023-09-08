package br.com.fiap.Insight.ia.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RestValueInvalidException extends RuntimeException {
     
    public RestValueInvalidException(String message) {
        super(message);
    }
}
