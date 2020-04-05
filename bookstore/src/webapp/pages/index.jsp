<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset = "UTF-8">
    <title>Книжный магазин</title>
    <link rel = "stylesheet" type = "text/css"
          href = "${pageContext.request.contextPath}/resources/css/styles.css">
</head>
<body>

<jsp:include page = "header.jsp"/>

<table border = "1" style = "width:100%">
    <tr>
        <th>Название</th>
        <th>Авторы</th>
        <th>Цена</th>
        <th>Наличие</th>
    </tr>
    <c:forEach items = "${bookList}" var = "book">
        <tr>
            <td>${book.key.bookName}</td>
            <td>
                <c:forEach items = "${book.value}" var = "author">
                    ${author.authorName}<br/>
                </c:forEach>
            </td>
            <td>${book.key.bookPrice} руб.</td>
            <td>
                <c:if test = "${book.key.availableCount > 0}">
                    В наличии
                </c:if>
                <c:if test = "${book.key.availableCount == 0}">
                    Нет в наличии
                </c:if>
            </td>
        </tr>
    </c:forEach>
</table>

<jsp:include page = "footer.jsp"/>

</body>
</html>