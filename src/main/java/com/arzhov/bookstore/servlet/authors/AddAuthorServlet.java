package com.arzhov.bookstore.servlet.authors;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AddAuthorServlet", urlPatterns = {"/authors/add"})
public class AddAuthorServlet extends AbstractAuthorServlet {
    private static final long serialVersionUID = 8202293596281872305L;   
    
    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        
        validateAuthor(request);                
        if (checkForValidationMessages(request)) {
            proceedForEditing(request, response, "/authors/add.jsp");
        } else {
            createAuthor(request);            
            response.sendRedirect(request.getContextPath() + "/authors/all");
        }               
    }
    
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("allBooks", bookEJB.findAll());
        request.getRequestDispatcher("/authors/add.jsp").forward(request, response);
    }

}
