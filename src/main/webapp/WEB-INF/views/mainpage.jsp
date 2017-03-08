<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="user" type="org.wtiger.inno.litportal.models.rows.UsersEntity"--%>
<html>
<head>
    <rapid:block name="title">
        <title>Литературный портал</title>
    </rapid:block>
    <rapid:block name="scripts">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
        <link href="./css/style.css" type="text/css" rel="stylesheet"/>
    </rapid:block>
</head>
<body>
<div class="container">
    <rapid:block name="topline">
        <div class="header topline">
            <ul class="nav nav-pills pull-right">
                <li class="active"><a class="nav-link active" href="./login">Личный кабинет<span class="sr-only">(current)</span></a>
                </li>
                <li><c:if test="${user!=null}">
                    <a class="nav-link" href="./logout">Выйти</a>
                </c:if><c:if test="${user==null}">
                    <a class="nav-link" href="#">Вы не авторизованы.</a>
                </c:if></li>
            </ul>
            <h4 class="text-muted>"><a href="./posts">Литературный портал</a></h4><br>
            <p><c:if test="${user!=null}">
                <c:out value="Добро пожаловать, ${user.visibleName}!"/></c:if></p>
        </div>
    </rapid:block>
    <rapid:block name="main">
        <div class="main">Основная страница</div>
    </rapid:block>
    <rapid:block name="bottomline">
        <div class="bottomline">Футер</div>
    </rapid:block>
</div>
</body>
</html>
