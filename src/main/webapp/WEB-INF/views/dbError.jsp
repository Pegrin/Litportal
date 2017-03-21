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
    <title>Ошибка БД</title>
</rapid:override>
<rapid:override name="main">
    <div class="main">
        <h1>Сбой службы БД на сервере. Попробуйте обновить страницу позже.</h1>
    </div>
</rapid:override>
<%@ include file="/WEB-INF/views/mainpage.jsp" %>
