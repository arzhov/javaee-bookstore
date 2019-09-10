<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form action="<%=request.getContextPath()%>${param.formAction}" method="POST">
    
    <input type="hidden" name="id" value="${param.id}" />
    
    <h1>${empty param.formTitle ? 'Author Form' : param.formTitle}</h1>
    <jsp:include page="../validationMessages.jsp"/>
    <table>                 	 	      
        <tr>
            <td>First Name</td><td><input type="text" name="author.firstName" value="${author.firstName}"></td>
        </tr>
        <tr>
            <td>Last Name</td><td><input type="text" name="author.lastName" value="${author.lastName}"></td>
        </tr> 

        <tr>
            <td>Books: </td>
            <td><select multiple="multiple" name="author.authoredBooks" style="height: 100px;">
                <c:forEach items="${allBooks}" var="book">
                        <option ${author.authoredBooks.contains(book) ? 'selected="selected"': ""} value="${book.id}">
                            ${book.title} - ${book.isbn}
                        </option>
                </c:forEach>
            </select></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value = "${empty param.buttonLable ? 'Submit' : param.buttonLable}"></td>
        </tr>        
    </table>           
</form>