package com.ecommerce.exceptions;

public class CustomNotFoundException extends BaseException {
	
	private static final long serialVersionUID = -5694049047060382644L;

	public CustomNotFoundException(String message) {
		super(message, 404);
	}
}
