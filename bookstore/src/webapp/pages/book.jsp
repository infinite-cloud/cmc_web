<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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

<div class = "page-title" style = "text-align: center;">${bookEntity.bookName}</div>

Picture Placeholder

<table style = "text-align: left" align = "center" border = "1">
    <tr>
        <td>Описание</td>
        <td>${bookEntity.description}</td>
    </tr>

    <tr>
        <td>Жанр</td>
        <td>${bookEntity.genreId.genreName}</td>
    </tr>

    <tr>
        <td>Обложка</td>
        <td>${bookEntity.coverTypeId.coverTypeName}</td>
    </tr>

    <tr>
        <td>Издательство</td>
        <td>${bookEntity.publisherId.publisherName}</td>
    </tr>

    <tr>
        <td>Авторы</td>
        <td>
            <c:forEach items = "${bookAuthors}" var = "author">
                ${author.authorName}<br/>
            </c:forEach>
        </td>
    </tr>

    <tr>
        <td>Количество страниц</td>
        <td>${bookEntity.pageCount}</td>
    </tr>

    <tr>
        <td>Год издания</td>
        <td>
            <c:set var = "publicationDate" value = "${bookEntity.publicationDate}"/>
            <c:set var = "publicationYear" value = "${fn:substring(publicationDate, 0, 4)}"/>
            ${publicationYear}
        </td>
    </tr>

    <tr>
        <td>Цена</td>
        <td>${bookEntity.bookPrice} руб.</td>
    </tr>
</table>

<jsp:include page = "footer.jsp"/>

</body>
</html>