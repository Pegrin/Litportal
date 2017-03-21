<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="posts" type="java.util.List<org.wtiger.inno.litportal.models.pojo.PostPojo>"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<rapid:override name="main">
    <div class="main">
        <table class="poststable table">
            <thead>
            <tr>
                <th>Наименование</th>
                <th>Дата публикации</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${posts}" var="item">
                <tr>
                    <td><a href="./post?post_uuid=<c:out value="${item.postUuid}"></c:out>">
                        <c:out value="${item.getHead()}">
                        </c:out></a></td>
                    <td><c:out value="${item.getDate()}"></c:out></td>
                    <td><a href="./editPost?post_uuid=<c:out value="${item.postUuid}"></c:out>">
                        Редактировать</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</rapid:override>
<%@ include file="/WEB-INF/views/mainpage.jsp" %>
