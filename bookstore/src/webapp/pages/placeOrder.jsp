<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset = "UTF-8">
    <title>Оформление заказа</title>
    <link rel = "stylesheet" type = "text/css"
          href = "${pageContext.request.contextPath}/resources/css/styles.css">
</head>
<body>

<jsp:include page = "header.jsp"/>
<jsp:include page = "menu.jsp"/>

<div class = "page-title" style = "text-align: center;"><b>Оформление заказа</b></div>

<c:choose>
    <c:when test = "${param.success == null}">
        <form:form modelAttribute = "orderForm" method = "POST"
                   action = "${pageContext.request.contextPath}/placeOrder">
        <table align = "center">
            <tr>
                <td>Адрес доставки</td>
                <td>
                    <label>
                        <textarea name = "deliveryAddress">${orderingUser.homeAddress}</textarea>
                    </label>
                </td>
                <td>
                    <form:errors path = "deliveryAddress" class = "error-message"/>
                </td>
            </tr>

            <tr>
                <td>Дата доставки</td>
                <td>
                    <label>
                        <form:input path = "deliveryDate" value = "YYYY-MM-DD" type = "datetime-local"/>
                    </label>
                </td>
                <td>
                    <form:errors path = "deliveryDate" class = "error-message"/>
                </td>
            </tr>

            <tr>
                <td>&nbsp;</td>
                <td>
                    <label>
                        <input type = "submit" value = "Оформить заказ"/>
                    </label>
                </td>
            </tr>
        </table>
        </form:form>
    </c:when>
    <c:when test = "${param.success == true && param.orderNumber != null}">
        <b>Заказ оформлен</b><br/>
        Номер вашего заказа: ${param.orderNumber}
    </c:when>
    <c:otherwise>
        <b>Ошибка</b><br/>
        Произошла ошибка
    </c:otherwise>
</c:choose>

<jsp:include page = "footer.jsp"/>

</body>
</html>
