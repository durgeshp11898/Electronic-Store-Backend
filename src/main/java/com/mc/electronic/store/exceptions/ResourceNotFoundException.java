package com.mc.electronic.store.exceptions;

import lombok.Builder;

@Builder
public class ResourceNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException() {
		super("Resource Not Found !!");
	}
	
	public ResourceNotFoundException(String message) {
		super(message);
	}
	

}
