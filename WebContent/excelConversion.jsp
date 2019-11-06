<%--
  Created by IntelliJ IDEA.
  User: op1768
  Date: 2019/9/1
  Time: 20:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>excel转换为参数</title>
</head>
<link rel="stylesheet" href="./css/font.css">
<link rel="stylesheet" href="./css/xadmin.css">
<script type="text/javascript" src="./js/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="./lib/layui/layui.js" charset="utf-8"></script>
<script type="text/javascript" src="./js/xadmin.js"></script>
<body>

<div class="x-nav">

    <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right" href="javascript:location.replace(location.href);" title="刷新">
        <i class="layui-icon" style="line-height:30px">ဂ</i></a>
</div>
<b>xlsx 列排序：INTERNALSITEID	      PEROUTER	PEPORT	PE_WAN_IP	CE_WAN_IP  </b><br>
<b>文件必须xlsx或者csv格式，文件名为case号,执行一条需要4秒左右</b><br>
<b>执行after前必须执行before </b><br>


<div class="layui-form-item" title="excel必须为xlsx或者csv">

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
                             
                            <button type="button" class="layui-btn layui-btn-normal" id="chooseFile">选择附件</button>
                             
                            <button type="button" class="layui-btn" id="upload">开始上传</button><!--  layui-hide -->
                             
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
    <!--     防止重复提交 -->
    <input id="submit_flag" type="hidden" value="false"/>

    <div class="layui-form-item">

        <button class="layui-btn" type="button"  onclick="beforeAdd()" lay-submit="">
            before
        </button>
        <button class="layui-btn" type="button"   onclick="afterAdd()" lay-submit="">
            after
        </button>

    </div>
</div>
<div id="returnResult" lay-ignore="">
    <p></p>
</div>

<div id="test">
    <p></p>
</div>

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
            multiple: false,  //是否允许多文件上传
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
                demoListView.find("tr").remove();
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
                    $("#upLoadFiles").val( fullPath);//只取最后一次的文件
                    var nowDate=fnDate();
                    $("#returnResult").append("<p>"+nowDate+" 上传了文件:" + $("#upLoadFiles").val() + "</p>");
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

<script>

    $(document).ready(function () {
        $("#btn1").click(function () {
            var ppp = "Appended text";
            $("#test").append(" <p>" + ppp + "</p>");
        });


    });
    
    function  validateUpLoadFiles(upLoadFiles) {
        if(upLoadFiles==null||upLoadFiles==undefined||(upLoadFiles.indexOf(".xlsx")==-1&&upLoadFiles.indexOf(".csv")==-1)){
            alert("请先上传excel文件，已经上传文件请点击开始上传");
            return "请先上传excel文件，已经上传文件请点击开始上传";
        }
        if(upLoadFiles.indexOf("#")>-1){
            alert("文件名请不要携带特殊字符，直接填写caseId即可");
            return "文件名请不要携带特殊字符，直接填写caseId即可";
        }
        return "";
    }

    function afterAdd() {
        var upLoadFiles = $("#upLoadFiles").val();
        var nowDate=fnDate();
        var validateString=validateUpLoadFiles(upLoadFiles);
        if(validateString=="请先上传excel文件，已经上传文件请点击开始上传"){
            $("#returnResult").append("<pre>"+nowDate+" 请先上传excel文件，已经上传文件请点击开始上传:" + upLoadFiles + "</pre>");
            return false;
        }
        if(validateString!=""){
            $("#returnResult").append("<pre>"+nowDate+"  "+validateString+"</pre>");
            return false;
        }
        $("#returnResult").append("<pre>"+nowDate+" 上传的文件是:" + upLoadFiles + "</pre>");
        var afterUrl = "";
        $.ajax({
            url: "${pageContext.request.contextPath }/param_getParam.action",
            type: "post",
            async: "true",
            data: "upLoadFiles=" + upLoadFiles + "&period=after",
            dataType: "text",
            success: function (result) {
                $("#returnResult").append("<pre>"+nowDate+" :" + result + "</pre>");

            }
        });
    }



    function beforeAdd() {
        var upLoadFiles = $("#upLoadFiles").val();
        var validateString=validateUpLoadFiles(upLoadFiles);
        var nowDate=fnDate();
        if(validateString=="请先上传excel文件，已经上传文件请点击开始上传"){
            $("#returnResult").append("<pre>"+nowDate+" 请先上传excel文件，已经上传文件请点击开始上传:" + upLoadFiles + "</pre>");
            return false;
        }
        if(validateString!=""){
            $("#returnResult").append("<pre>"+nowDate+"  "+validateString+"</pre>");
            return false;
        }
        $("#returnResult").append("<pre>"+nowDate+" 上传的文件是:" + upLoadFiles + "</pre>");
        var afterUrl = "";
        $.ajax({
            url: "${pageContext.request.contextPath }/param_getParam.action",
            type: "post",
            async: "true",
            data: "upLoadFiles=" + upLoadFiles + "&period=before",
            dataType: "text",
            success: function (result) {
                $("#returnResult").append("<pre>"+nowDate+" :" + result + "</pre>");
            },error: function (result) {
                alert(JSON.stringify(result));
            }
        });
    }

    //js 获取当前时间
    function fnDate(){
        var timeDiv=document.getElementById("time");
        var dateTime = new Date();
        var year=dateTime.getFullYear();
        var month=dateTime.getMonth()+1;
        var ri=dateTime.getDate();
        var day;
        if(dateTime.getDay()==6){
            day="六";}
        if(dateTime.getDay()==1){
            day="一";}
        if(dateTime.getDay()==2){
            day="二";}
        if(dateTime.getDay()==3){
            day="三";}
        if(dateTime.getDay()==4){
            day="四";}
        if(dateTime.getDay()==5){
            day="五";}
        if(dateTime.getDay()==0){ day="日";}
        var hours = dateTime.getHours();
        var minutes = dateTime.getMinutes();
        var seconds = dateTime.getSeconds();
        var date=fnW(year)+"年"+fnW(month)+"月"+fnW(ri)+"日  "+fnW(hours) + ":" + fnW(minutes) + ":" + fnW(seconds);//+ "  星期"+day;
        return date;
    }

    //补位 当某个字段不是两位数时补0
    function fnW(str){
        var num;
        str>=10?num=str:num="0"+str;
        return num;
    }

    var form, layedit, layer, laydate, $;
    layui.use(['jquery', 'form', 'layedit', 'layer', 'laydate'], function () {
        $ = layui.jquery;
        form = layui.form;
        layer = layui.layer;
        laydate = layui.laydate;
        var nowTime = new Date().valueOf();
        var max = null;
        layui.form.render("select");


    });


</script>

</body>
</html>
