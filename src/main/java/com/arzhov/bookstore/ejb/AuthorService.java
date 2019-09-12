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
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import com.arzhov.bookstore.jpa.Author;
import com.arzhov.bookstore.jpa.Book;

@Stateless
public class AuthorService extends JPAService<Author> {
//	@PersistenceContext(unitName = "bookstorePersistenceUnit")
	@PersistenceContext(unitName = "MySQL")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public AuthorService() {
		super(Author.class);
	}

	@Override
	public Author find(final Long id) {
		//*
		final TypedQuery<Author> nq = getEntityManager().createNamedQuery(Author.QUERY_ALL_AUTHOR_BOOKS, Author.class);
		nq.setParameter("id", id);
		return nq.getSingleResult();
		//*/

		/*
		final TypedQuery<Author> q = getEntityManager().createQuery("SELECT a FROM Author a JOIN FETCH a.authoredBooks b WHERE a.id = :id", Author.class);
		q.setParameter("id", id);
		return q.getSingleResult();
		//*/

		/*
		final EntityGraph<?> graph = getEntityManager().createEntityGraph(Author.GRAPH_AUTHOR_BOOKS);
		Map hints = new HashMap();
		hints.put("javax.persistence.fetchgraph", graph);
		return getEntityManager().find(Author.class, id, hints);
		 //*/
	}

	/*
	@Override
	public List<Author> findAll() {
		CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		final Root r = cq.from(Author.class);
//		r.fetch("authoredBooks", JoinType.INNER);
		cq.select(r);
		return getEntityManager().createQuery(cq).getResultList();
	}*/

	@Override
	public List<Author> findRange(final int[] range) {
		final CriteriaQuery<Author> cq = getEntityManager().getCriteriaBuilder().createQuery(Author.class);
		final Root<Author> r = cq.from(Author.class);
		r.fetch("authoredBooks", JoinType.INNER);
		cq.select(r);
		final TypedQuery<Author> q = getEntityManager().createQuery(cq);
		q.setMaxResults(range[1] - range[0]);
		q.setFirstResult(range[0]);
		return q.getResultList();
	}

	@Override
	public void create(final Author author) {
		final Set<Book> authoredBooks = author.getAuthoredBooks();
		if (authoredBooks != null && authoredBooks.size() > 0) {
			for (final Book book : authoredBooks) {
				if (book.getAuthors() == null) {
					book.setAuthors(new HashSet<>());
				}
				book.getAuthors().add(author);
				em.merge(book);
			}
		}
		em.persist(author);
	}

	@Override
	public void edit(final Author author) {
		if (author.getAuthoredBooks() != null) {
			removeAuthorFromBooks(author);
		} else {
			removeAuthorFromAllBooks(author);
		}
		em.merge(author);
	}

	@Override
	public void remove(Author author) {
		author = em.find(Author.class, author.getId());
		removeAuthorFromAllBooks(author);
		super.remove(author);
	}	

	 public List<Author> findByName(final String name) {
		 final TypedQuery<Author> nq = getEntityManager().createNamedQuery(Author.QUERY_FIND_AUTHOR_BOOKS, Author.class);
		 nq.setParameter("name", String.format("%%%s%%", name));
		 return nq.getResultList();
	 }

	private void removeAuthorFromBooks(final Author author) {
		final Collection<Book> booksToExclude = author.getAuthoredBooks();
		final List<Long> selectedBooksId = new ArrayList<>();
		for (final Book book : booksToExclude)
			selectedBooksId.add(book.getId());
		final TypedQuery<Book> queryBooksHavingThisAuthorButNotSelected = em
				.createQuery(
						"SELECT b FROM Book b JOIN b.authors a WHERE a.id = :authorId AND b.id NOT IN :booksIdList",
						Book.class);
		queryBooksHavingThisAuthorButNotSelected.setParameter("authorId",
				author.getId());
		queryBooksHavingThisAuthorButNotSelected.setParameter("booksIdList",
				selectedBooksId);
		removeAuthorFromBooks(author,
				queryBooksHavingThisAuthorButNotSelected.getResultList());
	}

	private void removeAuthorFromAllBooks(final Author author) {
		final TypedQuery<Book> query = em.createQuery(
				"SELECT b FROM Book b JOIN b.authors a WHERE a.id = :authorId",
				Book.class);
		query.setParameter("authorId", author.getId());
		removeAuthorFromBooks(author, query.getResultList());
	}

	private void removeAuthorFromBooks(final Author author, final List<Book> resultList) {
		for (final Book book : resultList) {
			book.getAuthors().remove(author);
			em.merge(book);
		}
	}

}
