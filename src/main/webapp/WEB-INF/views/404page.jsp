<%--
  Created by IntelliJ IDEA.
  User: olymp
  Date: 12.03.2017
  Time: 23:51
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<rapid:override name="title">
    <title>Ошибка получения страницы</title>
</rapid:override>
<rapid:override name="main">
    <div class="main">
        <h1>Такой страницы не найдено.</h1>
    </div>
</rapid:override>
<%@ include file="/WEB-INF/views/mainpage.jsp" %>
