<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:include page="../main-template.jsp">
	<jsp:param name="pageTitle" value="Edit Book" />
	<jsp:param name="pageContent" value="books/editBookForm.jsp" />
	<jsp:param name="pageSidebar" value="/books/booksSidebar.jsp" />
</jsp:include>
