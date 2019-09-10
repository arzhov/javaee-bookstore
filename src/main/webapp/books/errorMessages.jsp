<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
	pageContext.setAttribute("errors",
			request.getAttribute("errorMessages"));
%>

<c:if test='${errors.size() > 0}'>
	<table>
		<c:forEach items="${errors}" var="error">
			<tr>
				<td>${error}</td>
			</tr>
		</c:forEach>
	</table>
</c:if>