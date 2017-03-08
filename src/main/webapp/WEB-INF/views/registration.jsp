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
        <div><h2>Регистрация нового пользователя:</h2></div>
        <form action="./registration" method="post" class="form-signin center-pill registration-table">
            <input type="text" name="uuid" style="display:none;" value="${uuid}">
            <table>
                <tr>
                    <td style="padding-right: 5px">Логин</td>
                    <td><input class="form-control" type="text" name="login" value="${login}" required></td>
                </tr>
                <tr>
                    <td>Пароль</td>
                    <td><input class="form-control" type="password" name="password" value="${password}" required></td>
                </tr>
                <tr>
                    <td>Электронная почта</td>
                    <td><input class="form-control" type="email" name="email" value="${email}" required></td>
                </tr>
                <tr>
                    <td>Отображаемое имя</td>
                    <td><input class="form-control" type="text" name="visible_name" value="${visible_name}"></td>
                </tr>
            </table>
            <input class="btn btn-lg btn-primary btn-block" type="submit" value="Зарегистрироваться">
        </form>
    </div>
</rapid:override>
<%@ include file="/WEB-INF/views/mainpage.jsp" %>
