<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="post" type="org.wtiger.inno.litportal.models.jaxb.rows.TableRowJaxbPosts"--%>
<rapid:override name="main">
    <div class="main">
        <c:if test="${post!=null}">
            <h1 class="blog-post-title">${post.head}</h1>
            <p class="blog-post-meta">${post.date}, автор: ${post.author.visible_name}</p>
            <p>${post.body}</p>
        </c:if>
    </div>
</rapid:override>

