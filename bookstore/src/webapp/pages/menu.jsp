<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page language = "java" contentType = "text/html; charset = UTF-8"
         pageEncoding = "UTF-8"%>

<div class = "menu-container">
    <a href = "<c:url value = '/'/>">Каталог</a>
    <c:if test = "${pageContext.request.userPrincipal.name != null}">
        &nbsp;|&nbsp;
        <a href = "<c:url value = '/cart'/>">Корзина</a>
    </c:if>
    <security:authorize access = "hasRole('ROLE_ADMIN')">
        &nbsp;|&nbsp;
        <a href = "<c:url value = '/orderList'/>">Список заказов</a>
        &nbsp;|&nbsp;
        <a href = "<c:url value = '/addBook'/>">Добавить книгу</a>
        &nbsp;|&nbsp;
        <a href = "<c:url value = '/addItem?author'/>">Добавить автора</a>
        &nbsp;|&nbsp;
        <a href = "<c:url value = '/addItem?publisher'/>">Добавить издательство</a>
        &nbsp;|&nbsp;
        <a href = "<c:url value = '/addItem?coverType'/>">Добавить обложку</a>
        &nbsp;|&nbsp;
        <a href = "<c:url value = '/removeItem?author'/>">Удалить автора</a>
        &nbsp;|&nbsp;
        <a href = "<c:url value = '/removeItem?publisher'/>">Удалить издательство</a>
        &nbsp;|&nbsp;
        <a href = "<c:url value = '/removeItem?coverType'/>">Удалить обложку</a>
    </security:authorize>
</div>
