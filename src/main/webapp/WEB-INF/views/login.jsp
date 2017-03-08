<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>
<%--
  Created by IntelliJ IDEA.
  User: olymp
  Date: 26.02.2017
  Time: 23:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<rapid:override name="title">
    <title>Авторизация</title>
</rapid:override>
<rapid:override name="main">
    <div class="main">
        <form class="form-signin center-pill" action="./login" method="post">
            <h2 class="form-signin-heading">Пожалуйста, авторизуйтесь</h2>
            <label for="login" class="sr-only">Email address</label>
            <input id="login" name="login" class="form-control" placeholder="Логин" required="" autofocus=""
                   type="login">
            <label for="password" class="sr-only">Password</label>
            <input id="password" name="password" class="form-control" placeholder="Пароль" required="" type="password">
            <button class="btn btn-lg btn-primary btn-block" type="submit">Войти</button>
        </form>
        <a href="./registration">Зарегистрироваться</a>
    </div>
</rapid:override>
<%@ include file="/WEB-INF/views/mainpage.jsp" %>
