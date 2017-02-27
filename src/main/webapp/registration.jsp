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
        <form action="./registration" method="post">
            <input type="text" name="uuid" style="display:none;" value="${uuid}">
            <table>
                <tr>
                    <td>Логин</td>
                    <td><input type="text" name="login" value="${login}"></td>
                </tr>
                <tr>
                    <td>Пароль</td>
                    <td><input type="text" name="password" value="${password}"></td>
                </tr>
                <tr>
                    <td>Электронная почта</td>
                    <td><input type="text" name="email" value="${email}"></td>
                </tr>
                <tr>
                    <td>Отображаемое имя</td>
                    <td><input type="text" name="visible_name" value="${visible_name}"></td>
                </tr>
                <c:if test="${add!=null}">
                    <tr style="visibility: hidden">
                        <td><input name="add" value="add"></td>
                    </tr>
                </c:if>
            </table>
            <input type="submit" value="Зарегистрироваться">
        </form>
    </div>
</rapid:override>
<%@ include file="mainpage.jsp" %>
