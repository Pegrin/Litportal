<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="group" type="org.wtiger.inno.litportal.models.rows.GroupsEntity"--%>
<%--@elvariable id="groups" type="java.util.List<org.wtiger.inno.litportal.models.rows.GroupsEntity>"--%>
<%--@elvariable id="posts" type="java.util.List<org.wtiger.inno.litportal.models.rows.PostsEntity>"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<rapid:override name="main">
    <div class="main">
        <table class="poststable table">
            <thead>
            <tr>
                <th>Наименование</th>
                <th>Автор</th>
                <th>Дата публикации</th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${group!=null&&group.parentGroupUuid!=null}">
                <tr>
                    <td colspan="3">
                        <div><a href="./posts?group_uuid=${group.parentGroupUuid}">--- Перейти на каталог выше ---</a>
                        </div>
                    </td>
                </tr>
            </c:if>
            <c:if test="${group!=null&&group.parentGroupUuid==null}">
                <tr>
                    <td colspan="3">
                        <div><a href="./posts">--- Перейти на каталог выше ---</a></div>
                    </td>
                </tr>
            </c:if>
            <c:if test="${group!=null}">
                <tr>
                    <td colspan="3">
                        <div><h2>${group.head}</h2></div>
                        <div><h5>${group.body}</h5></div>
                    </td>
                </tr>
            </c:if>
            <c:forEach items="${groups}" var="item">
                <tr>
                    <td colspan="3">
                        <div><a href="./posts?group_uuid=${item.groupUuid}">${item.getHead()}</a></div>
                    </td>
                </tr>
            </c:forEach>
            <c:forEach items="${posts}" var="item">
                <tr>
                    <td><c:out value="${item.getHead()}"></c:out></td>
                    <td><c:out value="${item.usersByUserUuid.visibleName}"></c:out></td>
                    <td><c:out value="${item.getDate()}"></c:out></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</rapid:override>
<%@ include file="mainpage.jsp" %>
