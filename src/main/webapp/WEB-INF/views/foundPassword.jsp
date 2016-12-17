<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>找回密码</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>
<%@include file="demo.jsp"%>
<!--header-bar end-->
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title">找回密码</span>
        </div>
        <form action="" id="foundForm" class="form-horizontal">
            <div class="control-group">
                <label class="control-label">找回方式</label>
                <div class="controls">
                    <select name="type" id="type">
                        <option value="email">根据电子邮件找回</option>
                        <option value="phone">根据电话号码找回</option>
                    </select>
                </div>
            </div>
            <div class="control-group">
                <label id="typename" class="control-label">电子邮件</label>
                <div class="controls">
                    <input name="value" type="text">
                </div>
            </div>
            <div class="form-actions">
                <button id="foundBtn" class="btn btn-primary">提交</button>
            </div>
        </form>
    </div>
    <!--box end-->
</div>
<script src="/static/js/jquery-1.11.1.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/user/foundPassword.js"></script>
<!--container end-->
</body>
</html>