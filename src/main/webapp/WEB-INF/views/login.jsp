
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>用户登录</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/sweetalert.css">
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>
<%@include file="demo.jsp"%>
<!--header-bar end-->
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-sign-in"></i> 登录</span>
        </div>

        <form action="" id="loginForm" class="form-horizontal">
            <c:if test="${not empty requestScope.message}">
                <div class="alert alert-success">
                    ${requestScope.message}
                </div>
            </c:if>
            <c:if test="${not empty param.redirect}">
                <div class="alert alert-success">
                        请登陆后才能设置
                </div>
            </c:if>
            <div class="control-group">
                <label class="control-label">账号</label>
                <div class="controls">
                    <input name="username" type="text">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">密码</label>
                <div class="controls">
                    <input name="password" type="password">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label"></label>
                <div class="controls">
                    <a href="/found">忘记密码</a>
                </div>
            </div>

            <div class="form-actions">
                <button type="button" id="loginBtn" class="btn btn-primary">登录</button>

                <a class="pull-right" href="/reg">注册账号</a>
            </div>

        </form>
    </div>
    <!--box end-->
</div>
<script src="/static/js/jquery-1.11.1.js"></script>
<script src="/static/js/sweetalert.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/user/login.js"></script>
<!--container end-->
</body>
</html>
