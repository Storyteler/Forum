$(function () {
    $("#type").change(function () {
        var val = $(this).val();
        if(val=="email") {
            $("#typename").text("电子邮件");
        } else if(val=="phone") {
            $("#typename").text("电话号码");
        }
    });

    $("#foundBtn").click(function () {
        $("#foundForm").submit();
    });

    $("#foundForm").validate({
        errorElemet: "span",
        errorClass: "text-error",
        rules: {
            value: {
                required: true
            }
        },
        messages: {
            value: {
                required: "本项必填"
            }
        },
        submitHandler:function (form) {
            $.ajax({
                url:"/found",
                type:"post",
                data:$(form).serialize(),
                beforeSend:function () {
                    $("#foundBtn").text("找回中...").attr("disabled","disabled");
                },
                success:function (data) {
                    if(data.state == "success") {
                        swal("请前往邮箱，重制密码!", "OK", "success",function () {
                            window.location.href = "/login";
                        });
                    } else {
                        sweetAlert(data.message,'', "error");
                    }
                },
                error:function () {
                    sweetAlert("服务器异常",'', "error");
                },
                complete:function () {
                    $("#foundBtn").text("提交").removeAttr("disabled");
                }
            })
        }
    });

});