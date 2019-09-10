package com.arzhov.bookstore.servlet.authors;

import java.io.IOException;
import java.util.HashSet;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.arzhov.bookstore.ejb.AuthorService;
import com.arzhov.bookstore.ejb.BookService;
import com.arzhov.bookstore.jpa.Author;
import com.arzhov.bookstore.jpa.Book;
import com.arzhov.bookstore.jpa.validation.ValidationMessage;
import com.arzhov.bookstore.jpa.validation.ValidationUtils;


public abstract class AbstractAuthorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    public static final String FIRST_NAME_PARAM = "author.firstName";
    public static final String LAST_NAME_PARAM = "author.lastName";
    public static final String AUTHORED_BOOKS_PARAM = "author.authoredBooks";   
    public static final String ALL_BOOKS_PARAM = "allBooks";
    public static final String AUTHOR_PARAM = "author";
    
    @EJB
    protected BookService bookEJB;
    
    @EJB
    protected AuthorService authorEJB;

    @Inject
    protected ValidationUtils validationUtils;

    protected Author getAuthorFromRequestParameters(HttpServletRequest request) {
        Author author = new Author();
        author.setId(getAuthorIdFromRequest(request));
        author.setFirstName(request.getParameter(FIRST_NAME_PARAM));
        author.setLastName(request.getParameter(LAST_NAME_PARAM));

        final String[] selectedBooksIds = request.getParameterValues(AUTHORED_BOOKS_PARAM);
        if (selectedBooksIds != null && selectedBooksIds.length > 0) {            
            author.setAuthoredBooks(new HashSet<Book>());
            for (String bookId : selectedBooksIds) {
                author.getAuthoredBooks().add(bookEJB.find(Long.valueOf(bookId)));
            }            
        }
        return author;
    }

    /**
     * @param request - HttpServletRequest which is supposed to has the "id" parameter
     * @return The Long value of a request parameter "id"
     */
    protected Long getAuthorIdFromRequest(HttpServletRequest request) {
        String idValue = request.getParameter("id");
        return idValue != null && !idValue.isEmpty() ? Long.valueOf(idValue) : null;
    }
  
    /**
     * Adds a ValidationMessage in a List<ValidationMessage> object in the HttpServletRequest object.
     * @param request - The current request object
     * @param message - The ValidationMessage object
     */    
    protected void addValidationMessage(HttpServletRequest request, ValidationMessage message) {
        validationUtils.addValidationMessage(request, message);
    }

    protected boolean checkForValidationMessages(HttpServletRequest request) {
        return validationUtils.areThereValidationMessages(request);
    }

    protected void proceedForEditing(HttpServletRequest request, HttpServletResponse response, String fallbackPage) throws ServletException, IOException {
        request.setAttribute(ALL_BOOKS_PARAM, bookEJB.findAll());
        Author author = getAuthorFromRequestParameters(request);
        request.setAttribute(AUTHOR_PARAM, author);
        request.getRequestDispatcher(fallbackPage).forward(request, response);
    }

    protected void validateAuthor(HttpServletRequest request) {
        Author author = getAuthorFromRequestParameters(request);        
        validationUtils.validateObject(request, author);
    }

    protected void editAuthor(HttpServletRequest request) throws IOException {
        Author author = getAuthorFromRequestParameters(request);
        authorEJB.edit(author);
    }

    protected void createAuthor(HttpServletRequest request) {
        Author author = getAuthorFromRequestParameters(request);
        authorEJB.create(author);
    }
}