
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/static/css/sweetalert.css">
    <link rel="stylesheet" type="text/css" href="/static/js/webuploader/webuploader.css">
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>
<%@include file="demo.jsp"%>
<!--header-bar end-->
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-cog"></i> 基本设置</span>
        </div>

        <form action="" id="emailForm" class="form-horizontal">
            <div class="control-group">
                <label class="control-label">账号</label>
                <div class="controls">
                    <input type="text" readonly>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">电子邮件</label>
                <div class="controls">
                    <input name="email" type="text">
                </div>
            </div>
            <div class="form-actions">
                <button type="button" id="emailBtn" class="btn btn-primary">保存</button>
            </div>

        </form>

    </div>
    <!--box end-->
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-key"></i> 密码设置</span>
            <span class="pull-right muted" style="font-size: 12px">如果你不打算更改密码，请留空以下区域</span>
        </div>

        <form action="" id="passwordForm" class="form-horizontal">
            <div class="control-group">
                <label class="control-label">原始密码</label>
                <div class="controls">
                    <input name="oldPassword" type="password">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">密码</label>
                <div class="controls">
                    <input name="newPassword" id="newPassword" type="password">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">重复密码</label>
                <div class="controls">
                    <input name="rePassword" type="password">
                </div>
            </div>
            <div class="form-actions">
                <button type="button" id="passwordBtn" class="btn btn-primary">保存</button>
            </div>

        </form>

    </div>
    <!--box end-->

    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-user"></i> 头像设置</span>
        </div>

        <form action="" class="form-horizontal">
            <div class="control-group">
                <label class="control-label">当前头像</label>
                <div class="controls">
                    <img id="avatar" src="http://oigkz9w96.bkt.clouddn.com/${sessionScope.user.avatar}?imageView2/1/w/60/h/60" class="img-circle" alt="">
                </div>
            </div>
            <hr>
            <p style="padding-left: 20px">关于头像的规则</p>
            <ul>
                <li>禁止使用任何低俗或者敏感图片作为头像</li>
                <li>如果你是男的，请不要用女人的照片作为头像，这样可能会对其他会员产生误导</li>
            </ul>
            <div class="form-actions">
                <div id="picker">上传新头像</div>
            </div>


        </form>

    </div>
    <!--box end-->
</div>
<script src="/static/js/jquery-1.11.1.js"></script>
<script src="/static/js/sweetalert.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/webuploader/webuploader.js"></script>
<script src="/static/js/user/setting.js"></script>

<script>
    $(function () {
        var uploader = WebUploader.create({
            // 选完文件后，是否自动上传。
            auto: true,
            // swf文件路径
            swf: '/static/js/Uploader.swf',
            // 文件接收服务端。
            server: 'http://up-z1.qiniu.com/',
            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: '#picker',
            //{Object} [可选] [默认值：'file']  设置文件上传域的name
            fileVal:"file",
            //{Object} [可选] [默认值：{}] 文件上传请求的参数表，每次发送都会发送此对象中的参数。
            formData:{"token":"${token}"},
            // 只允许选择图片文件。
            accept: {
                title: 'Images',
                extensions: 'gif,jpg,jpeg,bmp,png',
                mimeTypes: 'image/*'
            }
        });
        //上传成功
        uploader.on('uploadSuccess',function(file,data){
            var fileKey = data.key;
            //修改数据库中的值
            $.post("/setting?action=avatar",{'fileKey':fileKey})
                .done(function (data) {
                    if(data.state == 'success') {
                        var url = "http://oigkz9w96.bkt.clouddn.com/"+fileKey;
                        $("#avatar").attr("src",url+"?imageView2/1/w/60/h/60");
                        $("#demo_avatar").attr("src",url+"?imageView2/1/w/30/h/30");
                        swal("头像设置成功!", "OK", "success");
                    }
                }).error(function(){
                sweetAlert(data.message,'', "error");
            });
        });
        //上传失败
        uploader.on('uploadError',function(){
            sweetAlert("上传失败,请稍后再试",'', "error");
        });
    });
</script>
<!--container end-->
</body>
</html>
