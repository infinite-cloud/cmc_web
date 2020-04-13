<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<%@ taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<script type = "text/javascript" src = "http://code.jquery.com/jquery-1.7.1.min.js"></script>
<!DOCTYPE html>
<html>
<head>
    <meta charset = "UTF-8">
    <title>Книжный магазин</title>
    <link rel = "stylesheet" type = "text/css"
          href = "${pageContext.request.contextPath}/resources/css/styles.css">
    <script src = "${pageContext.request.contextPath}/resources/javascript/toggle.js"></script>
    <script src = "${pageContext.request.contextPath}/resources/javascript/select.js"></script>
    <script src = "${pageContext.request.contextPath}/resources/javascript/new-field.js"></script>

    <script type = "text/javascript">
        $('#reset').live('click', function() {
            sessionStorage.clear();
            $('#textBox0').val('');
        });
    </script>
</head>
<body>

<jsp:include page = "header.jsp"/>
<jsp:include page = "menu.jsp"/>

<c:if test = "${param.bookAdded == true}">
    <script type = "text/javascript">
        sessionStorage.clear();
    </script>
    <div style = "text-align: center;">Книга добавлена в каталог</div>
</c:if>

<c:if test = "${param.accountDeleted == true}">
    <div style = "text-align: center;">Учётная запись удалена</div>
</c:if>

<c:if test = "${param.bookDeleted == true}">
    <div style = "text-align: center;">Книга удалена</div>
</c:if>

<c:if test = "${param.itemAdded == true}">
    <div style = "text-align: center;">Элемент добавлен в каталог</div>
</c:if>

<div class = "filter-button">
    <a id = "displayText" href = "javascript:toggle('Фильтр');">
        Фильтр
    </a>
</div>

<div id = "toggleText" style = "display: none">
    <form:form modelAttribute = "bookFilterForm" method = "POST"
               name = "filterForm" id = "filterForm">
        <table style = "text-align: left" align = "center">
            <tr>
                <td>Название</td>
                <td><form:input path = "name"/></td>
            </tr>

            <tr>
                <td>Жанр</td>
                <td>
                    <label>
                        <input type = "hidden" name = "selectedValue" value = ""/>
                        <form:select path = "genre" id = "genre">
                            <option value = ""></option>
                            <c:forEach items = "${genres}" var = "genre">
                                <option value = "${genre.genreId}">${genre.genreName}</option>
                            </c:forEach>
                        </form:select>
                    </label>
                </td>
            </tr>

            <tr>
                <td>Обложка</td>
                <td>
                    <label>
                        <input type = "hidden" name = "selectedValue" value = ""/>
                        <form:select path = "cover" id = "cover">
                            <option value = ""></option>
                            <c:forEach items = "${covers}" var = "cover">
                                <option value = "${cover.coverTypeId}">${cover.coverTypeName}</option>
                            </c:forEach>
                        </form:select>
                    </label>
                </td>
            </tr>

            <tr>
                <td>Издательство</td>
                <td>
                    <label>
                        <input type = "hidden" name = "selectedValue" value = ""/>
                        <form:select path = "publisher" id = "publisher">
                            <option value = ""></option>
                            <c:forEach items = "${publishers}" var = "publisher">
                                <option value = "${publisher.publisherId}">${publisher.publisherName}</option>
                            </c:forEach>
                        </form:select>
                    </label>
                </td>
            </tr>

            <tr>
                <td>Авторы</td>
                <td>
                    <div id = 'textBoxesGroup'>
                        <div id = "textBoxDiv">
                            <label>
                                <input type = "text" name = "authors[0]" id = "textBox0"/>
                            </label>
                        </div>
                    </div>
                    <input type = "button" value = "+" id = "addButton">
                    <input type = "button" value = "–" id = "removeButton">
                </td>
            </tr>

            <tr>
                <td>Цена</td>
                <td>
                    от <form:input path = "minPrice" type = "number" step = "0.01"/>
                    до <form:input path = "maxPrice" type = "number" step = "0.01"/>
                </td>
            </tr>

            <tr>
                <td>Количество страниц</td>
                <td>
                    от <form:input path = "minPages" type = "number"/>
                    до <form:input path = "maxPages" type = "number"/>
                </td>
            </tr>

            <tr>
                <td>Дата издания</td>
                <td>
                    от <form:input path = "minDate" type = "date"/>
                    до <form:input path = "maxDate" type = "date"/>
                </td>
            </tr>

            <tr>
                <td>Только в наличии</td>
                <td>
                    <form:checkbox path = "availability"/>
                </td>
            </tr>

            <tr>
                <td>&nbsp;</td>
                <td>
                    <input type = "submit" value = "Применить"/>
                    <a id = "reset" href = "<c:url value = '/'/>">Сбросить</a>
                </td>
            </tr>
        </table>
    </form:form>
</div>

<table border = "1" style = "width: 100%">
    <tr>
        <th>Название</th>
        <th>Авторы</th>
        <th>Цена</th>
        <th>Наличие</th>
    </tr>
    <c:forEach items = "${bookList}" var = "book">
        <tr>
            <td>
                <a href = "${pageContext.request.contextPath}/book?id=${book.key.bookId}">
                    ${book.key.bookName}
                </a>
            </td>
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