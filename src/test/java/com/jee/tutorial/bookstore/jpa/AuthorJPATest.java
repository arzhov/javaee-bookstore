package com.jee.tutorial.bookstore.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;
import javax.ejb.embeddable.EJBContainer;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.arzhov.bookstore.jpa.Author;
import com.arzhov.bookstore.jpa.Book;


public class AuthorJPATest {
	private static EJBContainer container;
	
	
	@BeforeClass
	public static void setUp() {		
		container = EJBContainer.createEJBContainer();
	}
	
	@AfterClass
	public static void tearDown() {
		container.close();
	}
	
	@Before
	public void before() throws Exception {	
		container.getContext().bind("inject", this);		
	}
	
	@Resource
    private UserTransaction userTransaction;

    @PersistenceContext
    private EntityManager entityManager;	
    
    @Test
    public void testAuthor() throws NotSupportedException, SystemException, IllegalStateException, SecurityException, HeuristicMixedException, HeuristicRollbackException, RollbackException {
    	
    	Query query = entityManager.createQuery("SELECT a FROM Author a");
    	assertEquals(0, query.getResultList().size());
		
    	query = entityManager.createQuery("SELECT b FROM Book b");
    	assertEquals(0, query.getResultList().size());
    	
    	userTransaction.begin();
    	Book book = new Book();
    	book.setTitle("Pod igoto");
    	book.setPrice(new BigDecimal("50.00"));
    	book.setIsbn("90-70002-34-5");
    	entityManager.persist(book);
    	
    	Author author = new Author();
    	author.setFirstName("Ivan");
    	author.setLastName("Vazov");
    	Set<Book> books = new HashSet<Book>();
    	books.add(book);
    	author.setAuthoredBooks(books);    	
    	entityManager.persist(author);
    	
    	userTransaction.commit();
    	
    	assertNotNull(author.getId());
    	
    	userTransaction.begin();
    	Author fromDb = entityManager.find(Author.class, author.getId());    	
    	userTransaction.commit();
    	assertEquals(1, fromDb.getAuthoredBooks().size());
    }
}
