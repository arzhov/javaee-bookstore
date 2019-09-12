package com.arzhov.bookstore.jpa.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.ISBNValidator;

public class ISBNConstraintValidator implements ConstraintValidator<ISBNConstraint, String>{

	@Override
	public void initialize(final ISBNConstraint constraintAnnotation) {
		// Nothing to do here
	}

	@Override
	public boolean isValid(final String value, final ConstraintValidatorContext context) {
		if (StringUtils.isBlank(value)) return true;
		final ISBNValidator validator = ISBNValidator.getInstance();
		return validator.isValid(value);
	}
}
