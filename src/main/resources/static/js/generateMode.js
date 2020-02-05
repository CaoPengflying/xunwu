/**
 * caopenglfying
 */

$(function () {
    //表单验证
    $("#generate-model").validate({
        rules: {
            desc: {
                required: true,
            },
            moduleName: {
                required: true,
            },
            auth: {
                required: true,
            },
            host: {
                required: true
            },
            port: {
                required: true,
            },
            username: {
                required: true
            },
            password: {
                required: true
            },
            projectName: {
                required: true,
            },
            templateType: {
                required: true,
            },
            database: {
                required: true,
            },
            tableName: {
                required: true,
            }
        },
        messages: { // 自定义提示信息
            templateType: '必须选择模板类型',
            projectName: '必须写项目名称',
            database: '必须选择数据库',
            username: '必须填写用户名',
            password: '必须填写密码',
            host: '必须填写数据库地址',
            port: '必须填写端口号',
            auth: '必须填写作者',
            moduleName: '必须填写模块名',
            desc: '必须填写功能描述'
        },
        errorPlacement: function (error, element) { //错误信息位置设置方法
            error.appendTo(element); //这里的element是录入数据的对象
        },
        onkeyup: false,
        focusCleanup: true,
        success: "valid",
        submitHandler: function (form) {
                $(form).ajaxSubmit({
                type: 'post',
                url: '/generate/generateModel', // 提交地址
                success: function (data) {
                    if (data.code === 200) {
                        alert('提交成功！');
                        window.location.href = data.data + "?attname=generateFile.zip";
                    } else {
                        layer.msg(data.message, {icon: 5, time: 2000});
                    }
                },
                error: function (request, message, e) {
                    layer.msg(request.responseText, {icon: 5, time: 2000});
                }
            });
            return false; //此处必须返回false，阻止常规的form提交
        }
    });
});
