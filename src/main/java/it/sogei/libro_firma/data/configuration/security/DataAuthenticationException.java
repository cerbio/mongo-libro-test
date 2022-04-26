package it.sogei.libro_firma.data.configuration.security;

import org.springframework.security.core.AuthenticationException;

public class DataAuthenticationException extends AuthenticationException {

	public DataAuthenticationException(String msg) {
		super(msg);
	}

	private static final long serialVersionUID = 1L;

	private String message;
	private Integer code;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

}
