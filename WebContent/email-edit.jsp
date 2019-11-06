<%@page import="sun.awt.OSInfo.OSType" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta charset="UTF-8">
    <title>email-edit页面</title>
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
<div class="x-body">
    <form class="layui-form">

        <div class="layui-form-item">
            <label for="email_code" class="layui-form-label">
                <span class="x-red">*</span>发送邮箱：
            </label>
            <div class="layui-input-inline">
                <select lay-search="" id="email_uuid" name="email_uuid" lay-filter="email_code" lay-verify="required"
                        value="${cases.pop_name}"><!--     -->
                    <option value="">直接选择或搜索选择</option>
                </select>
            </div>

            <label class="layui-form-label font10" title="模版类型对应为邮件发送时候的语言">
                <span class="x-red">*</span>模版类型：
            </label>
            <div class="layui-input-inline">
                <select id="emailMode" name="emailMode" lay-verify="required">
                    <option value="简体中文">简体中文</option>
                    <option value="繁体中文">繁体中文</option>
                    <option value="English">English</option>
                </select>
            </div>
            <label for="yesOrNo" class="layui-form-label" style="margin-left: -10px;">
                <span class="x-red">*</span>编辑类型：
            </label>
            <div class="layui-input-inline" style="width: 140px;">
                <select id="edit_mode" name="edit_mode" lay-verify="required">
                    <option value="追加人员">追加人员</option>
                    <option value="普通发送">普通发送</option>
                </select>
            </div>
        </div>

        <div class="layui-form-item">
            <label for="caseID" class="layui-form-label">
                <span class="x-red">*</span>caseID：
            </label>
            <div class="layui-input-inline">
                <input type="text" id="caseID" name="caseID" required="" lay-verify="required"
                       autocomplete="off" class="layui-input">
            </div>

            <label for="yesOrNo" class="layui-form-label">
                <span class="x-red">*</span>是否发送：
            </label>
            <div class="layui-input-inline">
                <select id="yesOrNo" name="yesOrNo" lay-verify="required">
                    <option value="是">是</option>
                    <option value="否">否</option>
                </select>
            </div>

            <label for="insType" class="layui-form-label" title="可以更新此case 的状态" style="margin-left: -10px;">
                <span class="x-red">*</span>状态：
            </label>
            <div class="layui-input-inline" style="width: 140px;">
                <select id="case_status" name="case_status" lay-verify="required" lay-filter="case_status">
                    <option value="T">提交授权申请</option>
                    <option value="R">回复邮件</option>
                    <option value="S">确认授权完成</option>
                </select>
            </div>
        </div>

        <div class="layui-form-item">
            <label for="start" class="layui-form-label">
                <span class="x-red">*</span>开始时间：
            </label>
            <div class="layui-input-inline">
                <input type="text" class="layui-input" id="start_time" name="start_time" autocomplete="off"
                       lay-verify="required"/>
            </div>

            <label for="start" class="layui-form-label">
                <span class="x-red">*</span>结束时间：
            </label>
            <div class="layui-input-inline">
                <input type="text" class="layui-input" id="end_time" name="end_time" autocomplete="off"
                       lay-verify="required"/>
            </div>
            <!--        <div class="layui-input-inline">
                           <input type="button"  onclick="get()"  value="获取时间" />
                   </div> -->

        </div>

        <div class="layui-form-item">
            <label class="layui-form-label" for="cityNames">
                <span class="x-red">*</span>地区：
            </label>
            <div class="layui-input-inline">
                <select lay-search="" id="cityNames" name="cityNames" lay-filter="cityNames" lay-verify="required"
                        value="${cases.city_name}"><!--  -->
                </select>
            </div>

            <label class="layui-form-label" for="popNames">
                <span class="x-red">*</span>机房名称：
            </label>
            <div class="layui-input-inline">
                <select lay-search="" id="popNames" name="popNames" lay-filter="popNames" lay-verify="required">
                    <!--  -->
                    <option value="">直接选择或搜索选择</option>
                </select>
            </div>

        </div>


        <div class="layui-form-item">
            <label class="layui-form-label font12">
                <span class="x-red">*</span>收件人邮箱：
            </label>
            <div class="layui-input-block">
                <input type="text" id="addressee_email" name="addressee_email" required=""
                       lay-verify="required|addressee_email"
                       autocomplete="off" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label font12">
                <span class="x-red">*</span>抄送人邮箱：
            </label>
            <div class="layui-input-block">
                <input type="text" id="cc_addressee_email" name="cc_addressee_email" required=""
                       lay-verify="cc_addressee_email"
                       autocomplete="off" class="layui-input layui-unselect"
                       title="邮箱格式为xx@xx.com;xx@xx.com  多个邮箱用小写;割开">
            </div>
        </div>

        <div class="layui-form-item">
            <label for="cabinet" class="layui-form-label">
                <span class="x-red">*</span>机柜信息：
            </label>
            <div class="layui-input-block">
                <input type="text" id="cabinet" name="cabinet" required=""
                       autocomplete="off" class="layui-input" lay-verify="required">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">
                <span class="x-red">*</span>操作详细 :
            </label>
            <div class="layui-input-block">
                <input type="text" id="opType" name="opType" lay-verify="required" autocomplete="off"
                       class="layui-input" value="设备维护">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">
                备注 :
            </label>
            <div class="layui-input-block">
                <input type="text" id="remarks" name="remarks" autocomplete="off" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label for="intoPersonnel" class="layui-form-label">
                <span class="x-red">*</span>相关人员：
            </label>
            <!-- <div class="layui-input-block">
                <input type="text" id="intoPersonnel" name="intoPersonnel" lay-verify="required"
                autocomplete="off" class="layui-input">
            </div> -->
            <div class="layui-input-block">
                <textarea placeholder="申请相关人员信息：" id="intoPersonnel" name="intoPersonnel" class="layui-textarea"
                          lay-verify="required|intoPersonnel"></textarea>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">
                <span class="x-red">*</span>追加人员：
            </label>
            <div class="layui-input-block">
                <textarea placeholder="追加进入机房人员信息：" id="addIntoPersonnel" name="addIntoPersonnel" class="layui-textarea"
                          lay-verify="required|addIntoPersonnel"></textarea>
            </div>
        </div>

        <div class="layui-form-item">
            <label for="carryFacility" class="layui-form-label">
                <span class="x-red">*</span>携带设备：
            </label>
            <div class="layui-input-block">
                 <textarea id="carryFacility" name="carryFacility" class="layui-textarea" contenteditable="true"
                           lay-verify="required|carryFacility"
                           autocomplete="off">无</textarea>
            </div>
            <!--               <input  type="button"  onclick="test()" value="测试111" /> -->
        </div>


        <div class="layui-form-item">
            <label class="layui-form-label">
                上传文件：
            </label>
            <div class="layui-col-md12">
                <div class="modal fade" id="uploadModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                     aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                            </div>
                            <div class="">
                                <div class="layui-upload">
                                     
                                    <button type="button" class="layui-btn layui-btn-normal" id="chooseFile">选择附件
                                    </button>
                                     
                                    <button type="button" class="layui-btn" id="upload">开始上传</button>
                                    <!--  layui-hide -->
                                     
                                    <div class="layui-upload-list">
                                        <label class="layui-form-label">
                                        </label>
                                           
                                        <table class="layui-table" lay-size="sm">
                                                 
                                            <thead>
                                                   
                                            <tr>
                                                <th>文件名</th>
                                                       
                                                <th>大小</th>
                                                       
                                                <th>状态</th>
                                                       
                                                <th>操作</th>
                                                     
                                            </tr>
                                            </thead>
                                                 
                                            <tbody id="proImageList"></tbody>
                                               
                                        </table>
                                         
                                    </div>
                                    <!-- 					  <button type="button" class="layui-btn" id="back">返回</button> -->
                                </div>
                            </div><!-- /.modal-content -->
                        </div><!-- /.modal -->
                    </div>
                </div>
            </div>

            <!-- 隐藏input ,存放对应的上传表单信息-->
            <input type="hidden" id="upLoadFiles" name="upLoadFiles" autocomplete="off" value=""/>
            <!-- 隐藏input ,存放文件是否已经上传了-->
            <input type="hidden" id="upYesOrNo" name="upYesOrNo" autocomplete="off" value="false"/>
            <!-- 隐藏input ,存放手动改之前的抄送人value-->
            <input type="hidden" id="cc_addressee_email_hid" name="cc_addressee_email_hid" autocomplete="off" value=""/>
            <input type="hidden" id="case_uuid" name="case_uuid" autocomplete="off" value="false"/>
            <!--     防止重复提交 -->
            <input id="submit_flag" type="hidden" value="false"/>

            <div class="layui-form-item">
                <label class="layui-form-label">
                </label>
                <button id="add" class="layui-btn" type="button" lay-filter="add" lay-submit="">
                    发送email
                </button>
            </div>
        </div>

    </form>


    <script>


        $(document).ready(function () {
//     	alert(${param.case_id});

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

        function sleep(n) {
            var start = new Date().getTime();
            //  console.log('休眠前：' + start);
            while (true) {
                if (new Date().getTime() - start > n) {
                    break;
                }
            }
//         console.log('休眠后：' + new Date().getTime());
        }

        var form, layedit, layer, laydate, $;

        function test() {
            $("#popNames").find("option[ value='" + "luke测试机房" + "']").attr("selected", true);
            $("#cityNames").find("option[ value='" + "luke" + "']").attr("selected", true);
            alert($("#email_uuid").val());
            $("#email_uuid").find("option[ value='" + "563054c9f5ba4b509bc2846f9e5c47b2" + "']").attr("selected", true);
            layui.use(['jquery', 'form', 'layedit', 'layer', 'laydate'], function () {
                layui.form.render("select");
            });


        }


        function get() {
            var start_time = $('#start_time').val();
            var end_time = $('#end_time').val();
            alert(end_time > start_time);
            alert("开始时间：（" + start_time + "），结束时间：（" + end_time + ")");
        }


        layui.use(['jquery', 'form', 'layedit', 'layer', 'laydate'], function () {
            $ = layui.jquery;
            form = layui.form;
            layer = layui.layer;
            laydate = layui.laydate;
            var nowTime = new Date().valueOf();
            var max = null;
            layui.form.render("select");
            InitializationData();  	//初始化 加载select数据

            var start = laydate.render({
                elem: '#start_time',
                type: 'datetime',
                format: 'yyyy-MM-dd HH:mm:ss'
            });

            var end = laydate.render({
                elem: '#end_time',
                type: 'datetime',
                format: 'yyyy-MM-dd HH:mm:ss'
            });

            form.on('select(case_status)', function (data) {
                var case_status = data.value;
                var case_uuid = $("#case_uuid").val();
                console.log(case_status);
                layer.confirm('确定要修改case的状态吗？', {
                    btn: ['确定', '取消']//按钮
                }, function (index) {
                    $.ajax({//加载所有的城市
                        url: "${pageContext.request.contextPath }/mail_caseEmailUpdate.action",
                        type: "post",
                        async: "true",
                        data: "case_status=" + case_status + "&case_uuid=" + case_uuid,
                        dataType: "json",
                        success: function (result) {
                            layer.msg("编辑成功,已发送授权邮件", {time: 500, icon: 6}, function () {
                                // 获得frame索引
                                var index = parent.layer.getFrameIndex(window.name);
                                //关闭当前frame
                                parent.layer.close(index);
                                window.parent.location.reload();
                            });

                        }
                    });


                });


            });


            form.on('select(cityNames)', function (data) {//设置拦截器cityNames 改变或者点击事件
                var popName = data.value;
// 			  alert (popName);
                if (popName == "") {
                    $.ajax({//加载所有的城市
                        url: "${pageContext.request.contextPath }/mail_loadEmpowerMessageByDao.action",
                        type: "post",
                        async: "true",
                        data: "",
                        dataType: "json",
                        success: function (result) {
                            $('#popNames').empty();
                            $('#popNames').append(" <option value='' >直接选择或搜索选择</option> ");    // 给select 添加option子标签
                            for (var val1 in result) {
                                var append = "  <option   value='" + result[val1]['pop_name'] + "'>" + result[val1]['pop_name'] + "</option>";
//		   				 alert(append);
                                $('#popNames').append(append);    // 给select 添加option子标签
                            }
                            layui.form.render("select");
                        }
                    });
                } else {
                    $.ajax({
                        url: "${pageContext.request.contextPath }/mail_loadPopNames.action",
                        type: "post",
                        async: "true",
                        data: "popName=" + popName,
                        dataType: "json",
                        success: function (result) {
                            $('#popNames').empty();
                            $('#popNames').append(" <option value='' >直接选择或搜索选择</option> ");    // 给select 添加option子标签
                            for (var i = 0; i < result.length; i++) {
                                $('#popNames').append("<option value='" + result[i] + "'>" + result[i] + "</option>");    // 给select 添加option子标签
                            }
                            layui.form.render("select");
                        }
                    });
                }


            });

            form.on('select(popNames)', function (data) {//设置拦截器popNames 改变或者点击事件
                var popName = data.value;
                // 			  alert (popName);
                $.ajax({
                    url: "${pageContext.request.contextPath }/mail_getEmpowerMessage.action",
                    type: "post",
                    async: "true",
                    data: "popName=" + popName,
                    dataType: "json",
                    success: function (result) {
// 				   layer.alert(JSON.stringify(result[0]) );
// 				  alert(result[0]['city_name']);
                        set_select_checked('cityNames', result[0]['city_name']);

                        var reg = new RegExp("###", "g");//g,表示全部替换。
                        var addressee_email = result[0].addressee_email.replace(reg, ';');
                        var cc_addressee_email = result[0].cc_addressee_email.replace(reg, ';');
                        $("#addressee_email").val(addressee_email);
                        $("#cc_addressee_email").val(cc_addressee_email);
                        $("#cc_addressee_email_hid").val(cc_addressee_email);
                        if (addressee_email == "") {//如果没有收件人邮箱 改为可以编辑
                            $("#addressee_email").removeAttr("disabled");
                            $("#addressee_email").removeClass("layui-disabled");
                            $("#addressee_email").attr("title", "请手动输入正确的收件人邮箱，多个邮箱小写;割开");
// 						layer.msg('没有找到该机房的收件人邮箱');
                        } else {
                            $("#addressee_email").attr("disabled", "disabled");
                            $("#addressee_email").addClass("layui-disabled");
                            $("#addressee_email").attr("title", "不可编辑，需要增加邮箱请增加在抄送人邮箱");
                        }
// 					alert("addressee_email:"+addressee_email);
// 					alert("cc_addressee_email:"+cc_addressee_email);


                        layui.form.render("select");
                    }
                });

            });


            //自定义验证规则
            form.verify({
                cc_addressee_email: function (value) {
                    var cc_addressee_email = $("#cc_addressee_email").val();
                    var cc_addressee_email_hid = $("#cc_addressee_email_hid").val();
                    var reg = /^(\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*(\;))*\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
                    if (cc_addressee_email != "" && !reg.test(cc_addressee_email)) {
                        return '你的抄送人邮箱格式有错';
                    }
                    if (cc_addressee_email_hid != "" && !(cc_addressee_email.indexOf(cc_addressee_email_hid) > -1)) {
                        return '请不要修改原来的抄送人邮箱信息';
                    }
                },
                addressee_email: function (value) {
                    var addressee_email = $("#addressee_email").val();
                    var reg = /^(\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*(\;))*\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
                    if (addressee_email != "" && !reg.test(addressee_email)) {
                        return '你的收件人邮箱格式有错';
                    }
                }, intoPersonnel: function (value) {
                    if (value.length > 1000) {
                        return '相关人员文字长度不能大于1000';
                    }
                }, addIntoPersonnel: function (value) {
                    if (value.length > 1000) {
                        return '追加人员文字长度不能大于1000';
                    }
                }, carryFacility: function (value) {
                    if (value.length > 1000) {
                        return '携带设备文字长度不能大于400';
                    }
                }
            });

            //监听提交
            form.on('submit(add)', function (data) {
                var JsonStr = JSON.stringify(data.field);
                var trList = $('#proImageList').find('tr');
                var error = "";
                for (var i = 0; i < trList.length; i++) {
                    var tdArr = trList.eq(i).find("td");
                    if (!(tdArr.eq(2).text().indexOf("上传成功") > -1)) {
                        error = "false";
//        	               		layer.alert(tdArr.eq(0).text());
                        layer.msg("有文件没有上传，正在上传...", {time: 1000});
                        $("#upload").trigger("click");
                        return false;
                    }
                }

                var submit_flag = $("#submit_flag").val();
                if (submit_flag != "false") {

                    layer.confirm('您已经提交过这个表单了，再次提交吗？', {
                        btn: ['确定', '取消'] //按钮
                    }, function () {

                        $.ajax({
                            url: "${pageContext.request.contextPath }/mail_sendEmail.action",
                            type: "post",
                            async: "true",
                            data: "JsonStr=" + JsonStr,
                            dataType: "json",
                            success: function (result) {
                                layer.msg("修改成功，已经发送邮件", {icon: 6, time: 1000}, function () {
                                    // 获得frame索引
                                    var index = parent.layer.getFrameIndex(window.name);
                                    //关闭当前frame
                                    parent.layer.close(index);
                                    window.parent.location.reload();
                                });

                            }
                        });
                    }, function () {
                    });
                } else {
                    $("#submit_flag").val("true");
                    $.ajax({
                        url: "${pageContext.request.contextPath }/mail_sendEmail.action",
                        type: "post",
                        async: "true",
                        data: "JsonStr=" + JsonStr,
                        dataType: "json",
                        success: function (result) {
                            layer.msg("修改成功，已经发送邮件", {icon: 6, time: 1000}, function () {
                                // 获得frame索引
                                var index = parent.layer.getFrameIndex(window.name);
                                //关闭当前frame
                                parent.layer.close(index);
                                window.parent.location.reload();
                            });

                        }
                    });
                }

            });


        });


        /** 开始就加载cityNames 地区**/
        function InitializationData() {
            $.ajax({//加载所有的城市
                url: "${pageContext.request.contextPath }/mail_loadEmpowerMessageByDao.action",
                type: "post",
                async: "false",
                dataType: "json",
                success: function (result) {
//  				layer.msg(JSON.stringify(result));
                    for (var val1 in result) {
                        var append = "  <option   value='" + result[val1]['pop_name'] + "'>" + result[val1]['pop_name'] + "</option>";
//  				 alert(append);
                        $('#popNames').append(append);    // 给select 添加option子标签
                    }
                }
            });

            $.ajax({//加载所有地区
                url: "${pageContext.request.contextPath }/mail_loadCityNames.action",
                type: "post",
                async: "false",
                dataType: "json",
                success: function (result) {
                    for (var i = 0; i < result.length; i++) {//设置北上广深在前面
//					alert("<option value='"+result[i]+"'>"+result[i]+"</option>");
                        var arr = ['北京', '上海', '广州', '深圳'];
                        var a = arr.indexOf(result[i]);
                        if (arr.indexOf(result[i]) > -1) {
                            $('#cityNames').prepend("<option value='" + result[i] + "'>" + result[i] + "</option>");    // 给select 添加option子标签
                        } else {
                            $('#cityNames').append("<option value='" + result[i] + "'>" + result[i] + "</option>");    // 给select 添加option子标签
                        }
                    }
                    $('#cityNames').prepend("	<option value=''>直接选择或点我搜索全部</option>");
                    $("#cityNames").val("");

                }
            });

// 		     sleep(300);
            $.ajax({//加载所有发送的email
                url: "${pageContext.request.contextPath }/mail_loadSendEmail.action",
                type: "post",
                async: "false",
                dataType: "json",
                success: function (result) {
                    for (var prop  in result) {
// 						  alert(JSON.stringify(result[prop] ));//遍历外层的json对象的字符串
// 						  title='"+ result[prop]['email_name']+"' 
// 						  var append="  <option  title='"+ result[prop]['email_name']+"'  value='"+  result[prop]['email_uuid']  +"'>"+ result[prop]['email_code']+"</option>";
                        var append = "  <option   value='" + result[prop]['email_uuid'] + "'>" + result[prop]['email_code'] + "</option>";
// 						  alert(append);
                        $('#email_uuid').append(append);    // 给select 添加option子标签
                        for (var val in  result[prop]) {
// 							  alert("result[" + prop + "]."+val+"=" + result[prop][val]);  //遍历第二层所有字符串
                        }
                    }
                    var case_uuid = '${param.case_uuid}';
                    $.ajax({//加载页面信息
                        url: "${pageContext.request.contextPath }/mail_getCaseEmail.action",
                        type: "post",
                        async: "false",
                        dataType: "json",
                        data: "case_uuid=" + case_uuid,
                        success: function (result0) {

                            //					 	layer.alert(JSON.stringify(result0[0]));
                            $("#email_uuid").find("option[ value='" + result0[0].send_email_uuid + "']").attr("selected", true);
                            $("#send_email").val(result0[0].send_email);
                            $("#caseID").val(result0[0].case_id);
// 									$("#ins_type").val(result0[0].ins_type);
                            $("#start_time").val(result0[0].start_time);
                            $("#end_time").val(result0[0].end_time);
                            $("#popNames").find("option[ value='" + result0[0].pop_name + "']").attr("selected", true);
                            $("#cityNames").find("option[ value='" + result0[0].city_name + "']").attr("selected", true);
                            var reg = new RegExp("###", "g");//g,表示全部替换。
                            $("#addressee_email").val(result0[0].addressee_email.replace(reg, ';'));
                            $("#cc_addressee_email").val(result0[0].cc_addressee_email.replace(reg, ';'));
                            $("#cabinet").val(result0[0].cabinet);
                            $("#opType").val(result0[0].op_type);
                            $("#intoPersonnel").val(result0[0].into_personnel);
                            $("#addIntoPersonnel").val(result0[0].add_into_personnel);
                            $("#carryFacility").val(result0[0].carry_facility);
                            $("#case_uuid").val(result0[0].case_uuid);
                            $("#remarks").val(result0[0].remarks);

                            if (result0[0].case_status == 'D') {
                                $("#add").addClass("layui-btn-disabled");
                                $("#add").attr('disabled', true);
                                $("#add").attr('title', '该单已经删除，无法编辑发送');
                            }
                            layui.form.render("select");
                        }
                    });

// 					  form.render("select"); 
                }
            });

        }


    </script>
    <script>

        var uploadListIns;
        //多文件列表示例  图片上传

        var form, layedit, layer, laydate, $;
        layui.use(['jquery', 'layedit', 'layer', 'laydate', 'upload'], function () {
            $ = layui.jquery;
            layer = layui.layer;
            laydate = layui.laydate;

            upload = layui.upload;
// 	      layui.use('upload', function(){
// 	       var $ = layui.jquery,upload = layui.upload;
            var demoListView = $('#proImageList');
            uploadListIns = upload.render({
                elem: '#chooseFile', //选择文件的按钮
                url: '${pageContext.request.contextPath }/mail_uploadImg.action', //后台处理文件长传的后台方法
                data: {'serviceName': '外协订单供应商上传检验报告', 'tableName': 'T_OUTSOURCE_ORDER', 'fileType': '图片'},//传到后台的方法
                accept: 'file',
// 	        size:'5120',
                method: 'post',
                multiple: true,  //是否允许多文件上传
// 	        acceptMime: 'image/*', //规定打开文件选择框时，筛选出的文件类型
                field: 'file',  //设定文件域的字段名
                drag: 'true',//是否能接受拖拽文件上传
                auto: false, //选择文件后是否自动上传
                bindAction: '#upload', //用来触发上传的按钮ID
                before: function (obj) {
// 	        	 console.log('接口地址：'+ this.url, this.item, {tips: 1});
// 	        	layer.load();
                },
                choose: function (obj) { //选择文件后的回调函数，本例中在此将选择的文件进行展示
                    $("#upload").trigger("click");
                    var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
                    //读取本地文件
                    obj.preview(function (index, file, result) {
                        var tr = $(['<tr id="upload-' + index + '">'
                            , '<td>' + file.name + '</td>'
                            , '<td>' + (file.size / 1014).toFixed(1) + 'kb</td>'
                            , '<td>等待上传</td>'
                            , '<td>'
                            , '<button class="layui-btn layui-btn-xs demo-reload layui-hide">重传</button>'
                            , '<button class="layui-btn layui-btn-xs layui-btn-danger demo-delete">删除</button>'
                            , '</td>'
                            , '</tr>'].join(''));

                        //单个重传
                        tr.find('.demo-reload').on('click', function () {
                            obj.upload(index, file);
                        });

                        //删除
                        tr.find('.demo-delete').on('click', function () {
                            var upLoadFiles = $("#upLoadFiles").val();
                            var upLoadStrings = upLoadFiles.split("###");
                            var newUpLoadFiles = "";
                            for (var a in upLoadStrings) {
                                var delFileName = tr.eq(0).find("td").eq(0).text();
                                var sub = upLoadStrings[a].substring(upLoadStrings[a].lastIndexOf("\\") + 1);
                                if (delFileName != sub) {
                                    if (newUpLoadFiles == "") {
                                        newUpLoadFiles = newUpLoadFiles + upLoadStrings[a];
                                    } else {
                                        newUpLoadFiles = newUpLoadFiles + "###" + upLoadStrings[a];
                                    }
                                }
                            }
                            $("#upLoadFiles").val(newUpLoadFiles);
                            delete files[index]; //删除对应的文件
                            tr.remove();
                            uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
                        });
                        demoListView.append(tr);
                    });
                },
                done: function (res, index, upload) {    //多文件上传时，只要有一个文件上传成功后就会触发这个回调函数
// 	         console.info(res);
// 	        alert(JSON.stringify(res));
                    if (res.status == "success") { //上传成功
                        var tr = demoListView.find('tr#upload-' + index)
                            , tds = tr.children();
                        tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
// 	       tds.eq(3).html('<a href="'+res.url+'" rel="external nofollow" >查看</a>'); //清空操作
// 			tds.eq(3).html('<button class="layui-btn layui-btn-xs layui-btn-danger demo-delete">删除</button>');
                        var upLoadFiles = $("#upLoadFiles").val();
                        var fullPath = res.fullPath;
                        if (upLoadFiles.indexOf(fullPath) < 0) {
                            if (upLoadFiles == "") {
                                $("#upLoadFiles").val(upLoadFiles + fullPath);//第一次不分割
                            } else {
                                $("#upLoadFiles").val(upLoadFiles + "###" + fullPath);//###分割开
                            }
                        }
                        return delete this.files[index]; //删除文件队列已经上传成功的文件
                    } else {//如果status不是success
                        alert(res.message);
                    }
                    this.error(index, upload);
                },
                allDone: function (obj) { //当文件全部被提交后，才触发
// 	        	 console.log(JSON.stringify(obj));
                    if (obj.total > obj.successful) {
                        layer.msg("有文件上传失败，暂不更新生产进度，请重试或联系管理员");
                    } else {
                        //更新生产进度
// 	          updateProductionSchedule(currentId, currentSchedule);
                    }
                },
                error: function (index, upload) {
                    var tr = demoListView.find('tr#upload-' + index)
                        , tds = tr.children();
                    tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
                    tds.eq(3).find('.demo-reload').removeClass('layui-hide'); //显示重传
                }
            });
            $(".layui-upload-file").hide();
        });


    </script>


</body>

</html>
