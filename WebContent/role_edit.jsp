<%@page import="sun.awt.OSInfo.OSType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--     <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  
  <head>
    <meta charset="UTF-8">
    <title>role添加role_add页面</title>
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
    
    <!--zTree_v所需的css 和js  -->
     <link rel="stylesheet" href="./lib/zTree_v3/css/demo.css" type="text/css">
	<link rel="stylesheet" href="./lib/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
<!-- 	<script type="text/javascript" src="./lib/zTree_v3/js/jquery-1.4.4.min.js"></script> -->
	<script type="text/javascript" src="./lib/zTree_v3/js/jquery.ztree.core.js"></script>
	<script type="text/javascript" src="./lib/zTree_v3/js/jquery.ztree.excheck.js"></script>
	<script type="text/javascript" src="./lib/zTree_v3/js/jquery.ztree.exedit.js"></script>
    
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
                  <span class="x-red">*</span>角色名称
              </label>
              <div class="layui-input-inline">
                  <input type="text" id="role_name" name="role_name"role_name"" required="" lay-verify="required"
                  autocomplete="off" class="layui-input">
              </div>
          </div>
          
			  <div class="layui-form-item" pane="">
			    <label class="layui-form-label">是否可用</label>
			     <div class="layui-input-block">
				      <input type="radio"  name="status" value="0" title="可用" checked="">
				      <input type="radio"    name="status" value="1" title="不可用">
				   </div>
			  </div>
			  
			    <div class="layui-form-item">
	              <label for="L_repass" class="layui-form-label">
	              </label>
	              <ul id="treeDemo" class="ztree"></ul>
	              </div>
	              
	               <div class="layui-form-item">
	              <label for="L_repass" class="layui-form-label">
	              </label>
	              <button  class="layui-btn"  type="button"      lay-filter="add" lay-submit="">
	                   修改
	              </button>
	          </div>
	          <input type="hidden"  id="modifyMenuId" name="modifyMenuId"  />
		      </form>
		    </div>
		    
		   
    <script>
    var role_id= '${param.role_id}';
			
	$(document).ready(function(){
		initTree();		
	});
	
        layui.use(['form','layer'], function(){
            $ = layui.jquery;
          var form = layui.form
          ,layer = layui.layer;
           getRole();
//          loadRoles();
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
        	  var role_id= '${param.role_id}';
        	  var modifyMenuId= $("#modifyMenuId").val();
        	  json.role_id=role_id;
        	 json.modifyMenuId=modifyMenuId;
//         	 alert(modifyMenuId);
// 			alert(JSON.stringify(json));
// 			return false;
        	   $.ajax({
        		  url:"${pageContext.request.contextPath}/role_updateSysRole.action",
        		  type:"post",
        		  data:"jsonStr="+JSON.stringify(json),
        		  dataType:"json",
        		  success:function(result){
      	            layer.msg('修改角色成功', {icon: 6,time:1000},function () {
      	            var index = parent.layer.getFrameIndex(window.name);
      	            parent.layer.close(index);
      	            window.parent.location.reload();
      	            });
        		  } 
        	  	});  
        		
	         
          });
        });
        
        
        function getRole(){
        	 layui.use(['form','layer'], function(){ 
        		   $.ajax({
      	     		  url:"${pageContext.request.contextPath}/role_getRoleByRoleId.action",
      	     		  type:"post",
      	     		  data:"role_id="+role_id,
      	     		  dataType:"json",
      	     		  success:function(result){
      	     			 	 $("#role_name").val(result.role_name);
                    		$("input[name=status][value=0]").attr("checked",result.status== 0 ? true : false);
                    		$("input[name=status][value=1]").attr("checked",result.status== 1 ? true : false);
      	     	         	 layui.form.render('radio' );   
      	     		  }, error:function(res){
//       	     			  alert("出错了："+res);
      	     		  }
      	     	  });    
        		 
        	 });
        }
        
        //获取节点复选框的值
        function onCheck(e,treeId,treeNode){
            var treeObj=$.fn.zTree.getZTreeObj("treeDemo"),
            nodes=treeObj.getCheckedNodes(true),
            v="";
            id="";
            modifyMenuId="";//选中节点的menu_id
            for(var i=0;i<nodes.length;i++){
            v+=nodes[i].name + ",";
            id+=nodes[i].id + ",";
            modifyMenuId+=nodes[i].menu_id + ",";
            // alert(nodes[i].id); //获取选中节点的值
           }
            modifyMenuId=(modifyMenuId.substring(modifyMenuId.length-1)==','?modifyMenuId.substring(0,modifyMenuId.length-1):modifyMenuId);
//             alert(modifyMenuId);                //获取选中节点的值
            $("#modifyMenuId").val(modifyMenuId);
            
         }
        
	
 
        
        function initTree(){
    		var setting = {
    	            check: {
    	                enable: true 
    	            },
    	            data: {
    	                simpleData: {
    	                    enable: true,
    	                    idKey : "id", // id编号命名   
    	                    pIdKey : "pId", // 父id编号命名  
    	                    chkboxType: { "Y": "ps", "N": "" },
    	                    rootPId : 0 
    	                }
    	            },
    	            callback: {
    	                onCheck: onCheck,
    	                //onAsyncSuccess: onAsyncSuccess
    	            }
    	        };	
    		
    		var node  ="[";
      	   $.ajax({
    	     		  url:"${pageContext.request.contextPath}/role_loadRoleMenuByRoleId.action",
    	     		  type:"post",
    	     		  data:"",
    	     		  dataType:"json",
    	     		  success:function(result){
    	     			  
    	     		  	   $.ajax({
    	     	     		  url:"${pageContext.request.contextPath}/role_loadRoleMenuByRoleId.action",
    	     	     		  type:"post",
    	     	     		  data:"role_id="+role_id,
    	     	     		  dataType:"json",
    	     	     		  success:function(result1){
    	     	     			  
    	     	     			for(var i in result){
        	     			  		node+="{ \"id\":\""+result[i].m_id+"\", \"pId\":\""+result[i].parent_m_id+"\", \"name\":\""+result[i].menu_name+"\""+",\"menu_id\":\""+result[i].menu_id+"\""+((result[i].parent_m_id==0)?",\"open\":true":"");
        	     			  		for(var a in result1){
        	     			  			if(result1[a].m_id==result[i].m_id){
        	     			  				node+=",\"checked\":true";
        	     			  			}
        	     			  			}
        	     			  		node+="},";
        	     			  	}
    	     	     			 node=(node.substring(node.length-1)==','?node.substring(0,node.length-1):node);
    	     	     			node+="]";
    	     	     			var zNodes=eval("("+node+")"); 
    	     	     			  $.fn.zTree.init($("#treeDemo"), setting, zNodes);
    	     	     			  return false;
    	     	     		  }, error:function(res){
//     	     	     			 	 alert("出错了："+JSON.stringify(res));
    	     	     			 	if(res.status==200&&res.responseText==""){
    	     	     					for(var i in result){
    	        	     			  		node+="{ \"id\":\""+result[i].m_id+"\", \"pId\":\""+result[i].parent_m_id+"\", \"name\":\""+result[i].menu_name+"\""+",\"menu_id\":\""+result[i].menu_id+"\""+((result[i].parent_m_id==0)?",\"open\":true":"");
    	        	     			  		node+="},";
    	        	     			  	}
    	    	     	     			 node=(node.substring(node.length-1)==','?node.substring(0,node.length-1):node);
    	    	     	     			node+="]";
    	    	     	     			var zNodes=eval("("+node+")"); 
    	    	     	     			  $.fn.zTree.init($("#treeDemo"), setting, zNodes);
    	    	     	     			  return false;
    	     	     			 	}
    	     	     			 	
    	     	     		  }
    	     	     	  });    
    	     	
    	     		  }, error:function(res){
    	     			  alert("出错了："+JSON.stringify(res));
    	     			
    	     		  }
    	     		 
   	     		
    	     	  });    
        
        }
        
    </script>

  </body>

</html>