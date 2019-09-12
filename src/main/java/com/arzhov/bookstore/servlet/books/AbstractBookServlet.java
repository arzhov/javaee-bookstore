package com.arzhov.bookstore.servlet.books;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.arzhov.bookstore.ejb.AuthorService;
import com.arzhov.bookstore.ejb.BookService;
import com.arzhov.bookstore.jpa.Book;
import com.arzhov.bookstore.jpa.validation.ValidationMessage;
import com.arzhov.bookstore.jpa.validation.ValidationUtils;

public abstract class AbstractBookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final String BOOK_TITLE_PARAM = "book.title";
    public static final String BOOK_PRICE_PARAM = "book.price";
    public static final String BOOK_ISBN_PARAM = "book.isbn";
    public static final String BOOK_ID_PARAM = "book.id";
    public static final String BOOK_AUTHORS_PARAM = "book.authors";
    
    @EJB
    protected AuthorService authorEJB;
    
    @EJB
    protected BookService bookEJB;
    
    @Inject
    protected ValidationUtils validationUtils;

    protected Book getBookFromParams(final HttpServletRequest request) {

        final String title = request.getParameter(BOOK_TITLE_PARAM);
        final String priceValue = request.getParameter(BOOK_PRICE_PARAM);
        
        final Book book = new Book();
        
        if (priceValue != null && !priceValue.isEmpty()) {
            try {
                final BigDecimal price = new BigDecimal(priceValue);
                book.setPrice(price);
            } catch(final NumberFormatException e) {
                validationUtils.addValidationMessage(request, new ValidationMessage("price", "Invalid price"));
            }
        }
        final String isbn = request.getParameter(BOOK_ISBN_PARAM);
        final String idValue = request.getParameter(BOOK_ID_PARAM);
        
        book.setId(idValue != null && !idValue.isEmpty() ? Long.valueOf(idValue) : null);
        book.setTitle(title);
        book.setIsbn(isbn);        

        final String[] selectedAuthorIds = request.getParameterValues(BOOK_AUTHORS_PARAM);
        if (selectedAuthorIds != null && selectedAuthorIds.length > 0) {
            book.setAuthors(new HashSet<>());
            for (final String authorId : selectedAuthorIds) {
                book.getAuthors().add(authorEJB.find(Long.valueOf(authorId)));
            }
        }
        return book;
    }

    protected void validateBook(final HttpServletRequest request) {
        final Book book = getBookFromParams(request);
        validationUtils.validateObject(request, book);
    }

    protected boolean checkForValidationMessages(final HttpServletRequest request) {
        return validationUtils.areThereValidationMessages(request);
    }

    protected void processForEditing(final HttpServletRequest request, final HttpServletResponse response, final String editingPage) throws ServletException, IOException {
        final Book book = getBookFromParams(request);
        request.setAttribute("allAuthors", authorEJB.findAll());
        request.setAttribute("book", book);
        request.getRequestDispatcher(editingPage).forward(request, response);
    }

    

}
