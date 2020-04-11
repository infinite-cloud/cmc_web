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

<div class = "page-title" style = "text-align: center;"><b>${bookEntity.bookName}</b></div>

<img src = "${pageContext.request.contextPath}/resources/images/${bookEntity.imageName}"/>

<table style = "text-align: left" align = "center" border = "1">
    <tr>
        <td><b>Описание</b></td>
        <td>${bookEntity.description}</td>
    </tr>

    <tr>
        <td><b>Жанр</b></td>
        <td>${bookEntity.genreId.genreName}</td>
    </tr>

    <tr>
        <td><b>Обложка</b></td>
        <td>${bookEntity.coverTypeId.coverTypeName}</td>
    </tr>

    <tr>
        <td><b>Издательство</b></td>
        <td>${bookEntity.publisherId.publisherName}</td>
    </tr>

    <tr>
        <td><b>Авторы</b></td>
        <td>
            <c:forEach items = "${bookAuthors}" var = "author">
                ${author.authorName}<br/>
            </c:forEach>
        </td>
    </tr>

    <tr>
        <td><b>Количество страниц</b></td>
        <td>${bookEntity.pageCount}</td>
    </tr>

    <tr>
        <td><b>Год издания</b></td>
        <td>
            <c:set var = "publicationDate" value = "${bookEntity.publicationDate}"/>
            <c:set var = "publicationYear" value = "${fn:substring(publicationDate, 0, 4)}"/>
            ${publicationYear}
        </td>
    </tr>

    <tr>
        <td><b>Цена</b></td>
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
            Товара нет в наличии
        </c:if>
    </security:authorize>
</div>

<jsp:include page = "footer.jsp"/>

</body>
</html>