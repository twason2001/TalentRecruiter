package com.talent.recruit.utils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValueValidator.class)
@Documented
public @interface ValueValidator {
	
	   public abstract String message() default "Invalid Application Status!";
     
	    public abstract Class<?>[] groups() default {};
	  
	    public abstract Class<? extends Payload>[] payload() default {};
	     
	    public abstract Class<? extends java.lang.Enum<?>> EnumValidatorClass();
	     

}
