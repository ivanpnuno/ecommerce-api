package com.ecommerce.exceptions;

public class CustomBadRequestException extends BaseException {

	private static final long serialVersionUID = 517984756314087915L;

	public CustomBadRequestException(String message) {
		super(message, 400);
	}
}
