package com.arzhov.bookstore.jpa.validation;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.validation.Validator;

@Stateless
public class ValidationUtilsProvider {
    @Inject @JPAValidator
    private Validator validator;
    
    @Produces
    public ValidationUtils getValidationUtils() {
        return new ValidationUtils(validator);
    }
}
