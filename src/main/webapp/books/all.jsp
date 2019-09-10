<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:include page="../main-template.jsp">
	<jsp:param name="pageTitle" value="All Books" />
	<jsp:param name="pageContent" value="/books/allBooksTable.jsp" />
	<jsp:param name="pageSidebar" value="/books/booksSidebar.jsp" />
</jsp:include>