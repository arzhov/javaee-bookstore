package com.arzhov.bookstore.servlet.authors;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.arzhov.bookstore.jpa.Author;

@WebServlet(name = "EditAuthorServlet", urlPatterns = {"/authors/edit"})
public class EditAuthorServlet extends AbstractAuthorServlet {
    private static final long serialVersionUID = 1L;    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Author author = authorEJB.find(getAuthorIdFromRequest(request));
        request.setAttribute("author", author);   
        request.setAttribute("allBooks", bookEJB.findAll());
        request.getRequestDispatcher("/authors/edit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validateAuthor(request);                
        if (checkForValidationMessages(request)) {
            proceedForEditing(request, response, "/authors/edit.jsp");
        } else {
            editAuthor(request);            
            response.sendRedirect(request.getContextPath() + "/authors/all");
        }               
    }
}
