package com.jee.tutorial.bookstore.jpa.validation;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;

import com.arzhov.bookstore.jpa.Book;

public class BookValidationTest {

	private static Validator validator;
	
	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
	}
	
	@Test
	public void shortTitle() {
		Book book = new Book();
		book.setTitle("a");
		
		Set<ConstraintViolation<Book>> validations = validator.validateProperty(book, "title");
		assertEquals(1, validations.size());					
		assertEquals("Size", getFirstAnnotaionName(validations));
		
		book.setTitle("I am longer than 5 symbols");
		validations = validator.validateProperty(book, "title");
		assertEquals(0, validations.size());
	}
	
	
	@Test
	public void missingTitle() {
		Book book = new Book();
		Set<ConstraintViolation<Book>> validations = validator.validateProperty(book, "title");
		
		assertEquals(1, validations.size());						
		String violationAnnotation = getFirstAnnotaionName(validations);
		assertEquals("NotNull", violationAnnotation);
		
		book.setTitle("I am not null");
		validations = validator.validateProperty(book, "title");
		assertEquals(0, validations.size());
	}

	
	@Test
	public void testISBNConstraintValidation() {
		final String ISBN_10 = "90-70002-34-5";
		final String ISBN_13 = "978-92-95055-02-5";
		Book book = new Book();
		book.setIsbn("isbn");
		
		Set<ConstraintViolation<Book>> validations = validator.validateProperty(book, "isbn");
		
		assertEquals(1, validations.size());
		assertEquals("ISBNConstraint", getFirstAnnotaionName(validations));		
		
		book.setIsbn(ISBN_10);
		validations = validator.validateProperty(book, "isbn");
		assertEquals(0, validations.size());
		
		book.setIsbn(ISBN_13);
		validations = validator.validateProperty(book, "isbn");
		assertEquals(0, validations.size());
				
	}	
	
	@Test
	public void missingISBNConstraint() {			
		Book book = new Book();		
		
		Set<ConstraintViolation<Book>> validations = validator.validateProperty(book, "isbn");
		assertEquals(1, validations.size());
		assertEquals("NotEmpty", getFirstAnnotaionName(validations));
	}
	
	private String getFirstAnnotaionName(Set<ConstraintViolation<Book>> validations) {
		return getFirstConstraint(validations).getConstraintDescriptor().getAnnotation().annotationType().getSimpleName();
	}

	private ConstraintViolation<Book> getFirstConstraint(
			Set<ConstraintViolation<Book>> validations) {
		return validations.iterator().next();
	}
	
}
