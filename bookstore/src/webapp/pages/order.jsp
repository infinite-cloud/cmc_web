<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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

<div class = "page-title" style = "text-align: center;"><b>Заказ №${orderData.orderId}</b></div>

<table border = "1" align = "center">
    <tr>
        <td><b>Имя заказчика</b></td>
        <td>${customerAccount.userName}</td>
    </tr>

    <tr>
        <td><b>Электронная почта заказчика</b></td>
        <td>${customerAccount.eMail}</td>
    </tr>

    <tr>
        <td><b>Номер телефона заказчика</b></td>
        <td>${customerAccount.phoneNumber}</td>
    </tr>

    <tr>
        <td><b>Адрес доставки</b></td>
        <td>${orderData.deliveryAddress}</td>
    </tr>

    <tr>
        <td><b>Дата заказа</b></td>
        <td>${orderData.orderDate}</td>
    </tr>

    <tr>
        <td><b>Дата доставки</b></td>
        <td>${orderData.orderDate}</td>
    </tr>

    <tr>
        <td><b>Стоимость заказа</b></td>
        <td>${orderData.totalPrice} руб.</td>
    </tr>

    <tr>
        <td><b>Содержание заказа</b></td>
        <td>
            <c:forEach items = "${orderedBooks}" var = "book">
                ${book.bookId.bookName} (${book.bookCount} шт.)
            </c:forEach>
        </td>
    </tr>

    <tr>
        <td><b>Статус заказа</b></td>
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
