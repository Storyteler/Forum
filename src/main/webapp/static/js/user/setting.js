$(function () {
    $("#emailBtn").click(function () {
        $("#emailForm").submit();
    });

    $("#emailForm").validate({
        errorElemet:"span",
        errorClass:"text-error",
        rules:{
            email:{
                required:true,
                email:true,
                remote:"/user/email?type=1"
            }
        },
        messages:{
            email:{
                required:"请填写邮箱地址",
                email:"请输入正确的邮箱地址",
                remote:"此邮箱已被占用"
            }
        },
        submitHandler:function (form) {
            $.ajax({
                url:"/setting?action=email",
                type:"post",
                data:$(form).serialize(),
                beforeSend:function () {
                    $("#emailBtn").text("保存中").attr("disabled","disabled")
                },
                success:function (data) {
                    if(data.state == "success") {
                        alert("邮箱修改成功");
                    } else {
                        alert(data.message);
                    }
                },
                error:function () {
                    alert("服务器异常");
                },
                complete:function () {
                    $("#emailBtn").text("保存").removeAttr("disabled")
                }
            })
        }
    });

    $("#passwordBtn").click(function () {
        $("#passwordForm").submit();
    });

    $("#passwordForm").validate({
        errorElemet:"span",
        errorClass:"text-error",
        rules:{
            oldPassword:{
                required:true,
                rangelength:[6,18],
            },
            newPassword:{
                required:true,
                rangelength:[6,18],
            },
            rePassword:{
                required:true,
                rangelength:[6,18],
                equalTo:"#newPassword"
            }
        },
        messages:{
            oldPassword:{
                required:"请输入原始密码",
                rangelength:"长度为6--18位",
            },
            newPassword:{
                required:"请输入新密码",
                rangelength:"长度为6--18位",
            },
            rePassword:{
                required:"请输入确认密码",
                rangelength:"长度为6--18位",
                equalTo:"两次密码不一致"
            }
        },
        submitHandler:function (form) {
            $.ajax({
                url:"/setting?action=password",
                type:"post",
                data:$(form).serialize(),
                beforeSend:function () {
                    $("#passwordBtn").text("保存中").attr("disabled","disabled")
                },
                success:function (data) {
                    if(data.state == "success") {
                        alert("密码修改成功，请前往登陆");
                        window.location.href = "/login";
                    } else {
                        alert(data.message);
                    }
                },
                error:function () {
                    alert("服务器异常");
                },
                complete:function () {
                    $("#passwordBtn").text("保存").removeAttr("disabled")
                }
            })
        }
    });
});