package com.arzhov.bookstore.jpa.validation;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

public class ValidationMessage {
    private final String fieldName;
    private final String message;

    public ValidationMessage(final String fieldName, final String message) {
        if (StringUtils.isBlank(message)) {
            throw new IllegalArgumentException("The message cannot be empty");
        }       
        this.fieldName = fieldName;
        this.message = message;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getMessage() {
        return message;
    }         

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.fieldName != null ? this.fieldName.hashCode() : 0);
        hash = 41 * hash + (this.message != null ? this.message.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ValidationMessage other = (ValidationMessage) obj;
        if (!Objects.equals(this.fieldName, other.fieldName)) {
            return false;
        }
        return Objects.equals(this.message, other.message);
    }        
}
