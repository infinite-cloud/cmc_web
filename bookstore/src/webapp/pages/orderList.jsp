<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset = "UTF-8">
    <title>Список заказов</title>
    <link rel = "stylesheet" type = "text/css"
          href = "${pageContext.request.contextPath}/resources/css/styles.css">
</head>
<body>

<jsp:include page = "header.jsp"/>

<div class = "page-title" style = "text-align: center;"><b>Список заказов</b></div>

<table border = "1" align = "center">
    <tr>
        <th>Номер заказа</th>
        <th>Дата заказа</th>
        <th>Дата доставки</th>
        <th>Статус</th>
    </tr>
    <c:forEach items = "${orders}" var = "order">
        <tr>
            <td><a href = "<c:url value = '/order?id=${order.orderId}'/>">${order.orderId}</a></td>
            <td>
                <c:set var = "orderDate" value = "${order.orderDate}"/>
                <c:set var = "orderDateFormatted" value = "${fn:substring(orderDate, 0, 16)}"/>
                    ${orderDateFormatted}
            </td>
            <td>
                <c:set var = "deliveryDate" value = "${order.deliveryDate}"/>
                <c:set var = "deliveryDateFormatted" value = "${fn:substring(deliveryDate, 0, 16)}"/>
                    ${deliveryDateFormatted}
            </td>
            <form:form modelAttribute = "orderSelector" method = "POST">
                <td>
                    <form:select path = "status">
                        <c:forEach items = "${orderStatus}" var = "status">
                            <c:choose>
                                <c:when test = "${order.orderStatus == status}">
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
                    <form:input path = "orderId" type = "hidden" value = "${order.orderId}"/>
                    <input type = "submit" value = "Обновить"/>
                </td>
            </form:form>
        </tr>
    </c:forEach>
</table>

<jsp:include page = "footer.jsp"/>

</body>
</html>
