<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset = "UTF-8">
    <title>Личный кабинет</title>
    <link rel = "stylesheet" type = "text/css"
          href = "${pageContext.request.contextPath}/resources/css/styles.css">
    <link rel = "stylesheet" type = "text/css"
          href = "${pageContext.request.contextPath}/resources/css/jquery-ui.min.css">

    <script src = "${pageContext.request.contextPath}/resources/javascript/jquery-3.4.1.min.js">
    </script>
    <script src = "${pageContext.request.contextPath}/resources/javascript/jquery-ui.min.js">
    </script>

    <script type = "text/javascript">
        $(function () {
            $("#tabs").tabs();
        });
    </script>
</head>
<body>

<jsp:include page = "header.jsp"/>
<jsp:include page = "menu.jsp"/>

<div class = "page-title" style = "text-align: center;"><b>Личный кабинет</b></div>

<div id = "tabs">
    <ul>
        <li><a href = "#accountInfo">Учётная запись</a></li>
        <li><a href = "#ordersInfo">Заказы</a></li>
    </ul>
    <div id = "accountInfo">
        <c:choose>
            <c:when test = "${param.edit != true}">
                <table align = "center">
                    <tr>
                        <th>Электронная почта</th>
                        <td>${user.eMail}</td>
                    </tr>

                    <tr>
                        <th>Имя пользователя</th>
                        <td>${user.userName}</td>
                    </tr>

                    <tr>
                        <th>Домашний адрес</th>
                        <td>${user.homeAddress}</td>
                    </tr>

                    <tr>
                        <th>Номер телефона</th>
                        <td>${user.phoneNumber}</td>
                    </tr>

                    <tr>
                        <td>&nbsp;</td>
                        <td><a href = "account?edit=true">Редактировать</a></td>
                    </tr>

                </table>
            </c:when>
            <c:otherwise>
                <form:form modelAttribute = "userAccountForm" method = "POST"
                           action = "${pageContext.request.contextPath}/account?edit=true">
                    <form:input path = "needsInitialValidation" type = "hidden" value = "false"/>
                    <form:input path = "eMail" type = "hidden" value = "${user.eMail}"/>
                    <table align = "center">
                        <tr>
                            <th>Электронная почта</th>
                            <td>${user.eMail}</td>
                        </tr>

                        <tr>
                            <th>Имя пользователя</th>
                            <td>
                                <label>
                                    <form:input path = "userName" value = "${user.userName}"/>
                                </label>
                            </td>
                            <td>
                                <form:errors path = "userName" class = "error-message"/>
                            </td>
                        </tr>

                        <tr>
                            <th>Домашний адрес</th>
                            <td>
                                <label>
                                    <textarea name = "homeAddress">${user.homeAddress}</textarea>
                                </label>
                            </td>
                            <td>
                                <form:errors path = "homeAddress" class = "error-message"/>
                            </td>
                        </tr>

                        <tr>
                            <th>Номер телефона</th>
                            <td>
                                <label>
                                    <form:input path = "phoneNumber" value = "${user.phoneNumber}"/>
                                </label>
                            </td>
                            <td>
                                <form:errors path = "phoneNumber" class = "error-message"/>
                            </td>
                        </tr>

                        <tr>
                            <th>Пароль</th>
                            <td>
                                <label>
                                    <form:input type = "password" path = "password"/>
                                </label>
                            </td>
                            <td>
                                <form:errors path = "password" class = "error-message"/>
                            </td>
                        </tr>

                        <tr>
                            <th>Пароль (повторно)</th>
                            <td>
                                <label>
                                    <form:input type = "password" path = "repeatedPassword"/>
                                </label>
                            </td>
                            <td>
                                <form:errors path = "repeatedPassword" class = "error-message"/>
                            </td>
                        </tr>

                        <tr>
                            <td>&nbsp;</td>
                            <td>
                                <label>
                                    <input type = "submit" value = "Сохранить"/>
                                    <a href = "account">Отмена</a>
                                </label>
                            </td>
                        </tr>
                    </table>
                </form:form>
                <a href = "<c:url value = '/deleteAccount'/>"
                   onclick = "return confirm('Вы уверены, что хотите удалить свою учётную запись?')">
                    Удалить учётную запись
                </a>
            </c:otherwise>
        </c:choose>
    </div>
    <div id = "ordersInfo">
        <table border = "1" align = "center">
            <tr>
                <th>Номер заказа</th>
                <th>Стоимость</th>
                <th>Статус</th>
                <th>Дата заказа</th>
                <th>Дата доставки</th>
                <th>Адрес доставки</th>
                <th>Содержание</th>
            </tr>
            <c:forEach items = "${userOrders}" var = "order">
                <tr>
                    <td>${order.id}</td>
                    <td>${order.price} руб.</td>
                    <td>${order.status}</td>
                    <td>${order.orderDate}</td>
                    <td>${order.deliveryDate}</td>
                    <td>${order.deliveryAddress}</td>
                    <td>
                        <c:forEach items = "${order.books}" var = "book">
                            ${book.key} (${book.value} шт.)<br/>
                        </c:forEach>
                    </td>
                    <c:if test = "${order.status == 'В обработке' || order.status == 'Собран'}">
                        <td>
                            <a href = "<c:url value = '/cancelOrder/${order.id}'/>"
                               onclick = "return confirm('Вы уверены, что хотите отменить заказ?')">
                                Отменить
                            </a>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

<jsp:include page = "footer.jsp"/>

</body>
</html>
