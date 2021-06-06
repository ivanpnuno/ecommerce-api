package com.ecommerce.exceptions;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseException extends Exception implements Serializable {

	private static final long serialVersionUID = -5055624086915172241L;
	private Integer exceptionCode;
	
	public BaseException(String message, Integer exceptionCode) {
		super(message);
		this.exceptionCode = exceptionCode;
	}
}
