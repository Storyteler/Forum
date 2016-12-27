
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>通知中心</title>
    <link href="//cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="//cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<%@include file="../demo.jsp"%>
<!--header-bar end-->
<!--header-bar end-->
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-bell"></i> 通知中心</span>
        </div>
        <button id="markBtn" style="margin-left: 8px;" disabled class="btn btn-mini">标记为已读</button>
        <table class="table">
            <thead>
            <tr>
                <th width="30"><c:if test="${not empty list}"><input type="checkbox" id="ckFather"></c:if></th>
                <th width="200">发布日期</th>
                <th>内容</th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${not empty list}">
                    <c:forEach items="${list}" var="nodify">
                        <c:choose>
                            <c:when test="${nodify.state == 1}">
                                <tr style="text-decoration: line-through">
                                    <td></td>
                                    <td>${nodify.createtime}</td>
                                    <td>${nodify.content}</td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td><input value=${nodify.id} type="checkbox" class="ckSon"></td>
                                    <td>${nodify.createtime}</td>
                                    <td>${nodify.content}</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr><td colspan="3"><p>暂时没有任何消息</p></td></tr>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>





    </div>
    <!--box end-->
</div>
<!--container end-->
<script src="js/jquery-1.11.3.min.js"></script>
<script src="js/jquery.validate.min.js"></script>
<script>
    $(function () {
        $("#ckfather").click(function () {
            var sons = $(".ckSon");
            for(var i = 0;i < sons.length;i++) {
                sons[i].checked = $(this)[0].checked;
            }

            if ($(this)[0].checked == true) {
                $("#markBtn").removeAttr("disabled");
            } else {
                $("#markBtn").attr("disabled","disabled");
            }
        });

        $(".ckSon").click(function () {
            var num = 0;
            for(var i =0;i<$(this).length;i++) {
                if($(this)[i].checked) {
                    num++;
                }
            }

            if (num == $(this).length) {
                $("#ckFather")[0].checked = true;
            } else {
                $("#ckFather")[0].checked = false;
            }

            if (num > 0) {
                $("#markBtn").removeAttr("disabled");
            } else {
                $("#markBtn").attr("disabled","disabled");
            }
        });

        $("#markBtn").click(function(){
            var ids = [];
            var sons = $(".ckSon");
            for(var i=0 ; i<sons.length;i++){
                if(sons[i].checked == true){
                    //jquery的方法数组添加元素
                    ids.push(sons[i].value);
                }
            }
            //将数组变成字符串
            alert(ids.join(","));
            $.post("/notifyRead",{"ids":ids.join(",")},function(json){
                if (json == "success"){
                    //刷新页面
                    window.history.go(0);
                }
            });
        });
    });
</script>
</body>
</html>
