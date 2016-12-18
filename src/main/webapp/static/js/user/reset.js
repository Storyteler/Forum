$(function () {
    $("#resetBtn").click(function () {
        $("#resetForm").submit();
    });

    $("#resetForm").validate({
        errorElemet:"span",
        errorClass:"text-error",
        rules:{
            password:{
                required:true,
                rangelength:[6,18]
            },
            again:{
                required:true,
                rangelength:[6,18],
                equalTo:"#password"
            }
        },
        messages:{
            password:{
                required:"密码不能为空",
                rangelength:"请输入正确的密码长度"
            },
            again:{
                required:"确认密码不能为空",
                rangelength:"确认密码长度有误",
                equalTo:"两次输入不一致"
            }
        },
        submitHandler:function (form) {
            $.ajax({
                url:"/reset",
                type:"post",
                data:$(form).serialize(),
                beforeSend:function () {
                    $("#resetBtn").text("重置中").attr("disabled","disabled")
                },
                success:function (data) {
                    if(data.state == "success") {
                        alert("重置成功，请重新登录");
                        window.location.href = "/login";
                    } else {
                        alert(data.message);
                    }
                },
                error:function () {
                    alert("服务器异常");
                },
                complete:function () {
                    $("#resetBtn").text("保存").removeAttr("disabled")
                }
            })
        }
    });
});
