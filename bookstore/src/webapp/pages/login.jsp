<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset = "UTF-8">
    <title>Вход</title>
    <link rel = "stylesheet" type = "text/css"
          href = "${pageContext.request.contextPath}/resources/css/styles.css">
</head>
<body>

<jsp:include page = "header.jsp"/>
<jsp:include page = "menu.jsp"/>

<div class = "page-title" style="text-align: center;"><b>Вход</b></div>

<c:if test = "${param.error == 'true'}">
    <div style = "color: red; margin: 10px 0px;">
        Ошибка:
        ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
    </div>
</c:if>

<c:if test = "${param.registered == 'true'}">
    <div style = "color: green; margin: 10px 0px;">
        Регистрация успешна
    </div>
</c:if>

<form method = "POST"
      action = "${pageContext.request.contextPath}/j_spring_security_check">
    <table align = "center">
        <tr>
            <td>Электронная почта</td>
            <td>
                <label>
                    <input name = "eMail" type = "email"/>
                </label>
            </td>
        </tr>

        <tr>
            <td>Пароль</td>
            <td>
                <label>
                    <input type = "password" name = "password"/>
                </label>
            </td>
        </tr>

        <tr>
            <td>&nbsp;</td>
            <td>
                <label>
                    <input type = "submit" value = "Вход"/>
                </label>
            </td>
        </tr>
    </table>
</form>

<jsp:include page = "footer.jsp"/>

</body>
</html>
