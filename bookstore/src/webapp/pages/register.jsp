<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset = "UTF-8">
    <title>Регистрация</title>
    <link rel = "stylesheet" type = "text/css"
          href = "${pageContext.request.contextPath}/resources/css/styles.css">
</head>
<body>

<jsp:include page = "header.jsp"/>

<div class = "page-title" style="text-align: center;"><b>Регистрация</b></div>

<form:form modelAttribute = "userForm" method = "POST"
           action = "${pageContext.request.contextPath}/register">
    <table align = "center">
        <tr>
            <td>Электронная почта (*)</td>
            <td>
                <label>
                    <form:input path = "eMail"/>
                </label>
            </td>
            <td>
                <form:errors path = "eMail" class = "error-message"/>
            </td>
        </tr>

        <tr>
            <td>Имя пользователя (*)</td>
            <td>
                <label>
                    <form:input path = "userName"/>
                </label>
            </td>
            <td>
                <form:errors path = "userName" class = "error-message"/>
            </td>
        </tr>

        <tr>
            <td>Домашний адрес</td>
            <td>
                <label>
                    <form:input path = "homeAddress"/>
                </label>
            </td>
            <td>
                <form:errors path = "homeAddress" class = "error-message"/>
            </td>
        </tr>

        <tr>
            <td>Номер телефона</td>
            <td>
                <label>
                    <form:input path = "phoneNumber"/>
                </label>
            </td>
            <td>
                <form:errors path = "phoneNumber" class = "error-message"/>
            </td>
        </tr>

        <tr>
            <td>Пароль (*)</td>
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
            <td>Пароль (повторно) (*)</td>
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
                    <input type = "submit" value = "Регистрация"/>
                </label>
            </td>
        </tr>
    </table>
</form:form>

<jsp:include page = "footer.jsp"/>

</body>
</html>
