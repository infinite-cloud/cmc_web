<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script type = "text/javascript" src = "http://code.jquery.com/jquery-1.7.1.min.js"></script>
<!DOCTYPE html>
<html>
<head>
    <meta charset = "UTF-8">
    <title>Редактировать книгу</title>
    <link rel = "stylesheet" type = "text/css"
          href = "${pageContext.request.contextPath}/resources/css/styles.css">
    <script src = "${pageContext.request.contextPath}/resources/javascript/new-selector.js"></script>

    <script type = "text/javascript">
        $('#reset').live('click', function() {
            sessionStorage.clear();
            $('#selector0').val('');
        });
    </script>
</head>
<body>

<jsp:include page = "header.jsp"/>
<jsp:include page = "menu.jsp"/>

<div class = "page-title" style = "text-align: center;"><b>Редактировать книгу</b></div>

<form:form modelAttribute = "bookForm" method = "POST"
           action = "${pageContext.request.contextPath}/editBook"
           enctype = "multipart/form-data">
    <form:input path = "needsImage" type = "hidden" value = "false"/>
    <form:input path = "bookId" type = "hidden" value = "${editedBook.bookId}"/>
    <table style = "text-align: left" align = "center">
        <tr>
            <th>Название</th>
            <td>
                <label>
                    <form:input path = "bookName" value = "${editedBook.bookName}"/>
                </label>
            </td>
            <td>
                <form:errors path = "bookName" class = "error-message"/>
            </td>
        </tr>

        <tr>
            <th>Описание</th>
            <td>
                <label>
                    <textarea name = "description">${editedBook.description}</textarea>
                </label>
            </td>
            <td>
                <form:errors path = "description" class = "error-message"/>
            </td>
        </tr>

        <tr>
            <th>Жанр</th>
            <td>
                <label>
                    <form:select path = "genreId" id = "genre">
                        <option value = ""></option>
                        <c:forEach items = "${genres}" var = "genre">
                            <c:choose>
                                <c:when test = "${genre.genreId == editedBook.genreId.genreId}">
                                    <option value = "${genre.genreId}" selected>${genre.genreName}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value = "${genre.genreId}">${genre.genreName}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </form:select>
                </label>
            </td>
            <td>
                <form:errors path = "genreId" class = "error-message"/>
            </td>
        </tr>

        <tr>
            <th>Обложка</th>
            <td>
                <label>
                    <form:select path = "coverTypeId" id = "cover">
                        <option value = ""></option>
                        <c:forEach items = "${covers}" var = "cover">
                            <c:choose>
                                <c:when test = "${cover.coverTypeId == editedBook.coverTypeId.coverTypeId}">
                                    <option value = "${cover.coverTypeId}" selected>${cover.coverTypeName}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value = "${cover.coverTypeId}">${cover.coverTypeName}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </form:select>
                </label>
            </td>
            <td>
                <form:errors path = "coverTypeId" class = "error-message"/>
            </td>
        </tr>

        <tr>
            <th>Издательство</th>
            <td>
                <label>
                    <form:select path = "publisherId" id = "publisher">
                        <option value = ""></option>
                        <c:forEach items = "${publishers}" var = "publisher">
                            <c:choose>
                                <c:when test = "${publisher.publisherId == editedBook.publisherId.publisherId}">
                                    <option value = "${publisher.publisherId}" selected>
                                            ${publisher.publisherName}
                                    </option>
                                </c:when>
                                <c:otherwise>
                                    <option value = "${publisher.publisherId}">${publisher.publisherName}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </form:select>
                </label>
            </td>
            <td>
                <form:errors path = "publisherId" class = "error-message"/>
            </td>
        </tr>

        <tr>
            <th>Авторы</th>
            <td>
                <div id = 'selectorGroup'>
                    <div style = "display: none;">
                        <label>
                            <select id = "selector" class = "selectors">
                                <option value = ""></option>
                                <c:forEach items = "${authors}" var = "author">
                                    <option value = "${author.authorId}">${author.authorName}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>
                    <c:forEach items = "${editedBookAuthors}" var = "editedBookAuthor" varStatus = "loop">
                        <div id = "selectorDiv${loop.index}">
                            <label>
                                <select name = "bookAuthors[${loop.index}]"
                                        id = "selector${loop.index}" class = "selectors">
                                    <option value = ""></option>
                                    <c:forEach items = "${authors}" var = "author">
                                        <c:choose>
                                            <c:when test = "${author.authorId == editedBookAuthor.authorId}">
                                                <option value = "${author.authorId}" selected>
                                                        ${author.authorName}
                                                </option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value = "${author.authorId}">${author.authorName}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                            </label>
                        </div>
                    </c:forEach>
                </div>
                <input type = "button" value = "+" id = "addButton">
                <input type = "button" value = "–" id = "removeButton">
            </td>
            <td>
                <form:errors path = "bookAuthors" class = "error-message"/>
            </td>
        </tr>

        <tr>
            <th>Количество страниц</th>
            <td>
                <label>
                    <form:input path = "pageCount" type = "number" value = "${editedBook.pageCount}"/>
                </label>
            </td>
            <td>
                <form:errors path = "pageCount" class = "error-message"/>
            </td>
        </tr>

        <tr>
            <th>Дата издания</th>
            <td>
                <label>
                    <form:input path = "publicationDate" type = "date" value = "${editedBook.publicationDate}"/>
                </label>
            </td>
            <td>
                <form:errors path = "publicationDate" class = "error-message"/>
            </td>
        </tr>

        <tr>
            <th>Наличие (шт.)</th>
            <td>
                <label>
                    <form:input path = "availableCount" type = "number" value = "${editedBook.availableCount}"/>
                </label>
            </td>
            <td>
                <form:errors path = "availableCount" class = "error-message"/>
            </td>
        </tr>

        <tr>
            <th>Изображение</th>
            <td>
                <label>
                    <form:input path = "image" type = "file"/>
                </label>
            </td>
            <td>
                <form:errors path = "image" class = "error-message"/>
            </td>
        </tr>

        <tr>
            <th>Цена (руб.)</th>
            <td>
                <label>
                    <form:input path = "bookPrice" type = "number" step = "0.01" value = "${editedBook.bookPrice}"/>
                </label>
            </td>
            <td>
                <form:errors path = "bookPrice" class = "error-message"/>
            </td>
        </tr>

        <tr>
            <td>&nbsp;</td>
            <td>
                <label>
                    <input type = "submit" value = "Сохранить"/>
                    <a id = "reset" href = "<c:url value = '/'/>">Отмена</a>
                </label>
            </td>
        </tr>
    </table>
</form:form>

<jsp:include page = "footer.jsp"/>

</body>
</html>
