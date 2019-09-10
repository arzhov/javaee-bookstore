<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="authorForm.jsp">
    <jsp:param name="formAction" value="/authors/edit"/>
    <jsp:param name="formTitle" value="Edit Author"/>
    <jsp:param name="buttonLable" value="Edit"/>
</jsp:include>