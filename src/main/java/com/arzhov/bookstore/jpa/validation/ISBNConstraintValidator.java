package com.arzhov.bookstore.jpa.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.ISBNValidator;

public class ISBNConstraintValidator implements ConstraintValidator<ISBNConstraint, String>{

	@Override
	public void initialize(ISBNConstraint constraintAnnotation) {		
		// Nothing to do here
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (StringUtils.isBlank(value)) return true;
		ISBNValidator validator = ISBNValidator.getInstance();
		return validator.isValid(value);
	}
}
