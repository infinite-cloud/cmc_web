<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset = "UTF-8">
    <title>Заказ №${orderData.orderId}</title>
    <link rel = "stylesheet" type = "text/css"
          href = "${pageContext.request.contextPath}/resources/css/styles.css">
</head>
<body>

<jsp:include page = "header.jsp"/>
<jsp:include page = "menu.jsp"/>

<div class = "page-title" style = "text-align: center;"><b>Заказ №${orderData.orderId}</b></div>

<table border = "1" align = "center">
    <tr>
        <th>Имя заказчика</th>
        <td>${customerAccount.userName}</td>
    </tr>

    <tr>
        <th>Электронная почта заказчика</th>
        <td>${customerAccount.eMail}</td>
    </tr>

    <tr>
        <th>Номер телефона заказчика</th>
        <td>${customerAccount.phoneNumber}</td>
    </tr>

    <tr>
        <th>Адрес доставки</th>
        <td>${orderData.deliveryAddress}</td>
    </tr>

    <tr>
        <th>Дата заказа</th>
        <td>
            <c:set var = "orderDate" value = "${orderData.orderDate}"/>
            <c:set var = "orderDateFormatted" value = "${fn:substring(orderDate, 0, 16)}"/>
            ${orderDateFormatted}
        </td>
    </tr>

    <tr>
        <th>Дата доставки</th>
        <td>
            <c:set var = "deliveryDate" value = "${orderData.deliveryDate}"/>
            <c:set var = "deliveryDateFormatted" value = "${fn:substring(deliveryDate, 0, 16)}"/>
            ${deliveryDateFormatted}
        </td>
    </tr>

    <tr>
        <th>Стоимость заказа</th>
        <td>${orderData.totalPrice} руб.</td>
    </tr>

    <tr>
        <th>Содержание заказа</th>
        <td>
            <c:forEach items = "${orderedBooks}" var = "book">
                ${book.bookId.bookName} (${book.bookCount} шт.)<br/>
            </c:forEach>
        </td>
    </tr>

    <tr>
        <th>Статус заказа</th>
        <form:form modelAttribute = "orderSelector" method = "POST">
            <td>
                <form:select path = "status">
                    <c:forEach items = "${orderStatus}" var = "status">
                        <c:choose>
                            <c:when test = "${orderData.orderStatus == status}">
                                <option value = "${status}" selected>
                                        ${statusStrings[status.ordinal()]}
                                </option>
                            </c:when>
                            <c:otherwise>
                                <option value = "${status}">
                                        ${statusStrings[status.ordinal()]}
                                </option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </form:select>
            </td>
        <td>
            <form:input path = "orderId" type = "hidden" value = "${orderData.orderId}"/>
            <input type = "submit" value = "Обновить"/>
        </td>
        </form:form>
    </tr>
</table>

<jsp:include page = "footer.jsp"/>

</body>
</html>
