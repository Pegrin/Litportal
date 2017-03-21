<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="post" type="org.wtiger.inno.litportal.models.pojo.PostPojo"--%>
<%--@elvariable id="comment" type="org.wtiger.inno.litportal.models.pojo.CommentPojo"--%>
<rapid:override name="main">
    <div class="main">
        <c:if test="${post!=null}">
            <h1 class="blog-post-title">${post.head}</h1>
            <p class="blog-post-meta">Назад к группе
                <a href="./posts?group_uuid=${post.groupUuid}">"${post.groupsByGroupUuid.head}"</a>
            </p>
            <p class="blog-post-meta">${post.date}, автор: ${post.usersByUserUuid.visibleName}</p>
            <p>${post.body}</p>
            <div class="comments-template">
                <h3>Комментарии</h3>
                <a href="./addComment?post_uuid=${post.postUuid}">Добавить комментарий</a>
                <c:forEach items="${comments}" var="comment">
                    <div id="${comment.commentUuid}" class="comment">
                        <article class="comment-body">
                            <div class="media-body">
                                <h4>Автор: <a href="#">${comment.usersByUserUuid.visibleName}</a>
                                    Дата: ${comment.date.toLocalDateTime()}</h4>
                                <p>${comment.body}</p>
                                <a href="./editComment?comment_uuid=${comment.commentUuid}">Редактировать</a><br>
                                <a href="./deleteComment?comment_uuid=${comment.commentUuid}">Удалить</a>
                            </div>
                        </article>
                    </div>
                </c:forEach>
            </div>
        </c:if>
    </div>
</rapid:override>
<%@ include file="/WEB-INF/views/mainpage.jsp" %>

