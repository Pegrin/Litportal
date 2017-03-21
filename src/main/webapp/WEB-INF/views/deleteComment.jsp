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
    <title>Удаление комментария</title>
</rapid:override>
<rapid:override name="main">
    <div class="main">
        <div><h2>Удаление комментария: </h2></div>
        <form action="./deleteComment" method="post" class="form-signin center-pill registration-table">
            <input type="text" name="commentUuid" style="display:none;" value="${comment.commentUuid}">
            <input type="text" name="postUuid" style="display:none;" value="${comment.postUuid}">
            <table>
                <tr>
                    <td>Автор</td>
                    <td>
                        <pre>${comment.usersByUserUuid.visibleName}</pre>
                    </td>
                </tr>
                <tr>
                    <td>Текст сообщения</td>
                    <td>
                        <pre>${comment.body}</pre>
                    </td>
                </tr>
            </table>
            <input class="btn btn-lg btn-primary btn-block" type="submit" value="Удалить">
        </form>
    </div>
</rapid:override>
<%@ include file="/WEB-INF/views/mainpage.jsp" %>
