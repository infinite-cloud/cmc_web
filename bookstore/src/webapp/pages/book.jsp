<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset = "UTF-8">
    <title>${bookEntity.bookName}</title>
    <link rel = "stylesheet" type = "text/css"
          href = "${pageContext.request.contextPath}/resources/css/styles.css">
</head>
<body>

<jsp:include page = "header.jsp"/>
<jsp:include page = "menu.jsp"/>

<div class = "page-title" style = "text-align: center;"><b>${bookEntity.bookName}</b></div>

<img src = "${pageContext.request.contextPath}/resources/images/${bookEntity.imageName}" alt = ""/>

<table style = "text-align: left" align = "center" border = "1">
    <tr>
        <th>Описание</th>
        <td>${bookEntity.description}</td>
    </tr>

    <tr>
        <th>Жанр</th>
        <td>${bookEntity.genreId.genreName}</td>
    </tr>

    <tr>
        <th>Обложка</th>
        <td>${bookEntity.coverTypeId.coverTypeName}</td>
    </tr>

    <tr>
        <th>Издательство</th>
        <td>${bookEntity.publisherId.publisherName}</td>
    </tr>

    <tr>
        <th>Авторы</th>
        <td>
            <c:forEach items = "${bookAuthors}" var = "author">
                ${author.authorName}<br/>
            </c:forEach>
        </td>
    </tr>

    <tr>
        <th>Количество страниц</th>
        <td>${bookEntity.pageCount}</td>
    </tr>

    <tr>
        <th>Дата издания</th>
        <td>
            <c:set var = "publicationDate" value = "${bookEntity.publicationDate}"/>
            <c:set var = "publicationYear" value = "${fn:substring(publicationDate, 0, 4)}"/>
            <c:set var = "publicationMonth" value = "${fn:substring(publicationDate, 5, 7)}"/>
            <c:set var = "publicationDay" value = "${fn:substring(publicationDate, 8, 10)}"/>
            ${publicationDay}.${publicationMonth}.${publicationYear}
        </td>
    </tr>

    <tr>
        <th>Наличие</th>
        <td>${bookEntity.availableCount} шт.</td>
    </tr>

    <tr>
        <th>Цена</th>
        <td>${bookEntity.bookPrice} руб.</td>
    </tr>
</table>

<div style = "text-align: center;">
    <security:authorize access = "hasAnyRole('ROLE_USER', 'ROLE_ADMIN')">
        <a href = "<c:url value = '/addToCart/${bookEntity.bookId}'/>">В корзину</a>

        <c:if test = "${param.addedToCart == true}">
            Товар добавлен в корзину
        </c:if>
        <c:if test = "${param.addedToCart == false}">
            На складе недостаточно экземпляров
        </c:if>
    </security:authorize>
    <security:authorize access = "hasRole('ROLE_ADMIN')">
        &nbsp;
        <a href = "<c:url value = '/deleteBook/${bookEntity.bookId}'/>">Удалить</a>
        &nbsp;
        <a href = "<c:url value = '/editBook?id=${bookEntity.bookId}'/>">Редактировать</a>
    </security:authorize>
</div>

<jsp:include page = "footer.jsp"/>

</body>
</html>