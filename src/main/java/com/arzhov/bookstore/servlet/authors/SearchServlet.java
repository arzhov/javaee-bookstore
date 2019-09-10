package com.arzhov.bookstore.servlet.authors;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.arzhov.bookstore.ejb.AuthorService;

//import com.jee.tutorial.bookstore.jpa.Author;
//import java.util.List;
//import java.util.Enumeration;
//import com.jee.tutorial.bookstore.jpa.Author;


/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/authors/SearchServlet")
public class SearchServlet extends AbstractAuthorServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			PrintWriter writer = response.getWriter();
			writer.print("Post method of SearchServlet");
			writer.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String authorName = request.getParameter("searchCriteria");
		authorEJB = new AuthorService();
		authorEJB.findByName(authorName); 

	}

}
