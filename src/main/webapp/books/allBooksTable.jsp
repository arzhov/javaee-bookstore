<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>All Books</h1>
<table class="newspaper">
    <thead>
        <tr>
            <th>Title</th><th>Price</th><th>ISBN</th><th>Authors</th><th>Delete</th><th>Edit</th>                        
        </tr>
    </thead>

    <% pageContext.setAttribute("allBooks", request.getAttribute("allBooks"));%>

    <tbody>
    <c:forEach items="${allBooks}" var="book">
        <tr>
            <td>${book.title}</td><td>${book.price}</td><td>${book.isbn}</td>
            <td><c:forEach items="${book.authors}" var="author">
                    <div>${author.firstName} ${author.lastName}</div>
                </c:forEach>
            </td>
            <td><a href="<%=request.getContextPath()%>/books/delete?book.id=${book.id}">delete</a></td>
            <td><a href="<%=request.getContextPath()%>/books/edit?book.id=${book.id}">edit</a></td>
        </tr>
    </c:forEach>
    </tbody>
    <tfoot>
        <tr>
            <td colspan="6">                
                <jsp:include page="../pagination.jsp">
                    <jsp:param name="address" value="/books/all"/>
                    <jsp:param name="pageSizeParameterName" value="allBooksPageSize"/>
                </jsp:include>
            </td>                        
        </tr>
    </tfoot>
</table>