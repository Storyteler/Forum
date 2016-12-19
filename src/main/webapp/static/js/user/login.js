$(function () {

    function getParameterByName(name, url) {
        if (!url) {
            url = window.location.href;
        }
        name = name.replace(/[\[\]]/g, "\\$&");
        var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
            results = regex.exec(url);
        if (!results) return null;
        if (!results[2]) return '';
        return decodeURIComponent(results[2].replace(/\+/g, " "));
    }

    $("#loginBtn").click(function () {
        $("#loginForm").submit();
    });

    $("#loginForm").validate({
        errorElement:"span",
        errorClass:"text-error",
        rules:{
            username:{
                required:true
            },
            password:{
                required:true
            }
        },
        messages:{
            username:{
                required:"用户名不能为空"
            },
            password:{
                required:"密码不能为空"
            }
        },
        submitHandler:function (form) {
            $.ajax({
                url:"/login",
                type:"post",
                data:$(form).serialize(),
                beforeSend:function () {
                    $("#loginBtn").text("登录中").attr("disabled","disabled");
                },
                success:function (data) {
                    if(data.state == "success") {
                        alert("登陆成功");
                        var url = getParameterByName("redirect");
                        if (url) {
                            window.location.href = url;
                        } else {
                            window.location.href = "/home";
                        }
                    } else {
                        alert(data.message);
                    }
                },
                error:function () {
                    alter("服务器异常")
                },
                complete:function () {
                   $("#loginBtn").text("登陆").removeAttr("disabled");
                }
            })
        }
    });
});