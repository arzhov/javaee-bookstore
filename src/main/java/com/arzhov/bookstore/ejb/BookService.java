package com.arzhov.bookstore.ejb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

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
	public Book find(final Long id) {
		final EntityGraph<?> graph = getEntityManager().createEntityGraph(Book.GRAPH_BOOKS_AUTHORS);

		/*
		// Dynamic graph - alternative
		final EntityGraph graph = this.em.createEntityGraph(Book.class);
		Subgraph authorsGraph = graph.addSubgraph("authors");
		//*/

		final Map hints = new HashMap();
		hints.put("javax.persistence.fetchgraph", graph);

		return getEntityManager().find(Book.class, id, hints);
	}

	/*
	@Override
	public List<Book> findAll() {
		CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		final Root r = cq.from(Book.class);
//        r.fetch("authors", JoinType.INNER);
		cq.select(r);
		return getEntityManager().createQuery(cq).getResultList();
	}*/

	@Override
	public List<Book> findRange(final int[] range) {
		final CriteriaQuery<Book> cq = getEntityManager().getCriteriaBuilder().createQuery(Book.class);
		final Root<Book> r = cq.from(Book.class);
		r.fetch("authors", JoinType.INNER);
		cq.select(r);
		final TypedQuery<Book> q = getEntityManager().createQuery(cq);
		q.setMaxResults(range[1] - range[0]);
		q.setFirstResult(range[0]);
		return q.getResultList();
	}

	@Override
    public void create(final Book book) {
    	em.persist(book);
    	if ( book.getAuthors() != null) {
	    	for (final Author author : book.getAuthors()) {
	    		if (author.getAuthoredBooks() == null) author.setAuthoredBooks(new HashSet<>());
	    		author.getAuthoredBooks().add(book);	   
	    		em.merge(author);
	    	}	    	
    	}    	
    }
    
    @Override
    public void edit(final Book book) {
    	final Set<Author> selectedAuthors = book.getAuthors();
    	if (selectedAuthors != null) {
    		removeBookFromAuthorsNotInList(book, book.getAuthors());    		
    		for (final Author author : book.getAuthors()) {
	    		if (author.getAuthoredBooks() == null) {
                            author.setAuthoredBooks(new HashSet<>());
                        }
			    author.getAuthoredBooks().add(book);
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
    
    protected void removeBookFromAllAuthors(final Book book) {
    	final TypedQuery<Author> query = em.createQuery("SELECT a FROM Author a JOIN a.authoredBooks b WHERE b.id = :bookId", Author.class);
		query.setParameter("bookId", book.getId());		
		removeBookFromAuthors(book, query.getResultList());    	
    }
    
    protected void removeBookFromAuthorsNotInList(final Book book, final Collection<Author> authorsExludeList) {
    	final List<Long> authorsIdList = new ArrayList<>();
    	for (final Author author : authorsExludeList) authorsIdList.add(author.getId());
    	final TypedQuery<Author> query = em.createQuery("SELECT a FROM Author a JOIN a.authoredBooks b WHERE b.id = :bookId AND a.id NOT IN :idList", Author.class);
		query.setParameter("bookId", book.getId());
		query.setParameter("idList", authorsIdList);    
		removeBookFromAuthors(book, query.getResultList());		
    }
    
    private void removeBookFromAuthors(final Book book, final List<Author> authors) {
    	for (final Author author : authors) {
			author.getAuthoredBooks().remove(book);			
			em.merge(author);
		}
    }   
}
