package com.talent.recruit.utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValueValidator implements ConstraintValidator<ValueValidator, String>{

	 private List<String> values;
	 
    @Override
    public void initialize(ValueValidator annotation)
    {
    	values = Stream.of(annotation.EnumValidatorClass().getEnumConstants()).map(Enum::name).collect(Collectors.toList());
    }
    
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
            return true;
        }
 
        return values.contains(value);
	}

}
