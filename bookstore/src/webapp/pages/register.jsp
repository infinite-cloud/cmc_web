<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
<jsp:include page = "menu.jsp"/>

<div class = "page-title" style="text-align: center;"><b>Регистрация</b></div>

<form:form modelAttribute = "userForm" method = "POST"
           action = "${pageContext.request.contextPath}/register">
    <form:input path = "needsInitialValidation" type = "hidden" value = "true"/>
    <table align = "center">
        <tr>
            <th>Электронная почта (*)</th>
            <td>
                <label>
                    <form:input path = "eMail" type = "email"/>
                </label>
            </td>
            <td>
                <form:errors path = "eMail" class = "error-message"/>
            </td>
        </tr>

        <tr>
            <th>Имя пользователя (*)</th>
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
            <th>Домашний адрес</th>
            <td>
                <label>
                    <textarea name = "homeAddress"></textarea>
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
                    <form:input path = "phoneNumber" type = "tel"/>
                </label>
            </td>
            <td>
                <form:errors path = "phoneNumber" class = "error-message"/>
            </td>
        </tr>

        <tr>
            <th>Пароль (*)</th>
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
            <th>Пароль (повторно) (*)</th>
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
