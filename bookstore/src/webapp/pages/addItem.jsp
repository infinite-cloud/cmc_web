<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script type = "text/javascript" src = "http://code.jquery.com/jquery-1.7.1.min.js"></script>
<!DOCTYPE html>
<html>
<head>
    <meta charset = "UTF-8">
    <%String title = "";%>
    <c:choose>
        <c:when test = "${param.publisher != null}">
            <%title = "Добавить издательство";%>
        </c:when>
        <c:when test = "${param.genre != null}">
            <%title = "Добавить жанр";%>
        </c:when>
        <c:when test = "${param.coverType != null}">
            <%title = "Добавить обложку";%>
        </c:when>
        <c:when test = "${param.author != null}">
            <%title = "Добавить автора";%>
        </c:when>
        <c:otherwise>
            <%title = "Ошибка";%>
        </c:otherwise>
    </c:choose>
    <title><%=title%></title>
    <link rel = "stylesheet" type = "text/css"
          href = "${pageContext.request.contextPath}/resources/css/styles.css">
</head>
<body>

<jsp:include page = "header.jsp"/>

<div class = "page-title" style = "text-align: center;"><b><%=title%></b></div>

<%request.setAttribute("title", title);%>

<c:if test = "${title != 'Ошибка'}">
    <form:form modelAttribute = "itemForm" method = "POST">
        <table align = "center">
            <tr>
                <td><b>Наименование</b></td>
                <td><label><form:input path = "value"/></label></td>
            </tr>

            <tr>
                <td>&nbsp;</td>
                <td>
                    <label>
                        <input type = "submit" value = "Добавить">
                        <a id = "reset" href = "<c:url value = '/'/>">Отмена</a>
                    </label>
                </td>
            </tr>
        </table>
    </form:form>
</c:if>

<jsp:include page = "footer.jsp"/>

</body>
</html>
