
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>编辑主题</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
    <link rel="stylesheet" href="/static/css/sweetalert.css">
    <link rel="stylesheet" href="/static/js/editer/styles/simditor.css">
    <link rel="stylesheet" href="/static/css/simditor-emoji.css">
    <link rel="stylesheet" href="/static/css/styles/solarized-light.css">

</head>
<body>
<%@include file="../demo.jsp"%>
<!--header-bar end-->
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-plus"></i> 编辑主题</span>
        </div>

        <form action="" id="editTopicForm" style="padding: 20px">
            <label class="control-label">主题标题</label>
            <input name="topic_id" id="topicId" type="hidden" value="${topic.id}">
            <input name="title" value="${topic.title}" type="text" style="width: 100%;box-sizing: border-box;height: 30px">
            <label class="control-label">正文</label>
            <textarea name="content" id="editor">${topic.content}</textarea>
            <select name="nodename" style="margin-top:15px;">
                <option>请选择节点</option>
                <c:forEach items="${list}" var="node">
                    <option ${topic.node.id == node.id?'selected':''} value="${node.id}">${node.name}</option>
                </c:forEach>
            </select>

        </form>
        <div class="form-actions" style="text-align: right">
            <button id="editTopicBtn" class="btn btn-primary">编辑完成</button>
        </div>


    </div>
    <!--box end-->
</div>
<!--container end-->
<script src="/static/js/jquery-1.11.1.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/sweetalert.min.js"></script>
<script src="/static/js/editer/scripts/module.min.js"></script>
<script src="/static/js/editer/scripts/hotkeys.min.js"></script>
<script src="/static/js/editer/scripts/uploader.min.js"></script>
<script src="/static/js/editer/scripts/simditor.min.js"></script>
<script src="/static/js/simditor-emoji.js"></script>
<script src="/static/js/highlight.pack.js"></script>
<script src="/static/js/topic/edit.js"></script>
<script>
    $(function () {
        var editor = new Simditor({
            textarea: $('#editor'),
            toolbar: ['title','bold','italic','underline','strikethrough',
                'fontScale','color','ol' ,'ul', 'blockquote','code',
                'table', 'image','emoji'],
            emoji: {
                imagePath: '/static/img/emoji/',
//                images: ['+1.png',
//                    '100.png',
//                    '109.png']
            },
            upload:{
                url: 'http://up-z1.qiniu.com/',
                params:{"token":"${token}"},
                fileKey:'file'
            }
        });
    });
</script>

</body>
</html>
