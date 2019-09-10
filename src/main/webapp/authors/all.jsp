<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:include page="../main-template.jsp">
    <jsp:param name="title" value="All Books"/>    
    <jsp:param name="pageContent" value="/authors/allAuthorsTable.jsp"/>
    <jsp:param name="pageSidebar" value="/authors/authorsSidebar.jsp"/>        
</jsp:include>