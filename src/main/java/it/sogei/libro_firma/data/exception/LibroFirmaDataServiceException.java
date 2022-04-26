package it.sogei.libro_firma.data.exception;

public class LibroFirmaDataServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	private String message;
	private Integer code;
	
	public LibroFirmaDataServiceException(String message) {
		this.setMessage(message);
		this.setCode(500);
	}
	
	public LibroFirmaDataServiceException(String message, Integer code) {
		this.setMessage(message);
		this.setCode(code);
	}

	@Override
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
