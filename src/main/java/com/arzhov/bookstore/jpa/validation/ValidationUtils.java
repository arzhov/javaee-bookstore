package com.arzhov.bookstore.jpa.validation;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

public class ValidationUtils {
    public final String VALIDATION_MESSAGES_PARAM = "validationMessages";
    
    protected Validator validator;

    public ValidationUtils(final Validator validator) {
        this.validator = validator;
    }
            
     /**
     * Adds a ValidationMessage in a List<ValidationMessage> object in the HttpServletRequest object.
     * @param request - The current request object
     * @param message - The ValidationMessage object
     */   
    @SuppressWarnings("unchecked")
	public void addValidationMessage(final HttpServletRequest request, final ValidationMessage message) {
        final Object validationMessagesObject = request.getAttribute(VALIDATION_MESSAGES_PARAM);
        
        final Set<ValidationMessage> validationMessages;
        if (validationMessagesObject == null) {
            validationMessages = new HashSet<>();
        } else if (validationMessagesObject instanceof Set){
            validationMessages = (Set<ValidationMessage>) validationMessagesObject;
        } else {
            throw new RuntimeException("You have stored a validationMessage object in request attributes that is not a List");
        }        
        validationMessages.add(message);
        
        request.setAttribute(VALIDATION_MESSAGES_PARAM, validationMessages);
    }

    public boolean areThereValidationMessages(final HttpServletRequest request) {
        return request.getAttribute(VALIDATION_MESSAGES_PARAM) != null;
    }
    
    public <T> void  validateObject(final HttpServletRequest request, final T t) {
         final Set<ConstraintViolation<T>> violations = validator.validate(t);
        if (violations != null && violations.size() > 0) {            
            for (final ConstraintViolation<T> violation : violations) {
                addValidationMessage(request, new ValidationMessage(violation.getPropertyPath().toString(), violation.getMessage()));
            }                        
        }
    }
}
