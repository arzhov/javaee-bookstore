package com.arzhov.bookstore.ejb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.arzhov.bookstore.jpa.Author;
import com.arzhov.bookstore.jpa.Book;

@Stateless
public class BookService extends JPAService<Book> {
//	@PersistenceContext(unitName = "bookstorePersistenceUnit")
	@PersistenceContext(unitName = "MySQL")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public BookService() {
		super(Book.class);
	}

    @Override
    public void create(Book book) {
    	em.persist(book);
    	if ( book.getAuthors() != null) {
	    	for (Author author : book.getAuthors()) {
	    		if (author.getAuthoredBooks() == null) author.setAuthoredBooks(new HashSet<Book>());
	    		author.getAuthoredBooks().add(book);	   
	    		em.merge(author);
	    	}	    	
    	}    	
    }
    
    @Override
    public void edit(Book book) {
    	Set<Author> selectedAuthors = book.getAuthors();    	
    	if (selectedAuthors != null) {
    		removeBookFromAuthorsNotInList(book, book.getAuthors());    		
    		for (Author author : book.getAuthors()) {
	    		if (author.getAuthoredBooks() == null) {
                            author.setAuthoredBooks(new HashSet<Book>());
                        }
	    		if (!author.getAuthoredBooks().contains(book)) { 
	    			author.getAuthoredBooks().add(book);
	    		}    		
	    	}    		
    	} else {
    		removeBookFromAllAuthors(book);
    	}
    	em.merge(book);    	    	
    }       
    
    @Override
    public void remove(Book book) {
        book = find(book.getId());
    	removeBookFromAllAuthors(book);
    	super.remove(book);    	
    }
    
    protected void removeBookFromAllAuthors(Book book) {
    	TypedQuery<Author> query = em.createQuery("SELECT a FROM Author a JOIN a.authoredBooks b WHERE b.id = :bookId", Author.class);
		query.setParameter("bookId", book.getId());		
		removeBookFromAuthors(book, query.getResultList());    	
    }
    
    protected void removeBookFromAuthorsNotInList(Book book, Collection<Author> authorsExludeList) {
    	List<Long> authorsIdList = new ArrayList<Long>();
    	for (Author author : authorsExludeList) authorsIdList.add(author.getId());
    	TypedQuery<Author> query = em.createQuery("SELECT a FROM Author a JOIN a.authoredBooks b WHERE b.id = :bookId AND a.id NOT IN :idList", Author.class);
		query.setParameter("bookId", book.getId());
		query.setParameter("idList", authorsIdList);    
		removeBookFromAuthors(book, query.getResultList());		
    }
    
    private void removeBookFromAuthors(Book book, List<Author> authors) {
    	for (Author author : authors) {
			author.getAuthoredBooks().remove(book);			
			em.merge(author);
		}
    }   
}
