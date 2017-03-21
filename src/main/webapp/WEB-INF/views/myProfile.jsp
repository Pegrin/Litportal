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
    <%--@elvariable id="euser" type="org.wtiger.inno.litportal.models.pojo.UserPojo"--%>
    <div class="main">
        <div><h2>Редактирование профиля</h2></div>
        <form action="./profile" method="post" class="form-signin center-pill registration-table">
            <input type="text" name="userUuid" style="display:none;" value="${euser.userUuid}">
            <input type="text" name="version" style="display:none;" value="${euser.version}">
            <label for="login" class="">Логин</label>
            <input id="login" class="form-control" type="text" name="login" value="${euser.login}" required>
                <%--<label for="password" class="sr-only">Пароль</label>--%>
                <%--<input id="password" class="form-control" type="text" name="password" value="${user.password}" required>--%>
            <label for="email" class="">Электронный адрес</label>
            <input id="email" class="form-control" type="email" name="email" value="${euser.email}" required>
            <label for="visibleName" class="">Отображаемое имя</label>
            <input id="visibleName" class="form-control" type="text" name="visibleName" value="${euser.visibleName}"
                   required>
            <input class="btn btn-lg btn-primary btn-block" type="submit" value="Обновить информацию">
        </form>
        <a href="./myPosts">Список ваших стихотворений</a>
    </div>
</rapid:override>
<%@ include file="/WEB-INF/views/mainpage.jsp" %>
