package com.arzhov.bookstore.servlet.authors;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DeleteAuthorServlet", urlPatterns = {"/authors/delete"})
public class DeleteAuthorServlet extends AbstractAuthorServlet {
    private static final long serialVersionUID = 1L;
    
    private static final Logger log = Logger.getLogger(DeleteAuthorServlet.class.getName());

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        log.info(getAuthorFromRequestParameters(request).toString());
        authorEJB.remove(getAuthorFromRequestParameters(request));
        request.setAttribute("allAuthors", authorEJB.findAll());
        request.getRequestDispatcher("/authors/all").forward(request, response);
    }
}
