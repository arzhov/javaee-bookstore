package com.arzhov.bookstore.jpa.validation;

import org.apache.commons.lang3.StringUtils;

public class ValidationMessage {
    private String fieldName;
    private String message;

    public ValidationMessage(String fieldName, String message) {
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
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ValidationMessage other = (ValidationMessage) obj;
        if ((this.fieldName == null) ? (other.fieldName != null) : !this.fieldName.equals(other.fieldName)) {
            return false;
        }
        if ((this.message == null) ? (other.message != null) : !this.message.equals(other.message)) {
            return false;
        }
        return true;
    }        
}
