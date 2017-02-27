<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <rapid:block name="title">
        <title>Литературный портал</title>
    </rapid:block>
    <rapid:block name="scripts">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
        <link href="css/style.css" type="text/css" rel="stylesheet"/>
    </rapid:block>
</head>
<body>
<div class="container">
    <rapid:block name="topline">
        <div class="header topline">
            <ul class="nav nav-pills pull-right">
                <li class="active">Личный кабинет</li>
                <li>Еще кнопка</li>
            </ul>
            <h3 class="text-muted>">Литературный портал</h3>
        </div>
    </rapid:block>
    <rapid:block name="main">
        <div class="main">Основная страница</div>
    </rapid:block>
    <rapid:block name="bottomline">
        <div class="bottomline">Футер</div>
    </rapid:block>
</div>
</body>
</html>
