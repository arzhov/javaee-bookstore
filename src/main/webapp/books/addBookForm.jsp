<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="bookForm.jsp">
    <jsp:param name="formAction" value="/books/new"/>
    <jsp:param name="formTitle" value="Add Book"/>
    <jsp:param name="buttonLable" value="Add"/>
</jsp:include>