$(function(){

    $("#newTopicBtn").click(function () {
        $("#newTopicForm").submit();
    });

    $("#newTopicForm").validate({
        errorElemet:"span",
        errorClass:"text-error",
        rules:{
            title:{
                required:true
            },
            nodeName:{
                required:true
            }
        },
        messages:{
            title:{
                required:"请输入标题"
            },
            nodeName:{
                required:"请选择节点"
            }
        },
        submitHandler:function (form) {
            $.ajax({
                url:"/newTopic",
                type:"post",
                data:$(form).serialize(),
                beforeSend:function () {
                    $("#newTopicBtn").text("发布中...").attr("disabled","disabled");
                },
                success:function (json) {
                    if (json.state == "success") {
                        swal("发帖成功","","success");
                        window.location.href = "/topic?id=" + json.data.id;
                    } else {
                        swal(json.message,"","error")
                    }
                },
                error:function () {
                    swal("服务器异常","","error");
                },
                complete:function () {
                    $("#newtopicBtn").text("发布主题").removeAttr("disabled");
                }
            })
        }
    });

});
