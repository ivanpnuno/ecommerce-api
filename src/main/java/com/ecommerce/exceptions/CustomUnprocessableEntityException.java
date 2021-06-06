package com.ecommerce.exceptions;

public class CustomUnprocessableEntityException extends BaseException {

	private static final long serialVersionUID = 4545818821756694586L;

	public CustomUnprocessableEntityException(String message) {
		super(message, 422);
	}
}
