<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:include page="../main-template.jsp">
    <jsp:param name="title" value="New Book"/>    
    <jsp:param name="pageContent" value="/books/addBookForm.jsp"/>
    <jsp:param name="pageSidebar" value="/books/booksSidebar.jsp"/>    
</jsp:include>