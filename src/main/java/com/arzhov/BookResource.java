package com.arzhov;


import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.arzhov.bookstore.ejb.BookService;


@RequestScoped
@Path("books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {

	@Inject
	BookService bookService;

	@GET
	public Response getAll() {
		return Response.ok(bookService.findAll()).build();
	}

/*	@GET
	@Path("{id}")
	public Response getTodo(@PathParam("id") Long id) {
		Todo todo = todoDAO.findById(id);

		return Response.ok(todo).build();
	}

	@PUT
	@Path("{id}")
	public Response update(@PathParam("id") Long id, Todo todo) {
		Todo updateTodo = todoDAO.findById(id);

		updateTodo.setTask(todo.getTask());
		updateTodo.setDescription(todo.getDescription());
		todoDAO.update(updateTodo);

		return Response.ok().build();
	}

	@POST
	public Response create(Todo todo) {
		todoDAO.create(todo);
		return Response.ok().build();
	}

	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") Long id) {
		Todo getTodo = todoDAO.findById(id);

		todoDAO.delete(getTodo);

		return Response.ok().build();
	}*/

}


