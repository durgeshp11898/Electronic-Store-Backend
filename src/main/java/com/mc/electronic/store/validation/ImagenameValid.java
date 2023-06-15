package com.mc.electronic.store.validation;



import org.apache.log4j.Logger;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ImagenameValid implements ConstraintValidator<ImagenameValidator, String>{

	Logger logger = Logger.getLogger(ImagenameValid.class);
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		logger.info("Message from isvalid custom --> "+value);
		if(value.isBlank()) {
		return false;
		}else 
			return true;
	}

}
