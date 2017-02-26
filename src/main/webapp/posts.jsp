<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: olymp
  Date: 26.02.2017
  Time: 23:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<rapid:override name="main">
    <div class="main">
        <table class="poststable">
            <tr>
                <td>Автор</td>
                <td>Заголовок</td>
                <td>Дата публикации</td>
            </tr>
            <c:forEach items="${posts}" var="item">
                <tr>
                    <td><c:out value="${item.getAuthor().getVisible_name()}"></c:out></td>
                    <td><c:out value="${item.getHead()}"></c:out></td>
                    <td><c:out value="${item.getDate()}"></c:out></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</rapid:override>
<%@ include file="mainpage.jsp" %>
