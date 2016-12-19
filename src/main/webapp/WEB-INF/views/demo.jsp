
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>demo</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>
<div class="header-bar">
    <div class="container">
        <a href="/home" class="brand">
            <i class="fa fa-reddit-alien"></i>
        </a>
        <ul class="unstyled inline pull-right">
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                        <a href="/setting">
                            <img src="http://7xp5t4.com1.z0.glb.clouddn.com/Fqb8f9uDknAt2ilBoY-ipSZRMes-?imageView2/1/w/20/h/20" class="img-circle" alt="">
                        </a>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-plus"></i></a>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-bell"></i></a>
                    </li>
                    <li>
                        <a href="/setting"><i class="fa fa-cog"></i></a>
                    </li>
                    <li>
                        <a href="/loginout"><i class="fa fa-sign-out"></i></a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li>
                        <a href="/login"><i class="fa fa-sign-in"></i></a>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</div>
</body>
</html>
