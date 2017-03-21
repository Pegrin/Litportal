<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: olymp
  Date: 13.03.2017
  Time: 0:26
  To change this template use File | Settings | File Templates.
--%>
<%--@elvariable id="comment" type="org.wtiger.inno.litportal.models.hibernate.CommentsEntity"--%>
<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<rapid:override name="title">
    <title>Авторизация</title>
</rapid:override>
<rapid:override name="main">
    <div class="main">
        <div><h2>Добавление комментария: </h2></div>
        <form action="./addComment" method="post" class="form-signin center-pill registration-table">
            <input type="text" name="commentUuid" style="display:none;" value="${postUuid}">
            <table>
                <tr>
                    <td>Текст сообщения</td>
                    <td><textarea class="form-control" name="body" required>${body}</textarea></td>
                </tr>
            </table>
            <input class="btn btn-lg btn-primary btn-block" type="submit" value="Отправить">
        </form>
    </div>
</rapid:override>
<%@ include file="/WEB-INF/views/mainpage.jsp" %>