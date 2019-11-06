<%@page import="sun.awt.OSInfo.OSType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--     <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  
  <head>
    <meta charset="UTF-8">
    <title>MtpRecordDetailed骨干类型add页面</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!--     <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" /> -->
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" href="./css/font.css">
    <link rel="stylesheet" href="./css/xadmin.css">
    <script type="text/javascript" src="./js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="./lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="./js/xadmin.js"></script>
	<style type="text/css">
	  .font12{
    	font-size: 12px ;
    }
	</style>
    <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
    <!--[if lt IE 9]>
      <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
      <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  
  <body>
    <div class="x-body layui-anim layui-anim-up">
        <form class="layui-form">
 
			<div class="layui-form-item">
	              <label class="layui-form-label" style="width: 15%;float: left;" >
	                  <span class="x-red">*</span>case_id
	              </label>
	              <div class="layui-input-inline">
	                  <input type="text" id="case_id" name="case_id"  style="width: 600px;"   required="" lay-verify="required"  
	                  autocomplete="off" class="layui-input">
	              </div>
	             <label class="layui-form-label1">
	                		 <span class="x-red" id="nameTip"> </span>
	              </label>
	          </div>
			
			<div class="layui-form-item">
	              <label  class="layui-form-label" style="width: 15%;float: left;" >
	                  <span class="x-red"  >*</span>provider_circuit_nums
	              </label>
	              <div class="layui-input-inline">
	              <textarea id="provider_circuit_nums" name="provider_circuit_nums"  style="width: 600px;"    class="layui-textarea"	placeholder="线路 多条线路换行,不要此行中文:&#10;E1101&#10;E3"  autocomplete="off"	 lay-verify=""
	                  autocomplete="off"   ></textarea>
	              </div>
	          </div>
              
	          <div class="layui-form-item">
	              <label  class="layui-form-label" style="width: 15%;float: left;" >
	                  <span class="x-red"  >*</span>trunk_names
	              </label>
	              <div class="layui-input-inline">
	              <textarea id="trunk_names" name="trunk_names" style="width: 600px;"  placeholder="格式 ：PE.port 多条换行,不要此行中文&#10;CNBEJJIC1002C.xe-0/2/0.50&#10;CNBEJJIC1002C.xe-0/2/0.50"   class="layui-textarea"	 autocomplete="off"	 lay-verify=""
	                  autocomplete="off"   ></textarea>
	              </div>
	           </div>	
	           
	           	<div class="layui-form-item">
	              <label  class="layui-form-label" style="width: 15%;float: left;" >
	                  <span class="x-red"  >*</span>发送包数量：
	              </label>
	              <div class="layui-input-inline">
	              <textarea id="send_size" name="send_size"  style="width: 600px;"    class="layui-textarea"	 autocomplete="off"	 lay-verify=""
	                  autocomplete="off"   >200</textarea>
	              </div>
	          </div>
				
		          <div class="layui-form-item">
		              <label  class="layui-form-label"  style="width: 15%;float: left;" >
		              </label>
		              <button  class="layui-btn" lay-submit=""   type="button" lay-filter="add" >
		                  增加
		              </button>
		              
		          </div>
		      </form>
		    </div>
    <script>
        layui.use(['form','layer'], function(){
            $ = layui.jquery;
          var form = layui.form
          ,layer = layui.layer;
    	 layui.form.render ();   
          
          //自定义验证规则
          form.verify({
            nikename: function(value){
              if(value.length < 5){
                return '昵称至少得5个字符啊';
              }
            }
            ,pass: [/(.+){6,12}$/, '密码必须6到12位']
            ,repass: function(value){
                if($('#L_pass').val()!=$('#L_repass').val()){
                    return '两次密码不一致';
                }
            }
          });

          //监听提交
          form.on('submit(add)', function(data){
        	  var data=data.field;
    	 	if(data.trunk_names==""&& data.provider_circuit_nums==""){
        	  	alert("provider_circuit_nums 和 trunk_names 不能都为空");
        	  	return false;
    	 	}

			 $.ajax({
       		  url:"${pageContext.request.contextPath}/mtprd_addMtpRecordDetailed.action",
       		  type:"post",
       		  data:"jsonStr="+JSON.stringify(data),
       		  dataType:"text",
       		  success:function(result){
//        			  alert(JSON.stringify(result));
//        			  return false;
      /*  			  var alert1=layer.alert(result);
					 layer.style(alert1, {
						width:'1000px',
						high:'2000px'
				     }); */
				  
       		  } ,error:function (res){
//        			  alert(JSON.stringify (res));
       			  return false;
       		  }
  
       	  	});    
	     	   layer.msg("正在查询数据,请稍后刷新页面", {icon: 6,time:1000},function () {
	 	           // 获得frame索引
	 	           var index = parent.layer.getFrameIndex(window.name);
	 	           //关闭当前frame
	 	           parent.layer.close(index);
	 	           window.parent.location.reload();
	 	           });
          });
          
          
        });
        
        
        
        
    </script>

  </body>

</html>