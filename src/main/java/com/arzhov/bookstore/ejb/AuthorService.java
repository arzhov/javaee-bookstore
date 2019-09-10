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
	public void create(Author author) {
		Set<Book> authoredBooks = author.getAuthoredBooks();
		if (authoredBooks != null && authoredBooks.size() > 0) {
			for (Book book : authoredBooks) {
				if (book.getAuthors() == null) {
					book.setAuthors(new HashSet<Author>());
				}
				book.getAuthors().add(author);
				em.merge(book);
			}
		}
		em.persist(author);
	}

	@Override
	public void edit(Author author) {
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

	 public void findByName(String name) {
	 @SuppressWarnings("unused")
	 TypedQuery<Author> query = em.createQuery(
	"SELECT * FROM Author a WHERE a.firstname like " + name,
	Author.class);
	 }

	private void removeAuthorFromBooks(Author author) {
		Collection<Book> booksToExclude = author.getAuthoredBooks();
		List<Long> selectedBooksId = new ArrayList<Long>();
		for (Book book : booksToExclude)
			selectedBooksId.add(book.getId());
		TypedQuery<Book> queryBooksHavingThisAuthorButNotSelected = em
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

	private void removeAuthorFromAllBooks(Author author) {
		TypedQuery<Book> query = em.createQuery(
				"SELECT b FROM Book b JOIN b.authors a WHERE a.id = :authorId",
				Book.class);
		query.setParameter("authorId", author.getId());
		removeAuthorFromBooks(author, query.getResultList());
	}

	private void removeAuthorFromBooks(Author author, List<Book> resultList) {
		for (Book book : resultList) {
			book.getAuthors().remove(author);
			em.merge(book);
		}
	}

}
