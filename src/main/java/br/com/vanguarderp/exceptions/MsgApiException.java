package br.com.vanguarderp.exceptions;

import org.springframework.http.HttpStatus;

public class MsgApiException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private HttpStatus status;
	
	public MsgApiException(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}

	public MsgApiException(String message) {
		super(message);
		this.status = HttpStatus.BAD_REQUEST;
	}

}
