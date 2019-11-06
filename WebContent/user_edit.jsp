<%@page import="sun.awt.OSInfo.OSType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--     <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  
  <head>
    <meta charset="UTF-8">
    <title>email-edit页面</title>
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
              <label class="layui-form-label">
                  <span class="x-red">*</span>用户名
              </label>
              <div class="layui-input-inline">
                  <input type="text" id="user_name" name="user_name"  onblur="checkName()" required="" lay-verify="required"
                  autocomplete="off" class="layui-input">
              </div>
               <label class="layui-form-label1">
                		 <span class="x-red" id="nameTip"> </span>
              </label>
          </div>
          <div class="layui-form-item">
              <label  class="layui-form-label">
                  <span class="x-red">*</span>密码
              </label>
              <div class="layui-input-inline">
                  <input type="password" id="password" name="password" required=""
                  autocomplete="off" class="layui-input">
              </div>
          </div>
          
              <div class="layui-form-item" pane="">
			    <label class="layui-form-label">角色</label>
			    <div class="layui-input-block" id="roles">
	          <!--       <input   type="checkbox" name="check" lay-skin="primary" value="1" title="admin">
				   <input type="checkbox" name="check" lay-skin="primary" value="2" title="lpl">
				   <input type="checkbox" name="check" lay-skin="primary" value="3" title="noc">
	               <span style="color: green;margin-top: -10px ">用户对应的角色</span> -->
				 </div>
<!-- 			 	<input class="layui-btn-warm layui-btn layui-btn-xs" type="button"  value="测试"  onclick="test()" /> -->
			
			  </div>
			  
		  <input   type="hidden"  id="user_id"  name="user_id"  autocomplete="off" value=""    />
				
		          <div class="layui-form-item">
		              <label for="L_repass" class="layui-form-label">
		              </label>
		              <button  class="layui-btn"  type="button"      lay-filter="add" lay-submit="">
		                  修改
		              </button>
		          </div>
		      </form>
		    </div>
    <script>
        layui.use(['form','layer'], function(){
            $ = layui.jquery;
          var form = layui.form
          ,layer = layui.layer;
         loadRoles();
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
        	  json.roles=getRolesChecked();
 	 		 $.ajax({
        		  url:"${pageContext.request.contextPath}/user_updateSysUser.action",
        		  type:"post",
        		  data:"jsonStr="+JSON.stringify(json),
        		  dataType:"json",
        		  success:function(result){
//         			  alert("修改成功了");
        				//发异步，把数据提交给php
      	            layer.msg("用户修改成功", {icon: 6},function () {
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
        
        function loadRoles (){
        	 layui.use(['form','layer'], function(){
                $ = layui.jquery;
                var form = layui.form
                ,layer = layui.layer;
           	   $.ajax({
           		  	url:"${pageContext.request.contextPath}/role_loadRoles.action",
           		  	type:"post",
           		  	data:'',
           		  	dataType:"json",
           		  	success:function(result){
           			var html="";
           			for( var i in result){
					html+="<input type='checkbox'  name='check'   lay-skin='primary' value='"+  result[i]. role_id+"' title='"+  result[i]. role_name+"'>";
           			}
           			html+="<span style='color: green;margin-top: -10px '>用户对应的角色</span>"; 
           			$("#roles").html(html);
           			 layui.form.render('checkbox');   
           			 
           	     	var user_id= '${param.user_id}';
                 	if(user_id!=null&&user_id!=""){
                 		   $.ajax({//加载复选框
                 	     		  url:"${pageContext.request.contextPath}/user_loadUserRoleByUserId.action",
                 	     		  type:"post",
                 	     		  data:"user_id="+user_id,
                 	     		  dataType:"json",
                 	     		  success:function(result1){
                 	     			 	var groupCheckbox=	$("input[name='check']");
                 	     	         	if(result!=""){
                 	     	         		for(var i in groupCheckbox){
                    	     	         		 for( var s in result1){
                    		     	         		if(	result1[s].role_id==groupCheckbox[i].value){
                    		    						groupCheckbox[i].checked=true;
                    		     	         		}
                    	         			 		}
                    	     	         	}
                 	     	         	 layui.form.render('checkbox' );   
                 	     	         	}
                 	     		  }, error:function(res){
                 	     			  alert("出错了："+JSON.stringify(res));
                 	     		  }
                 	     	  });    
                 		  $.ajax({//加载用户信息
             	     		  url:"${pageContext.request.contextPath}/user_getUserByUserId.action",
             	     		  type:"post",
             	     		  data:"user_id="+user_id,
             	     		  dataType:"json",
             	     		  success:function(result2){
             	     			$("#user_id").val(result2[0].user_id);
             	     			$("#user_name").val(result2[0].user_name);
//              	     			$("#password").val(result2[0].password);
             	     		  }, error:function(res){
             	     			  alert("出错了："+JSON.stringify(res));
             	     		  }
             	     	  });    
                 		   
                 		  
                 	}
           			 
           		  },
           		  error:function(res){
           			  alert("出错了："+res);
           		  }
           	  });  
        	});
        }
        
        
        function getRolesChecked(){
        	var str="";
        	var groupCheckbox=	$("input[name='check']");
         	for(var i in groupCheckbox){
         		if(groupCheckbox[i].checked){
         			if(str.length>0){
         				str+=","+groupCheckbox[i].value;
         			}else{
         				str+=groupCheckbox[i].value;
         			}
         		}
         	}
        	 return str;
        }
        
        function checkName() {
       	 layui.use(['form','layer'], function(){
       		    var user_name = $("#user_name").val();
       		    
                   $.ajax({
                       url : "${pageContext.request.contextPath}/user_getUserByUserName.action",  
                       data : {
                           "user_name" : user_name
                       }, type : "post",
                       success : function(result) {
                       	if(result[0]==true){ 
                               //根据后台返回前台的msg给提示信息加HTML
                               	 $("#nameTip").text( user_name+" 用户未修改或者已经存在 ");
//             	     	         	 layui.form.render('span' );   
                       	}else{
                        	 $("#nameTip").text("" );
                       	}
                       }
                   });
       		 
       	 });
       }
        
    </script>

  </body>

</html>