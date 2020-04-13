<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language = "java" contentType="text/html; charset = UTF-8"
         pageEncoding = "UTF-8"%>

<div class = "header-container">
    <div class = "site-name">
        <a href = "<c:url value = '/'/>">Книжный магазин</a>
    </div>

    <div class = "header-bar">
        <c:if test = "${pageContext.request.userPrincipal.name != null}">
            <a href = "<c:url value = '/account'/>">Личный кабинет</a>
            &nbsp;|&nbsp;
            <a href = "<c:url value = '/logout'/>">Выход</a>
        </c:if>
        <c:if test = "${pageContext.request.userPrincipal.name == null}">
            <a href = "<c:url value = '/login'/>">Вход</a>
            &nbsp;|&nbsp;
            <a href = "<c:url value = '/register'/>">Регистрация</a>
        </c:if>
    </div>
</div>
