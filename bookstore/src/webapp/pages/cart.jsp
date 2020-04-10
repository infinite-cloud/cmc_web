<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset = "UTF-8">
    <title>Корзина</title>
    <link rel = "stylesheet" type = "text/css"
          href = "${pageContext.request.contextPath}/resources/css/styles.css">
</head>
<body>

<jsp:include page = "header.jsp"/>

<div class = "page-title" style = "text-align: center;"><b>Корзина</b></div>

<table border = "1" align = "center">
    <tr>
        <th>№</th>
        <th>Название</th>
        <th>Цена</th>
        <th>Количество</th>
        <th>Итог</th>
    </tr>
    <form:form modelAttribute = "cartForm" method = "POST"
               action = "${pageContext.request.contextPath}/cart">
        <c:forEach items = "${cartInfo.bookList}" var = "item" varStatus = "loop">
            <form:input path = "bookList[${loop.index}].id" type = "hidden" value = "${item.id}"/>
            <tr>
                <td>${loop.index + 1}</td>
                <td>
                    <a href = "${pageContext.request.contextPath}/book?id=${item.id}">
                        ${item.book.bookName}
                    </a>
                </td>
                <td>${item.book.bookPrice} руб.</td>
                <td>
                    <form:input path = "bookList[${loop.index}].quantity" value = "${item.quantity}"/>
                </td>
                <td>${item.quantity * item.book.bookPrice} руб.</td>
                <td><a href = "<c:url value = '/deleteFromCart/${item.id}'/>">Удалить</a></td>
            </tr>
        </c:forEach>
        <tr>
            <td></td>
            <td></td>
            <td></td>
            <td><input type = "submit" value = "Обновить"></td>
            <td>${cartInfo.getTotalPrice()} руб.</td>
            <td><a href = "<c:url value = '/clearCart'/>">Очистить</a></td>
        </tr>
    </form:form>
</table>

<jsp:include page = "footer.jsp"/>

</body>
</html>
