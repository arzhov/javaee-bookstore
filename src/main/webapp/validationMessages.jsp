<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${validationMessages.size() > 0}">
    <table>
        <c:forEach items="${validationMessages}" var="validation">
            <tr>
                <td style="color: red; font-weight: bold;">${validation.fieldName}</td><td style="color: red;">${validation.message}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>  