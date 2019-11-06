<%@page import="sun.awt.OSInfo.OSType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--     <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  
  <head>
    <meta charset="UTF-8">
    <title>TrunkInfoMtp_add页面</title>
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
    .left{
    float:left;
    }
    .w60{
    width: 60%;
     }
       .w20{
    width: 20%;
     }
     .w15{
    width: 18%;
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
              <label class="layui-form-label w15"   >
                  <span class="x-red">*</span>trunk_id
              </label>
              <div class="layui-input-inline">
                  <input type="text" id="trunkId" name="trunkId"    required="" lay-verify="required"
                  autocomplete="off" class="layui-input">
              </div>
              
            <label class="layui-form-label w15"  >
                  <span class="x-red">*</span>link_type
              </label>
              <div class="layui-input-inline">
                  <input type="text" id="linkType" name="linkType"    required="" lay-verify="required"
                  autocomplete="off" class="layui-input">
              </div>
              
             <label class="layui-form-label1">
                		 <span class="x-red" id="nameTip"> </span>
              </label>
              
          </div>
          
          <div class="layui-form-item">
              <label class="layui-form-label w15"   >
                  <span class="x-red">*</span>a_end_full_name
              </label>
              <div class="layui-input-inline" >
                  <input type="text" id="aEndFullName" name="aEndFullName"    required="" lay-verify="required"
                  autocomplete="off" class="layui-input">
              </div>
              
			<label  class="layui-form-label w15"  >
                  <span class="x-red">*</span>a_end_interface
              </label>
              <div class="layui-input-inline">
                  <input type="text" id="aEndInterface" name="aEndInterface" required="" lay-verify="required"
                  autocomplete="off" class="layui-input">
              </div>
          </div>  
          <div class="layui-form-item">
                  <label class="layui-form-label w15" >
                  <span class="x-red">*</span>a_end_interface_ip
              </label>
              <div class="layui-input-inline left">
                  <input type="text" id="aEndInterfaceIp" name="aEndInterfaceIp"    required="" lay-verify="required"
                  autocomplete="off" class="layui-input">
              </div>
          </div>  
                  
          
          <div class="layui-form-item">
              <label class="layui-form-label w15"   >
                  <span class="x-red">*</span>z_end_full_name
              </label>
              <div class="layui-input-inline">
                  <input type="text" id="zEndFullName" name="zEndFullName"    required="" lay-verify="required"
                  autocomplete="off" class="layui-input">
              </div>
              
			<label  class="layui-form-label w15" >
                  <span class="x-red">*</span>z_end_interface
              </label>
              <div class="layui-input-inline"> 
                  <input type="text" id="zEndInterface" name="zEndInterface" required="" lay-verify="required"
                  autocomplete="off" class="layui-input">
              </div>
      
          </div>   
          
	 <div class="layui-form-item">
          <label class="layui-form-label w15">
                  <span class="x-red">*</span>z_end_interface_ip
              </label>
              <div class="layui-input-inline left">
                  <input type="text" id="zEndInterfaceIp" name="zEndInterfaceIp"    required="" lay-verify="required"
                  autocomplete="off" class="layui-input">
              </div>
          </div>   
          
         <div class="layui-form-item">
              <label class="layui-form-label w15"   >
                  <span class="x-red">*</span>trunk_name
              </label>
              <div class="layui-input-inline w60" style="width: 70%;">
                  <input type="text" id="trunkName" name="trunkName"    required="" lay-verify="required"
                  autocomplete="off" class="layui-input">
              </div>
          </div>   
          
        <div class="layui-form-item">
              <label class="layui-form-label w15"   >
                  <span class="x-red">*</span>provider_circuit_num
              </label>
              <div class="layui-input-inline "   style="width: 70%;" >
                  <input type="text" id="providerCircuitNum" name="providerCircuitNum"    required="" lay-verify="required"
                  autocomplete="off" class="layui-input">
              </div>
          </div>   
          
          <div class="layui-form-item">
              <label class="layui-form-label w15"   >
                  internal_circuit_num
              </label>
              <div class="layui-input-inline" style="width: 70%;"  >
                  <input type="text" id="internalCircuitNum" name="internalCircuitNum"    required="" 
                  autocomplete="off" class="layui-input">
              </div>
          </div>   
          
         <div class="layui-form-item">
              <label class="layui-form-label w15"  >
                 provider
              </label>
              <div class="layui-input-inline"  style="width: 70%;">
                  <input type="text" id="provider" name="provider"    required=""  
                  autocomplete="off" class="layui-input">
              </div>
          </div>   
          
		  <div class="layui-form-item">
              <label class="layui-form-label w15" >
		              </label>
		              <button  class="layui-btn" type="button" lay-filter="add" lay-submit="">提交</button>
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
        	  var jsonStr=data.field;
  			 alert(JSON.stringify(jsonStr));
//   			 return false;
        	   $.ajax({
        		  url:"${pageContext.request.contextPath}/timtp_addTrunkInfoMtp.action",
        		  type:"post",
        		  data:"jsonStr="+JSON.stringify(jsonStr),
        		  dataType:"json",
        		  success:function(result){
        		        layer.msg("增加线路成功", {icon: 6,time:1000},function () {
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
        
        
        
    </script>

  </body>

</html>