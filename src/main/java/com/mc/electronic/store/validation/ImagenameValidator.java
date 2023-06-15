package com.mc.electronic.store.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented()
@Constraint(validatedBy = ImagenameValid.class)
public @interface ImagenameValidator {

	//Defualt error message
	String message() default "Invalid image name";

	//represet group of constrints
	Class<?>[] groups() default { };

	//represent additinol information about annotations
	Class<? extends Payload>[] payload() default { };
}
