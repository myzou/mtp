<%@page import="sun.awt.OSInfo.OSType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--     <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  
  <head>
    <meta charset="UTF-8">
    <title>menu添加menu_add页面</title>
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

          
          
          
          <div class="layui-form-item"   id="menu_1">
              <label class="layui-form-label">
                  <span class="x-red">*</span>一级菜单
              </label>
              <div class="layui-input-inline">
              </div>
          </div>
          

          
          <div class="layui-form-item" id="menu_2">
              <label  class="layui-form-label">
                  <span class="x-red">*</span>二级菜单
              </label>
              <div class="layui-input-inline">
                  <input type="text" id="menu_name2" name="menu_name2" required="" lay-verify="required"
                  autocomplete="off" class="layui-input">
              </div>
          </div>
          
          <div class="layui-form-item">
              <label  class="layui-form-label">
                  <span class="x-red">*</span>路径
              </label>
              <div class="layui-input-inline">
                  <input type="text" id="menu_path" name="menu_path" required="" lay-verify="required" placeholder='菜单路径'
                  autocomplete="off" class="layui-input">
              </div>
          </div>
          
           <div class="layui-form-item"  id="parent">
              <label  class="layui-form-label font12">
                  <span class="x-red ">*</span>一级菜单排序
              </label>
              <div class="layui-input-inline">
                  <input type="text" id="parent_order_num" name="parent_order_num" required="" lay-verify="required"  placeholder='对应菜单排序'
                  autocomplete="off" class="layui-input">
              </div>
          </div>
          
             <div class="layui-form-item" id="child">
              <label  class="layui-form-label font12">
                  <span class="x-red">*</span>二级菜单排序
              </label>
              <div class="layui-input-inline">
                  <input type="text" id="child_order_num" name="child_order_num" required="" lay-verify="required"  placeholder='对应菜单排序'
                  autocomplete="off" class="layui-input">
              </div>
            </div>
          
              <div class="layui-form-item" pane="">
			    <label class="layui-form-label">是否可用</label>
			     <div class="layui-input-block">
				      <input type="radio" name="status" value="0" title="可用" checked="">
				      <input type="radio" name="status" value="1" title="不可用">
<!-- 				      <input type="radio" name="sex" value="禁" title="禁用" disabled=""> -->
				   </div>
<!-- 			 	<input class="layui-btn-warm layui-btn layui-btn-xs" type="button"  value="测试"  onclick="test()" /> -->
			
			  </div>
			  
				
				
				
		          <div class="layui-form-item">
		              <label  class="layui-form-label">
		              </label>
		              <button id ="add" class="layui-btn" lay-submit=""   type="button" lay-filter="add" >
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
         loadMenu();
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
    	 	data.child_order_num=$("#child_order_num").val();
    	 	data.parent_order_num=$("#parent_order_num").val();
//     	  	alert(JSON.stringify(data));
//     	  	return false;

			 $.ajax({
       		  url:"${pageContext.request.contextPath}/menu_loadMenu.action",
       		  type:"post",
       		  data:"",
       		  dataType:"json",
       		  success:function(result){
       			  var parents="";
       			for (var i  in result){
       					if(parents.indexOf(result[i].parent_order_num)==-1){
           					parents+=result[i].parent_order_num+",";
       					}
       			}
       			if(parents.indexOf(data.parent_order_num)!=-1){//排序没有存在
       				parents=(parents.substring(parents.length-1)=="," )?parents.substring(0,parents.length-1):parents;
       				layer.alert('不能创建已经存在的排序，已经存在的排序有。<br>'+parents);
       			  return false;
       			}else{
       				$.ajax({
              		  url:"${pageContext.request.contextPath}/menu_addSysMenu.action",
              		  type:"post",
              		  data:"jsonStr="+JSON.stringify(data),
              		  dataType:"json",
              		  success:function(result){
              			//发异步，把数据提交给php
              	           layer.msg("增加成功", {icon: 6,time:300},function () {
              	           // 获得frame索引
              	           var index = parent.layer.getFrameIndex(window.name);
              	           //关闭当前frame
              	           parent.layer.close(index);
              	           window.parent.location.reload();
              	           });
              	         
              		  } 
              	  	});    
       			}
       			
       		  } ,error:function (res){
       			  alert(JSON.stringify (res));
       			  return false;
       		  }
       	  	});    
			
          });
          
          
        });
        
        function loadMenu (){
        	 layui.use(['form','layer'], function(){
                $ = layui.jquery;
                var form = layui.form
                ,layer = layui.layer;
                var one= '${param.one}';
                if(one!=null &&one==1){
                	$("#menu_2").remove();
                	$("#child").remove();
                	$("#menu_1 div").html("<input   id='menu_name1'   name='menu_name1' lay-verify='' autocomplete='off'   placeholder='请输入菜单名称' class='layui-input'  lay-verify='required'  />          ");
                }else if(one!=null &&one==2){
                	$("#parent").remove();
                	$("#menu_name1").remove();
               		   $.ajax({
               	     		  url:"${pageContext.request.contextPath}/menu_loadMenuNames.action",
               	     		  type:"post",
               	     		  data:"",
               	     		  dataType:"json",
               	     		  success:function(result){
               	     			var html="";
                            	html+="<select   name='menu_name1'  lay-search=''        lay-filter='dept'  lay-verify='required'    >";
               	     			for(var i in result){
               	     			html+="<option value='"+result[i].menu_id+"'>"+result[i].menu_name+"</option>";
               	     			}
                            	html+=" </select>";
                            	$("#menu_1 div").html(html);
                            	layui.form.render();   
               	     		  }, error:function(res){
               	     			  alert("出错了,请联系管理员："+res);
               	     		  }
               	     	  });    
                	
                
                
                }
      			 
      			 		
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
        
    </script>

  </body>

</html>