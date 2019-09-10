<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>All Authors</h1>
<table class="newspaper">
    <thead>
        <tr>
            <th>First Name</th><th>Last Name</th><th>Books</th><th>Delete</th><th>Edit</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach items="${requestScope.allAuthors}" var="author">
     <tr>
    	<td>${author.firstName}</td>
        <td>${author.lastName}</td>
    	<td>
    		<c:forEach items="${author.authoredBooks}" var="book">
            	<div> ${book.title} </div>    
            </c:forEach>
        </td>             
    	<td><a href="<%=request.getContextPath()%>/authors/delete?id=${author.id}">delete</a></td>
    	<td><a href="<%=request.getContextPath()%>/authors/edit?id=${author.id}">edit</a></td>
    </tr>        
    </c:forEach>
   <tbody>
   
   <tfoot>
        <tr>
            <td colspan="5">                
                <jsp:include page="../pagination.jsp">
                    <jsp:param name="address" value="/authors/all"/>
                    <jsp:param name="pageSizeParameterName" value="allAuthorsPageSize"/>
                </jsp:include>
            </td>                        
        </tr>
    </tfoot>
   
</table>
