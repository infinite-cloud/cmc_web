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
            <%title = "Удалить издательство";%>
        </c:when>
        <c:when test = "${param.genre != null}">
            <%title = "Удалить жанр";%>
        </c:when>
        <c:when test = "${param.coverType != null}">
            <%title = "Удалить обложку";%>
        </c:when>
        <c:when test = "${param.author != null}">
            <%title = "Удалить автора";%>
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
<jsp:include page = "menu.jsp"/>

<div class = "page-title" style = "text-align: center;"><b><%=title%></b></div>

<%request.setAttribute("title", title);%>

<c:if test = "${param.itemRemoved == true}">
    Элемент удалён
</c:if>

<c:if test = "${title != 'Ошибка'}">
<table align = "center" border = "1">
    <tr>
        <th>Наименование</th>
    </tr>

    <c:choose>
        <c:when test = "${title == 'Удалить автора'}">
            <c:forEach items = "${authors}" var = "author">
                <tr>
                    <td>${author.authorName}</td>
                    <td>
                        <a href = "<c:url value = '/removeItem/author/${author.authorId}'/>">Удалить</a>
                    </td>
                </tr>
            </c:forEach>
        </c:when>
        <c:when test = "${title == 'Удалить издательство'}">
            <c:forEach items = "${publishers}" var = "publisher">
                <tr>
                    <td>${publisher.publisherName}</td>
                    <td>
                        <a href = "<c:url value = '/removeItem/publisher/${publisher.publisherId}'/>">Удалить</a>
                    </td>
                </tr>
            </c:forEach>
        </c:when>
        <c:when test = "${title == 'Удалить жанр'}">
            <c:forEach items = "${genres}" var = "genre">
                <tr>
                    <td>${genre.genreName}</td>
                    <td>
                        <a href = "<c:url value = '/removeItem/genre/${genre.genreId}'/>">Удалить</a>
                    </td>
                </tr>
            </c:forEach>
        </c:when>
        <c:when test = "${title == 'Удалить обложку'}">
            <c:forEach items = "${covers}" var = "cover">
                <tr>
                    <td>${cover.coverTypeName}</td>
                    <td>
                        <a href = "<c:url value = '/removeItem/coverType/${cover.coverTypeId}'/>">Удалить</a>
                    </td>
                </tr>
            </c:forEach>
        </c:when>
    </c:choose>
</table>
</c:if>

<jsp:include page = "footer.jsp"/>

</body>
</html>
