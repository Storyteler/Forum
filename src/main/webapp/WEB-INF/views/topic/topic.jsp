
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>主题页</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
    <link rel="stylesheet" href="/static/js/editer/styles/simditor.css">
    <link rel="stylesheet" type="text/css" href="/static/css/sweetalert.css">
    <style>
        body{
            background-image: url(/static/img/bg.jpg);
        }
        .simditor .simditor-body {
            min-height: 100px;
        }
    </style>
</head>
<body>
<%@include file="../demo.jsp"%>
<!--header-bar end-->
<div class="container">
    <div class="box">
        <ul class="breadcrumb" style="background-color: #fff;margin-bottom: 0px;">
            <li><a href="/home">首页</a> <span class="divider">/</span></li>
            <li class="active">${topic.node.name}</li>
        </ul>
        <div class="topic-head">
            <img class="img-rounded avatar" src="http://oigkz9w96.bkt.clouddn.com/${topic.user.avatar}?imageView2/1/w/60/h/60" alt="">
            <h3 class="title">${topic.title}</h3>
            <p class="topic-msg muted"><a href="/setting">${topic.user.username}</a> <span id="topictime">${topic.createtime}</span></p>
        </div>
        <div class="topic-body">
            ${topic.content}
        </div>
        <div class="topic-toolbar">
            <c:if test="${not empty sessionScope.user}">
                <ul class="unstyled inline pull-left">
                    <li><a id="collect" href="javascript:;">加入收藏</a></li>
                    <li><a href="">感谢</a></li>
                    <c:if test="${sessionScope.user.id == topic.user.id}">
                        <li><a href="/edit">编辑</a></li>
                    </c:if>
                </ul>
            </c:if>
            <ul class="unstyled inline pull-right muted">
                <li>${topic.click_num}次点击</li>
                <li><span id="collectnum">${topic.collect_num}</span>人收藏</li>
                <li>${topic.thanks_num}人感谢</li>
            </ul>
        </div>
    </div>
    <!--box end-->

    <div class="box" style="margin-top:20px;">
        <div class="talk-item muted" style="font-size: 12px">
            ${topic.reply_num}个回复 | 直到<span id="last_replytime">${topic.last_replytime}</span>
        </div>
        <c:forEach items="${list}" var="reply" varStatus="vs">
            <div class="talk-item">
                <table class="talk-table">
                    <tr>
                        <td width="50">
                            <img class="avatar" src="http://oigkz9w96.bkt.clouddn.com/${reply.user.avatar}?imageView2/1/w/40/h/40" alt="">
                        </td>
                        <td width="auto">
                            <a href="" style="font-size: 12px">${reply.user.username}</a> <span style="font-size: 12px" class="reply">${reply.createtime}</span>
                            <br>
                            <p style="font-size: 14px">${reply.content}</p>
                        </td>
                        <td width="70" align="right" style="font-size: 12px">
                            <a href="" title="回复"><i class="fa fa-reply"></i></a>&nbsp;
                            <span class="badge">${vs.count}</span>
                        </td>
                    </tr>
                </table>
            </div>
        </c:forEach>
    </div>
    <c:choose>
        <c:when test="${not empty sessionScope.user}">
        <div class="box" style="margin:20px 0px;">
                <div class="talk-item muted" style="font-size: 12px"><i class="fa fa-plus"></i> 添加一条新回复</div>
                <form action="/reply" method="post" style="padding: 15px;margin-bottom:0px;" id="replyForm">
                    <input type="hidden" name="topic_id" value="${topic.id}">
                    <textarea name="content" id="editor"></textarea>
                </form>
                <div class="talk-item muted" style="text-align: right;font-size: 12px">
                    <span class="pull-left">请尽量让自己的回复能够对别人有帮助回复</span>
                    <button id="replyBtn" class="btn btn-primary">发布</button>
                </div>
            </div>
        </c:when>
        <c:otherwise>
        <div class="box" style="margin:20px 0px;">
            <div class="talk-item"> 请<a href="/login?redirect=topic?topic_id=${topic.id}#reply">登录</a>后再回复</div>
        </div>
        </c:otherwise>
    </c:choose>

</div>
<!--container end-->
<script src="/static/js/jquery-1.11.1.js"></script>
<script src="/static/js/sweetalert.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/editer/scripts/module.min.js"></script>
<script src="/static/js/editer/scripts/hotkeys.min.js"></script>
<script src="/static/js/editer/scripts/uploader.min.js"></script>
<script src="/static/js/editer/scripts/simditor.min.js"></script>
<script src="//cdn.bootcss.com/moment.js/2.17.0/moment.min.js"></script>
<script src="//cdn.bootcss.com/moment.js/2.17.0/locale/zh-cn.js"></script>

<script>
    $(function(){
        <c:if test="${not empty sessionScope.user}">
        var editor = new Simditor({
            textarea: $('#editor'),
            toolbar:false
            //optional options
        });
        </c:if>

        $("#replyBtn").click(function () {
            $("#replyForm").submit();
        });

        $("")

        $("#replyForm").validate({
            errorElemet:"span",
            errorClass:"text-error",
            rules:{
                content:{
                    required:true
                }
            },
            messages:{
                content:{
                    required:"请输入你要回复的内容"
                }
            }
        });

        $("#collect").click(function () {
            var action = "";
            if($(this).text() == "加入收藏") {
                action = "collect";
            } else {
                action = "uncollect";
            }

            $.post("/collect",{"topic_id":${topic.id},"action":action}).done(function (json) {
                if(json.state == "success"){
                    if(action == "collect") {
                        $("#collect").text("取消收藏");
                    } else {
                        $("#collect").text("加入收藏");
                    }
                    $("#collectnum").text(json.data);
                } else {
                    sweetAlert(json.message,"","error");
                }
            });
        });

        $("#topictime").text(moment($("#topictime").text()).fromNow());
        $("#last_replytime").text(moment($("#last_replytime").text()).fromNow("YYYY年MM月DD天 HH:mm:ss"));
        $(".reply").text(function () {
                var $this = $(this).text();
                return moment($this).fromNow();
        });


    });
</script>

</body>
</html>
