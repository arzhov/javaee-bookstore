package com.arzhov;


import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.arzhov.bookstore.ejb.BookService;
import com.arzhov.bookstore.jpa.Book;


@RequestScoped
@Path("books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {

	@Inject
	BookService bookService;

	@GET
	public Response getAll() {
		return Response.ok(bookService.findRange(new int[] {0, 100})).build();
	}

	@GET
	@Path("{id}")
	public Response getBook(@PathParam("id") final Long id) {
		final Book book = bookService.find(id);

		return Response.ok(book).build();
	}

	@PUT
	@Path("{id}")
	public Response update(@PathParam("id") final Long id, final Book book) {
		final Book updateBook = bookService.find(id);

		updateBook.setTitle(book.getTitle());
		updateBook.setPrice(book.getPrice());
		updateBook.setIsbn(book.getIsbn());
		bookService.edit(updateBook);

		return Response.ok().build();
	}

	@POST
	public Response create(final Book book) {
		bookService.create(book);
		return Response.ok().build();
	}

	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") final Long id) {
		final Book book = bookService.find(id);

		bookService.remove(book);

		return Response.ok().build();
	}

}


