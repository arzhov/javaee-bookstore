package com.arzhov.bookstore.servlet.books;

import javax.ejb.EJB;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.arzhov.bookstore.ejb.BookService;
import com.arzhov.bookstore.ejb.JPAService;
import com.arzhov.bookstore.servlet.PaginationServlet;

@WebServlet(name = "AllBooksServlet", urlPatterns = {"/books/all", "/books/"},  initParams = {@WebInitParam(name = "DEFAULT_PAGE_SIZE", value = "2")})
public class AllBooksServlet extends PaginationServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private BookService bookService;
    
    @Override
    protected JPAService<?> getJPAService() {
        return bookService;
    }

    @Override
    protected String getCollectionName() {
        return "allBooks";
    }

    @Override
    protected String getJSPPageName() {
        return "/books/all.jsp";
    }

    @Override
    protected String getPageSizeParameterName() {
        return "allBooksPageSize";
    }	
}
