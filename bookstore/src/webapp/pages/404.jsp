<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset = "UTF-8">
    <title>Страница не найдена</title>
    <link rel = "stylesheet" type = "text/css"
          href = "${pageContext.request.contextPath}/resources/css/styles.css">
</head>
<body>

<jsp:include page = "header.jsp"/>

<div class = "page-title" style = "text-align: center;">404</div>
<div style = "text-align: center;">Страница не найдена</div>

<jsp:include page = "footer.jsp"/>

</body>
</html>
