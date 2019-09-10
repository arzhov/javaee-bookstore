<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% 
    String pageSizeParameterName = request.getParameter("pageSizeParameterName");
    if (pageSizeParameterName == null || pageSizeParameterName.isEmpty()) {
        pageSizeParameterName = "pageSize";
    }
    pageContext.setAttribute("pageSize", request.getAttribute(pageSizeParameterName));
%>

<select id="pageSize" onchange="window.location = '<%=request.getContextPath()%>${param.address}?${param.pageSizeParameterName}=' + event.target.value">
    <option ${pageSize == 2 ? 'selected' : ''} value="2">2</option>
    <option ${pageSize == 5 ? 'selected' : ''} value="5">5</option>
    <option ${pageSize == 10 ? 'selected' : ''} value="10">10</option>
</select>
<c:forEach begin="1" end="${totalNumber}" step="${pageSize}" varStatus="counter">                                    
    <c:if test="${counter.count == pageNumber}">
        <span style="margin-right: 10px; font-weight: bolder;">${counter.count}</span>
    </c:if>                    
    <c:if test="${counter.count != pageNumber}">
        <a style="text-decoration: none;" href="<%=request.getContextPath()%>${param.address}?pageNumber=${counter.count}&${param.pageSizeParameterName}=${pageSize}">
            <span style="margin-right: 10px;">${counter.count}</span>
        </a>
    </c:if>             
</c:forEach>      
