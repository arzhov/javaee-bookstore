<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:include page="../main-template.jsp">
    <jsp:param name="title" value="Edit Author"/>
    <jsp:param name="pageContent" value="/authors/editAuthorsForm.jsp"/>
    <jsp:param name="pageSidebar" value="/authors/authorsSidebar.jsp"/>        
</jsp:include>