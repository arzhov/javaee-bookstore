package com.arzhov.bookstore.servlet.authors;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.arzhov.bookstore.jpa.Author;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/authors/SearchServlet")
public class SearchServlet extends AbstractAuthorServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(final HttpServletRequest request,
	                     final HttpServletResponse response) throws ServletException, IOException {
		try {
			final PrintWriter writer = response.getWriter();
			writer.print("Use Post method of SearchServlet");
			writer.close();
		} catch (final IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(final HttpServletRequest request,
	                      final HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		final String authorName = request.getParameter("searchCriteria");
		final List<Author> findAuthors = authorEJB.findByName(authorName);

		request.setAttribute("allAuthors", findAuthors);
		request.getRequestDispatcher("/authors/findAuthors.jsp").forward(request, response);

	}

}
