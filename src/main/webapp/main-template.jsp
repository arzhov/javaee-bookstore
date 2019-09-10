<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/default.css" type="text/css" />
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/table.css" type="text/css" />
        <title>${empty param.pageTitle ? 'Welcome to JEE Demo' : param.pageTitle}</title>
    </head>
    <body>                        

        <div id="container">
            <div id="header" class="container-item">
                <jsp:include page="${empty param.pageHeader ? 'defaultHeader.jsp' : param.pageHeader}"/>                  
            </div>
            <div id="sidebar" class="container-item">
                <jsp:include page="${empty param.pageSidebar ? 'defaultHeader.jsp' : param.pageSidebar}"/>                  
            </div>
            <div id="content" class="container-item">
                <jsp:include page="${empty param.pageContent ? 'defaultContent.jsp' : param.pageContent}"/>                  
            </div>
            <div id="footer" class="container-item">
                Footer
            </div>
        </div>
    </body>
</html>