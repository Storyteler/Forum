
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
    <%--<link rel="shortcut icon" href="/static/img/1.jpg">--%>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>
<%@include file="demo.jsp"%>
<!--header-bar end-->
<div class="container">
    <div class="box">
        <div class="talk-item">
            <ul class="topic-type unstyled inline" style="margin-bottom:0px;">
                <li class="active"><a href="">全部</a></li>
                <li><a href="">问与答</a></li>
                <li><a href="">分享</a></li>
                <li><a href="">Java</a></li>
            </ul>
        </div>
        <% for (int i = 0;i<20;i++) { %>
        <div class="talk-item">
            <table class="talk-table">
                <tr>
                    <td width="50">
                        <img class="avatar" src="http://oigkz9w96.bkt.clouddn.com/default.png?imageView2/1/w/40/h/40" alt="">
                    </td>
                    <td width="80">
                        <a href="">付泽峰</a>
                    </td>
                    <td width="auto">
                        <a href="#">写了一个可以把你网站所有 js 代码格式化成圣诞树的 NodeJS 库： js2image</a>
                    </td>
                    <td width="50" align="center">
                        <span class="badge">12</span>
                    </td>
                </tr>
            </table>
        </div>
        <% }%>

    </div>
    <!--box end-->
</div>
<!--container end-->
<div class="footer">
    <div class="container">
        Copyright © 2016 shuoshuge
    </div>
</div>
</body>
</html>

