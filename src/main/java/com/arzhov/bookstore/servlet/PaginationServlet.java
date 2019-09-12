package com.arzhov.bookstore.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.arzhov.bookstore.ejb.JPAService;


@SuppressWarnings("serial")
public abstract class PaginationServlet extends HttpServlet {        
    public static final String PAGE_NUMBER_PARAM = "pageNumber";
    public static final String PAGE_TOTAL_NUMBER_PARAM = "totalNumber";
    private static final int DEFAULT_PAGE_SIZE = 2;
    
    /**
     * For each subclass there should be a different page size paramete name;
     * This is needed because each user's preference of the page size
     * should be stored once in session and as a HTTP Cooky
     * @return 
     */
    protected abstract String getPageSizeParameterName();
    
    /**
     * A template method aiming to abstract the derivative classes 
     * from the concrete implementation of ranging methods
     * @return - A JPAService working with a specific entity
     */    
    protected abstract JPAService<?> getJPAService();
    
    /**
     * The HttpServletRequest's attribute name that will point to the collection
     * returned by the getJPAService
     * @return 
     */
    protected abstract String getCollectionName();
    
    /**
     * The page that will the request be forwarded to.
     * Example: "/authors/all.jsp"
     * @return 
     */
    protected abstract String getJSPPageName();
    
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final int pageNumber = getPageNumber(request);
        final int pageSize = getPageSize(request);     
        
        request.setAttribute(getPageSizeParameterName(), pageSize);
        request.setAttribute(PAGE_NUMBER_PARAM, pageNumber);
        request.setAttribute(PAGE_TOTAL_NUMBER_PARAM, getJPAService().count());        

        final int[] range = {(pageNumber - 1) * pageSize, pageNumber * pageSize};
        final List<?> resultList = getJPAService().findRange(range);

        request.setAttribute(getCollectionName(), resultList);
        request.getRequestDispatcher(getJSPPageName()).forward(request, response);
    }

    /**     
     * @param request
     * @return the Integer value of page.number request parameter
     * @throws NumberFormatException if the requets paramete page.number is illegal number
     */
    private int getPageNumber(final HttpServletRequest request) {
        int pageNumber = 1;
        final String pageNumberRequestParameter = request.getParameter(PAGE_NUMBER_PARAM);
        if (!StringUtils.isBlank(pageNumberRequestParameter)) {
            pageNumber = Integer.parseInt(pageNumberRequestParameter);
        }        
        return pageNumber;            
    }

    /**     
     * @param request
     * @return the Integer value of page.size request parameter if provided; the value of "DEFAULT_PAGE_SIZE" init parameter
     * @throws NumberFormatException if the requets paramete page.size is illegal number
     */
    private int getPageSize(final HttpServletRequest request) {
        final Integer cachedPageSize = getCachedPageSize(request.getSession());
        final String pageSizeRequestParameter = request.getParameter(getPageSizeParameterName());
        if (!StringUtils.isBlank(pageSizeRequestParameter)) {
            final int requestPageSize = Integer.parseInt(pageSizeRequestParameter);
            cachePageSize(request.getSession(), requestPageSize);
            return requestPageSize;
        } else if (cachedPageSize != null){
            return cachedPageSize;
        }
        cachePageSize(request.getSession(), DEFAULT_PAGE_SIZE);
        return DEFAULT_PAGE_SIZE;            
    }    
    
    private Integer getCachedPageSize(final HttpSession session) {
        final Object cachedPageSize = session.getAttribute(getPageSizeParameterName());
        if (cachedPageSize != null) {
            try {
                return Integer.valueOf(cachedPageSize.toString());
            } catch (final NumberFormatException e) {
                super.log("NON INTEGER VALUE STORED IN SESSION'S CACHE", e);                
            } 
        }
        return null;
    }
    
    private void cachePageSize(final HttpSession session, final int pageSize) {
        session.setAttribute(getPageSizeParameterName(), pageSize);
    }  
}
