package com.arzhov.bookstore.servlet.books;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.arzhov.bookstore.jpa.Book;

@WebServlet(name = "EditBookServlet", urlPatterns = {"/books/edit"})
public class EditBookServlet extends AbstractBookServlet {
    private static final long serialVersionUID = 1L;            

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final Book book = bookEJB.find(getBookFromParams(request).getId());
        request.setAttribute("book", book);
        request.setAttribute("allAuthors", authorEJB.findAll());        
        request.getRequestDispatcher("/books/editBook.jsp").forward(request, response);
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        validateBook(request);
        if (checkForValidationMessages(request)) {
            processForEditing(request, response, "/books/editBook.jsp");
        } else {
            final Book book = getBookFromParams(request);
            bookEJB.edit(book);
            response.sendRedirect(request.getContextPath() + "/books/all");
        }
    }
}
