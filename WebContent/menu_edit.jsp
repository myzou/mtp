<%@page import="sun.awt.OSInfo.OSType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--     <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  
  <head>
    <meta charset="UTF-8">
    <title>menu编辑menu_edit页面</title>
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
                  <span class="x-red">*</span>菜单名称
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
			  </div>
				
		          <div class="layui-form-item">
		              <label   class="layui-form-label">
		              </label>
	      				<button id ="add" class="layui-btn" lay-submit=""   type="button" lay-filter="add" >立即提交</button>
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

          //监听提交
          form.on('submit(add)', function(data){
        		var data=data.field;
        		var menu_id= '${param.menu_id}';
        		data.menu_id=menu_id;
        	 	data.bolong_menu_id=$("#menu_name1").val();
        	 	data.child_order_num=$("#child_order_num").val();
        	 	data.parent_order_num=$("#parent_order_num").val();
//         	  	alert(JSON.stringify(data));
//         	  	return false;
 		     $.ajax({
	        		  url:"${pageContext.request.contextPath}/menu_updateSysMenu.action",
	        		  type:"post",
	        		  data:"jsonStr="+JSON.stringify(data),
	        		  dataType:"json",
	        		  success:function(result){
// 	        			  alert("修改成功了");
	        		      layer.msg('修改成功', {icon: 6,time:500},function () {
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
        
        function loadMenu (){
								layui.use(['form','layer'], function(){
								$ = layui.jquery;
								var form = layui.form;
								var layer = layui.layer;
								var one= '${param.one}';
								var menu_id= '${param.menu_id}';
								var belong_menu_id= '${param.belong_menu_id}';
								var menu_name="";
					$.ajax({
										url:"${pageContext.request.contextPath}/menu_getSysMenu.action",
										type:"post",
										data:"menu_id="+menu_id,
										dataType:"json",
									success:function(result){
										menu=result;
										$("#menu_path").val(result.menu_path);
										$("#order_num").val(result.order_num);
										$("#child_order_num").val(result.child_order_num);
										$("#parent_order_num").val(result.parent_order_num);
										$("#parent_m_id").val(result.parent_m_id);
										$("input[name=status][value=0]").attr("checked",result.status== 0 ? true : false);
			                    		$("input[name=status][value=1]").attr("checked",result.status== 1 ? true : false);
										$("#m_id").val(result.m_id);
										$("#menu_name2").val(result.menu_name);
										if(result.parent_m_id!='0'){
											$("#parent"). remove();
										}else if(result.parent_m_id=='0'){
											menu_name=result.menu_name;
											$("#menu_1 div").html("<input   id='menu_name1'   name='menu_name1' lay-verify='' autocomplete='off'   placeholder='请输入修改后菜单名称' class='layui-input'  lay-verify='required'  value='"+menu_name+"' />          ");
					    					$("#menu_2").remove();
					    					$("#child").remove( );
										}
// 										menu_name=result.menu_name;
										menu_id=result.menu_id;
										layui.form.render();   
	     	     		  }, error:function(res){
	     	     			  alert("出错了,请联系管理员："+res);
	     	     		  }
	     	     	  	});    
								if(one!=1){//修改一级菜单
				                 
			    			   	   	$.ajax({
										url:"${pageContext.request.contextPath}/menu_loadMenuNames.action",
										type:"post",
										data:"",
										dataType:"json",
										success:function(result){
										var html="";
										html+="<select id='menu_name1'  name='menu_name1'  lay-search=''        lay-verify='required'    >";
										for(var i in result){
										html+="<option value='"+result[i].menu_id+"'>"+result[i].menu_name+"</option>";
										}
										html+=" </select>";
										$("#menu_1 div").html(html);
										layui.form.render("select");    
										$("#menu_name1").find("option[ value='"+ belong_menu_id+"']").attr("selected", true);
										layui.form.render("select");    
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