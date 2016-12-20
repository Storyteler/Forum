$(function () {
    $("#regBtn").click(function () {
        $("#regForm").submit();
    });
    //validate方法中只有对象，都是键值对
    $("#regForm").validate({
        errorElemet:'span',
        //低版本为这个，高版本为texr-danger
        errorClass:'text-error',
        rules:{
            username:{
                required:true,
                //长度为一至六之间的字符
                rangelength:[1,6],
                //跳转至此servlet中进行判断
                remote:"/user/username"
            },
            password:{
                required:true,
                rangelength:[6,20]
            },
            again:{
                required:true,
                //与这个元素相同
                rangelength:[6,20],
                equalTo:"#password"
            },
            email:{
                required:true,
                //判断邮箱是否正确
                email:true,
                remote:"/user/email"
            },
            phone:{
                required:true,
                rangelength:[11,11],
                //判断是否全部为数字
                digits:true
            }
        },
        messages:{
            username:{
                required:"用户名不能为空",
                rangelength:"请输入符合要求的用户名长度",
                remote:"该用户名已被占用"
            },
            password:{
                required:"密码不能为空",
                rangelength:"密码长度应为6至20位"
            },
            again:{
                required:"确认密码不能为空",
                rangelength:"密码长度应为6至20位",
                equalTo:"两次输入的密码不一致"
            },
            email:{
                required:"邮箱不能为空",
                email:"请输入正确的邮箱",
                remote:"当前邮箱已被占用"
            },
            phone:{
                required:"电话不能为空",
                rangelength:"请输入正确的电话号码长度",
                digits:"请输入正确的电话号码格式"
            }
        },
        submitHandler:function () {
            $.ajax({
                //表单提交地址
                url:"/reg",
                //表单提交方式
                type:"post",
                //传递的值，get中是在url后面添加，serialize（）取得表单中所有元素
                data:$("#regForm").serialize(),
                //发送请求之前执行的函数
                beforeSend:function () {
                    //disabled属性为禁用
                    $("#regBtn").text("注册中...").attr("disabled","disabled")
                },
                //请求成功是执行的函数，data为返回的对象
                success:function (data) {
                    if(data.state == "success") {
                        swal("注册成功,请前往邮箱激活!", "OK", "success");
                        window.location.href = "/login"; 
                    } else {
                        sweetAlert(data.message,'', "error");
                    }
                },
                //请求失败的执行的函数
                error:function () {
                    sweetAlert("服务器异常",'', "error");
                },
                //请求完成时执行的函数
                complete:function () {
                    $("#regBtn").text("注册").removeAttr("disabled");
                }
            })
        }
        }
    );
});
