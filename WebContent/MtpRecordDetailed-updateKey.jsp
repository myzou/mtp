<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta charset="UTF-8">
    <title>MtpRecordDetailed-updateKey 页面</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!--     <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" /> -->
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" href="./css/font.css">
    <link rel="stylesheet" href="./css/xadmin.css">
    <script type="text/javascript" src="./js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="./lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="./js/xadmin.js"></script>
    <style type="text/css">
        .font12 {
            font-size: 12px;
        }
    </style>
    <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>


<body>
<div class="x-nav">
      <span class="layui-breadcrumb">
        <a href="">MTP</a>
        <a> <cite>mtp管理</cite></a>
      </span>
    <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right"
       href="javascript:location.replace(location.href);" title="刷新">
        <i class="layui-icon" style="line-height:30px">ဂ</i></a>
</div>
<div class="x-body">
    <form class="layui-form">
        <!--  认证口令 start  -->
        <div class="layui-form-item">
            <div class="layui-form-item">

            <label class="layui-form-label">
                <span class="x-red">*</span>帐号：
            </label>
            <div class="layui-input-inline">
                <input type="text" id="op_name" name="op_name" placeholder="请输入op帐号" autocomplete=“off”
                       class="layui-input">
            </div>

            <label class="layui-form-label">
                <span class="x-red">*</span>密码：
            </label>
            <div class="layui-input-inline">
                <input type="password" id="op_password" name="op_password" placeholder="请输入op密码" autocomplete="off"
                       class="layui-input">
            </div>

            <button class="layui-btn" onclick="updateKey()" type="button" title="更新执行命令的账号密码和密匙"><i class="layui-icon">&#xe614;</i>
            </button>
            </div>

            <div class="layui-form-item">
                <label   class="layui-form-label">
                    <span class="x-red">*</span>totp秘钥：
                </label>
                <div class="layui-input-block">
                <textarea placeholder="totp秘钥(2fa)：" id="totpKey" name="totpKey" class="layui-textarea"
                          lay-verify="required|totpKey"></textarea>
                </div>
            </div>

        </div>

        <!--  认证口令 end  -->
        <hr>


    </form>
</div>


<script>

    $(document).ready(function () {

    });

    /**
     * 设置select控件选中
     * @param selectId select的id值
     * @param checkValue 选中option的值
     * @author 标哥
     */
    function set_select_checked(selectId, checkValue) {
        var select = document.getElementById(selectId);
        for (var i = 0; i < select.options.length; i++) {
            if (select.options[i].value == checkValue) {
                select.options[i].selected = true;
                break;
            }
        }
    }


    <!-- use 开始-->
    var form, layedit, layer, laydate;
    layui.use(['jquery', 'form', 'layedit', 'layer', 'laydate'], function () {

        var $ = layui.jquery;
        form = layui.form;
        layer = layui.layer;
        laydate = layui.laydate;
        form.render();
        layui.form.render("select");


        //自定义验证规则
        form.verify({
            cabinetName: function (value) {
//         		  some code
            }
        });

    });

    <!--use 结束  -->

    function updateKey() {
        var op_name = $("#op_name").val();
        var op_password = $("#op_password").val();
        var totpKey = $("#totpKey").val();


        if (op_name == "" || op_password == ""||totpKey=="") {
            alert("帐号或者密码或者密匙不能为空");
            return false;
        }
        layer.confirm('是否修改帐号密码密匙?', {
            btn: ['修改', '取消'] //按钮
        }, function () {
            var jsonStr = new Object();
            jsonStr.op_name = op_name;
            jsonStr.op_password = op_password;
            jsonStr.totpKey=totpKey;
            $.ajax({
                url: "${pageContext.request.contextPath }/mtprd_updateKey.action",
                type: "post",
                async: "true",
                dataType: "json",
                data: "jsonStr=" + JSON.stringify(jsonStr),
                success: function (result) {
                    alert("修改帐号信息成功");
                    layer.msg('修改帐号信息成功', {offset: 't', time: 5000, icon: 1, btn: ['确定']});

//  				 layer.msg("帐号修改为:"+op_name+',密码修改为:'+op_password ,{offset:'t',time:5000, icon:1,btn:['确定']});
                }
            });
        }, function () {
        });
    }


</script>

</body>
</html>
