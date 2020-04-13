<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script type = "text/javascript" src = "http://code.jquery.com/jquery-1.7.1.min.js"></script>
<!DOCTYPE html>
<html>
<head>
    <meta charset = "UTF-8">
    <title>Добавить книгу</title>
    <link rel = "stylesheet" type = "text/css"
          href = "${pageContext.request.contextPath}/resources/css/styles.css">
    <script src = "${pageContext.request.contextPath}/resources/javascript/new-selector.js"></script>
    <script src = "${pageContext.request.contextPath}/resources/javascript/select.js"></script>

    <script type = "text/javascript">
        $('#reset').live('click', function() {
            sessionStorage.clear();
            $('#selector0').val('');
        });
    </script>

    <script type = "text/javascript">
        $('#submit').live('click', function() {
            sessionStorage.clear();
            $('#selector0').val('');
        });
    </script>
</head>
<body>

<jsp:include page = "header.jsp"/>
<jsp:include page = "menu.jsp"/>

<div class = "page-title" style = "text-align: center;"><b>Добавить книгу</b></div>

<form:form modelAttribute = "bookForm" method = "POST"
           action = "${pageContext.request.contextPath}/addBook"
           enctype = "multipart/form-data">
<table style = "text-align: left" align = "center">
    <tr>
        <th>Название</th>
        <td>
            <label>
                <form:input path = "bookName"/>
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
                <form:textarea path = "description"/>
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
                        <option value = "${genre.genreId}">${genre.genreName}</option>
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
                        <option value = "${cover.coverTypeId}">${cover.coverTypeName}</option>
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
                        <option value = "${publisher.publisherId}">${publisher.publisherName}</option>
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
                <div id = "selectorDiv0">
                    <label>
                        <select name = "bookAuthors[0]" id = "selector0" class = "selectors">
                            <option value = ""></option>
                            <c:forEach items = "${authors}" var = "author">
                                <option value = "${author.authorId}">${author.authorName}</option>
                            </c:forEach>
                        </select>
                    </label>
                </div>
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
                <form:input path = "pageCount" type = "number"/>
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
                <form:input path = "publicationDate" type = "date"/>
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
                <form:input path = "availableCount" type = "number"/>
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
                <form:input path = "bookPrice" type = "number" step = "0.01"/>
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
                <input type = "submit" value = "Добавить"/>
                <a id = "reset" href = "<c:url value = '/'/>">Отмена</a>
            </label>
        </td>
    </tr>
</table>
</form:form>

<jsp:include page = "footer.jsp"/>

</body>
</html>
