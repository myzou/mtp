<%@page import="sun.awt.OSInfo.OSType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--     <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  
  <head>
    <meta charset="UTF-8">
    <title>send_email添加页面</title>
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
              <label  class="layui-form-label"  >
                  <span class="x-red">*</span>邮箱别名：
              </label>
              <div class="layui-input-inline">
		              <input   type="text" id="email_name" name="email_name"  lay-verify="required"  class="layui-input" >
	          </div>
          </div>
          
         <div class="layui-form-item">
               <label  class="layui-form-label" >
                   邮箱密码：
              </label>
              <div  class="layui-input-inline"    >
                   <input   type="text" id="email_password" name="email_password"  lay-verify=""  class="layui-input" >
              </div>
          </div>    
          
            <div class="layui-form-item">
               <label  class="layui-form-label" >
                  <span class="x-red">*</span>邮箱帐号：
              </label>
              <div  class="layui-input-inline"    >
                   <input   type="text" id="email_code" name="email_code"  lay-verify="required"  class="layui-input" >
              </div>
          </div>    
          
            <div class="layui-form-item">
               <label  class="layui-form-label" >
                  <span class="x-red">*</span>邮箱主机：
              </label>
              <div  class="layui-input-inline"    >
                   <input   type="text" id="email_host" name="email_host"  lay-verify="required"  class="layui-input" >
              </div>
          </div>    
          
            <div class="layui-form-item">
               <label  class="layui-form-label" >
                  <span class="x-red">*</span>连接协议：
              </label>
              <div  class="layui-input-inline"    >
                   <input   type="text" id="protocol" name="protocol"  lay-verify="required"  class="layui-input" >
              </div>
          </div>    
              
				
		          <div class="layui-form-item">
		              <label for="L_repass" class="layui-form-label">
		              </label>
		              <button  class="layui-btn" type="button" lay-filter="add" lay-submit="">
		                  增加
		              </button>
		          </div>
		      </form>
		    </div>
    <script>
    var form ;
        layui.use(['form','layer'], function(){
            $ = layui.jquery;
            form = layui.form
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
        	  var json=data.field;
        	  console.log(JSON.stringify(json));
        	   $.ajax({
        		  url:"${pageContext.request.contextPath}/mail_addSendEmail.action",
        		  type:"post",
        		  data:"JsonStr="+JSON.stringify(json),
        		  dataType:"json",
        		  success:function(result){
        		        layer.msg("增加发送邮箱成功", {icon: 6,time:1000},function () {
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
        <!--use end -->
        
        
    </script>

  </body>

</html>