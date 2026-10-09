package br.com.vanguarderp.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionsHandler {
    public ResponseEntity<ResponseApi> generalMsgApiExceptions(MsgApiException msgApiException, HttpServletRequest httpServletRequest) {

        ResponseApi responseApi = new ResponseApi(
                new Date(),
                msgApiException.getStatus().value(),
                msgApiException.getStatus().getReasonPhrase(),
                msgApiException.getMessage(),
                httpServletRequest.getRequestURI()
        );

        return ResponseEntity.status(msgApiException.getStatus())
                                                    .contentType(MediaType.APPLICATION_JSON).body(responseApi);
    }
}
