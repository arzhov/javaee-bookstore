<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form action="<%=request.getContextPath()%>${param.formAction}" method="POST">
    
    <input type="hidden" name="book.id" value="${book.id}" />
    
    <h1>${empty param.formTitle ? 'Author Form' : param.formTitle}</h1>
    
    <jsp:include page="../validationMessages.jsp" />
    <table>                    
        <tr>
            <td>Title:</td><td><input type="text" name="book.title" value="${book.title}"></td>            
        </tr>
        <tr>
            <td>Price:</td><td><input type="text" name="book.price" value="${book.price}">$</td>            
        </tr>
        <tr>
            <td>ISBN: </td><td><input type="text" name="book.isbn" value="${book.isbn}"></td>                        
        </tr>
        <tr>
            <td>Authors: </td>
            <td>
                <select multiple="multiple" name="book.authors">
                    <c:forEach items="${allAuthors}" var="author">
                        <option ${book.authors.contains(author) ? 'selected="selected"': ""}
                            value="${author.id}">${author.firstName} ${author.lastName}</option>
                    </c:forEach>                                
                </select>
            </td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value = "${empty param.buttonLable ? 'Submit' : param.buttonLable}"></td>
        </tr>
    </table>            
</form>

