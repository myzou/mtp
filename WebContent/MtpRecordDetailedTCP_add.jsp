<%@page import="sun.awt.OSInfo.OSType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--     <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  
  <head>
    <meta charset="UTF-8">
    <title>MtpRecordDetailedTCP remedy推送页面</title>
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
	              <label  class="layui-form-label" style="width: 15%;float: left;" >
	                  <span class="x-red"  >*</span>jsonStr
	              </label>
	              <div class="layui-input-inline">
	              <textarea id="jsonStr" name="jsonStr"  style="width: 600px;height: 300px;"    class="layui-textarea"	placeholder="JosnStr"  autocomplete="off"	 lay-verify="required"
	                  autocomplete="off"   ></textarea>
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
        	  console.log($("#jsonStr").val());
			 $.ajax({
       		  url:"${pageContext.request.contextPath}/mtpr_addMtpRecordDetailed.action",
       		  type:"post",
       		  data:"jsonStr="+$("#jsonStr").val(),
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