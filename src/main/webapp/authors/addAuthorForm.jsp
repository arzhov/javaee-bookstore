<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="authorForm.jsp">
    <jsp:param name="formAction" value="/authors/add"/>
    <jsp:param name="formTitle" value="Add Author"/>
    <jsp:param name="buttonLable" value="Add"/>
</jsp:include>
