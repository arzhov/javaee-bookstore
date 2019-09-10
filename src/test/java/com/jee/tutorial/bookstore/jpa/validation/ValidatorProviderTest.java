package com.jee.tutorial.bookstore.jpa.validation;

import static org.junit.Assert.*;

import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
import javax.validation.Validator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ValidatorProviderTest {

	private static final EJBContainer EJB_CONTAINER = EJBContainer.createEJBContainer();

	@Before
	public void setUp() throws Exception {		
		EJB_CONTAINER.getContext().bind("inject", this);
	}
	
	@After
	public void tearDown() throws Exception {		
		EJB_CONTAINER.close();
	}
	
	@Inject
	private Validator validator;	
	
	@Test
	public void testInjection() {
		assertNotNull(validator);					
	}
	
}
