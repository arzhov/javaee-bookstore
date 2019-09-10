<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="bookForm.jsp">
    <jsp:param name="formAction" value="/books/edit"/>
    <jsp:param name="formTitle" value="Edit Book"/>
    <jsp:param name="buttonLable" value="Edit"/>
</jsp:include>