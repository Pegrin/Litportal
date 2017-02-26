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
        <form action="./login" method="post">
            <label for="login">Логин: </label>
            <input type="text" name="login" id="login" value="" placeholder="Input"><br>
            <label for="password">Пароль: </label>
            <input type="password" name="password" id="password" value="" placeholder="Input">
            <input type="submit" value="Войти">
        </form>
        <a href="./registration">Зарегистрироваться</a>
    </div>
</rapid:override>
<%@ include file="mainpage.jsp" %>
