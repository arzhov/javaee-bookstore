<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:include page="../main-template.jsp">
    <jsp:param name="title" value="Add Author"/>    
    <jsp:param name="pageContent" value="/authors/addAuthorForm.jsp"/>
    <jsp:param name="pageSidebar" value="/authors/authorsSidebar.jsp"/>       
</jsp:include>