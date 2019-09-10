package com.arzhov.bookstore.servlet.authors;

import javax.ejb.EJB;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.arzhov.bookstore.ejb.AuthorService;
import com.arzhov.bookstore.ejb.JPAService;
import com.arzhov.bookstore.servlet.PaginationServlet;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = {"/authors/all", "/authors/"}, initParams = {@WebInitParam(name = "DEFAULT_PAGE_SIZE", value = "2")})
public class AllAuthorsServlet extends PaginationServlet {

    @EJB
    private AuthorService authorService;
    
    @Override
    protected JPAService<?> getJPAService() {
        return authorService;
    }

    @Override
    protected String getCollectionName() {
        return "allAuthors";
    }

    @Override
    protected String getJSPPageName() {
        return "/authors/all.jsp";
    }   

    @Override
    protected String getPageSizeParameterName() {
        return "allAuthorsPageSize";
    }
}
