package com.arzhov.bookstore.jpa.validation;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;

@Stateless
public class ValitatorProvider {	
	
	private static Validator validator;	
	
	@Produces @JPAValidator
	public Validator getValidator() {		
		if (validator == null) {
			HibernateValidatorConfiguration configuration = Validation.byProvider( HibernateValidator.class ).configure();		
			ValidatorFactory validationFactory = configuration.buildValidatorFactory();
			validator = validationFactory.getValidator();
		}
		return validator;		
	}                
}
