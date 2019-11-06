<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--     <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  
  <head>
    <meta charset="UTF-8">
    <title>口令修改页面</title>
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
   <div class="x-nav">
      <span class="layui-breadcrumb">
        <a href="">mail管理</a>
        <a> <cite>管理</cite></a>
      </span>
      <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right" href="javascript:location.replace(location.href);" title="刷新">
        <i class="layui-icon" style="line-height:30px">ဂ</i></a>
    </div>
    <div class="x-body">
       <form class="layui-form">
     <!--  认证口令 start  -->
       <div class="layui-form layui-col-md12 x-so">
          <label for="popName" class="layui-form-label">
                  <span class="x-red">*</span>认证口令：
             </label>
             <div class="layui-input-inline" >
				 	<input type="text" id="passwordKeys"	 name="passwordKeys"  placeholder="请输入订单号"  autocomplete="off"    class="layui-input" >
  				     <button class="layui-btn"   onclick="updateKey()"	type="button"   title="修改认证口令"><i class="layui-icon">&#xe614;</i></button>
  			</div>
		</div>
		<!--  认证口令 end  -->
		<hr>
			<!--  机房增删改查 start  -->
	         <div class="layui-form-item">
              <label  class="layui-form-label" for="cityNames" >
                  <span class="x-red">*</span>地区：
              </label>
              <div class="layui-input-inline">
                  <select lay-search=""  id="city_name"  name="city_name"   lay-filter="cityNames"     lay-verify="required"    ><!--  -->
                  </select>
              </div>
              
                <label  class="layui-form-label" for="popNames">
                  <span class="x-red">*</span>机房名称：
              </label>
              <div  class="layui-input-inline" >
                  <select    lay-search=""  id="pop_name"  name="pop_name"    lay-filter="popNames"  lay-verify="required"    ><!--  -->
                  <option value="">直接选择或搜索选择</option>
                  </select>
              </div>
   			  <div  class="layui-input-inline"  style="width: 36%;">
                 <button class="layui-btn"   onclick="updateEmailByPopName()"	type="button"   title="修改机房信息"><i class="layui-icon">&#xe614;</i></button>
              	<button class="layui-btn" onclick="x_admin_show('添加邮箱','./emp_mess_add.jsp')"  type="button"   title="添加机房"><i class="layui-icon">&#xe654;</i></button>
                 <button class="layui-btn" onclick="deletePop()"  type="button"   title="删除机房"><i class="layui-icon">&#x1006;</i></button>
              </div>
          </div>
          
          <div class="layui-form-item"> 
           <label  class="layui-form-label font12" for="popNames">
                  <span class="x-red">*</span>改后地区名：
              </label>
              <div  class="layui-input-inline"   >
                   <input type="text" id="update_city_name" name="update_city_name" required="" lay-verify="required"
                  autocomplete="off" class="layui-input" >
              </div>
               <label  class="layui-form-label font12" for="popNames">
                  <span class="x-red">*</span>改后机房名：
              </label>
              <div  class="layui-input-inline"   style="width: 32.5%" >
                   <input type="text" id="update_pop_name" name="update_pop_name" required="" lay-verify="required"
                  autocomplete="off" class="layui-input" >
              </div>
          </div>
          
      <div class="layui-form-item">
              <label   class="layui-form-label font12" >
                    <span class="x-red">*</span> 供应商名称：
              </label>
              <div class="layui-input-block">
                  <input type="text" id="supplier_name" name="supplier_name" required="" lay-verify="required"
                  autocomplete="off" class="layui-input layui-unselect"  title="供应商名称"  >
              </div>
          </div> 
          
       <div class="layui-form-item">
              <label   class="layui-form-label font12">
                  <span class="x-red">*</span>收件人邮箱：
              </label>
              <div class="layui-input-block">
                  <input type="text" id="addressee_email" name="addressee_email" required="" lay-verify="required|addressee_email"
                  autocomplete="off" class="layui-input" >
              </div>
          </div>
          
       <div class="layui-form-item">
              <label   class="layui-form-label font12" >
                   抄送人邮箱：
              </label>
              <div class="layui-input-block">
                  <input type="text" id="cc_addressee_email" name="cc_addressee_email" required="" lay-verify="cc_addressee_email"
                  autocomplete="off" class="layui-input layui-unselect"  title="邮箱格式为xx@xx.com;xx@xx.com  多个邮箱用小写;割开"  >
              </div>
          </div> 
          
		<hr>
		<!--  机房增删改查 end  -->
		
		<!--  发送邮箱增删改查 start  -->
		      <div class="layui-form-item">
              <label  class="layui-form-label" for="cityNames" >
                  <span class="x-red">*</span>邮箱：
              </label>
              <div class="layui-input-inline">
                  <select lay-search=""  id="email_code"  name="email_code"   lay-filter="email_code"     lay-verify="required"    > 
                  <option value="">直接选择或搜索选择</option>
                  </select>
              </div>
              
                <label  class="layui-form-label" for="popNames">
                   密码：
              </label>
              <div  class="layui-input-inline" >
              <input type="text" id="email_password" name="email_password" required="" lay-verify=""
                  autocomplete="off" class="layui-input" >
              </div>
   			  <div  class="layui-input-inline"  style="width: 36%;">
                 <button class="layui-btn"   onclick="updateSendEmail()"	type="button"   title="修改发送邮箱信息"><i class="layui-icon">&#xe614;</i></button>
              	<button class="layui-btn" onclick="x_admin_show('添加邮箱','./send_email_add.jsp')"  type="button"   title="添加发送邮箱"><i class="layui-icon">&#xe654;</i></button>
                 <button class="layui-btn" onclick="deleteEmail()"  type="button"   title="删除发送邮箱"><i class="layui-icon">&#x1006;</i></button>
              </div>
          </div>
          
          <div class="layui-form-item"> 
            <label  class="layui-form-label font12" for="popNames">
                  <span class="x-red">*</span>改后邮箱：
              </label>
              <div  class="layui-input-inline"   >
                   <input type="text" id="update_email_code" name="update_email_code" required="" lay-verify="required"
                  autocomplete="off" class="layui-input" >
              </div>
           <label  class="layui-form-label font12" for="popNames">
                  <span class="x-red">*</span>主机：
              </label>
              <div  class="layui-input-inline"   >
                   <input type="text" id="email_host" name="email_host" required="" lay-verify="required"
                  autocomplete="off" class="layui-input" >
              </div>
               <label  class="layui-form-label font12" for="popNames">
                  <span class="x-red">*</span>连接池：
              </label>
              <div  class="layui-input-inline"     >
                   <input type="text" id="protocol" name="protocol" required="" lay-verify="required"
                  autocomplete="off" class="layui-input" >
              </div>
          </div>
		<!--  发送邮箱增删改查 end  -->
          
		
       </form>
       </div>
       
  
    <script>
    
    $(document).ready(function(){ 
        
    	}); 
    /** 
     * 设置select控件选中 
     * @param selectId select的id值 
     * @param checkValue 选中option的值 
     * @author 标哥 
    */  
    function set_select_checked(selectId, checkValue){  
        var select = document.getElementById(selectId);  
        for (var i = 0; i < select.options.length; i++){  
            if (select.options[i].value == checkValue){  
                select.options[i].selected = true;  
                break;  
            }  
        }  
    }

    
    <!-- use 开始-->
    var form,layedit,layer,laydate; 
        layui.use([ 'jquery','form', 'layedit','layer','laydate'], function(){
        	
        	var $=layui.jquery;
           form = layui.form;
           layer = layui.layer;
           laydate = layui.laydate;  
           loadPasswordKeys();
           loadPopNames();
           loadCityNames();
           loadSendEmail();
           form.render();
           layui.form.render("select");  
           
           
          //自定义验证规则
          form.verify({
        	  cabinetName:function(value){
//         		  some code
        	  }
          });
          

    	  form.on('select(cityNames)', function(data){//设置拦截器cityNames 改变或者点击事件
    		  var popName=data.value;
//     			  alert (popName);
    			  if(popName==""){
    				 $.ajax({//加载所有的城市
    		   	    	  url:"${pageContext.request.contextPath }/mail_loadEmpowerMessageByDao.action",
    		   			  type:"post",
    		   			  async:"true",
    		   			  data :"" ,
    		   			  dataType:"json",
    		   			  success:function(result){
    		   				$('#pop_name').empty();
    		   			  $('#pop_name').append(" <option value='' >直接选择或搜索选择</option> ");    // 给select 添加option子标签
    		   				for(var val1 in result){
    		   				 var append="  <option   value='"+  result[val1]['pop_name']  +"'>"+result[val1]['pop_name']+"</option>";
//    		   				 alert(append);
    		   				$('#pop_name').append(append);    // 给select 添加option子标签
    		   					}
    		   				layui.form.render("select");    
    		   			  }
    		   			  });	
    			  }else{
    				  $.ajax({
    			    	  url:"${pageContext.request.contextPath }/mail_loadPopNames.action",
    	    			  type:"post",
    	    			  async:"true",
    	    			  data :"popName="+popName ,
    	    			  dataType:"json",
    	    			  success:function(result){
    	    				  $('#pop_name').empty();
    	    				  $('#pop_name').append(" <option value='' >直接选择或搜索选择</option> ");    // 给select 添加option子标签
    	    				for(var i=0;i<result.length;i++){
    	    						$('#pop_name').append("<option value='"+result[i]+"'>"+result[i]+"</option>");    // 给select 添加option子标签
    	    				}
    	    				layui.form.render("select");    
    	    			  }
    	    			  });	
    			  }
        		  
            	}); 
     	 
     	  form.on('select(popNames)', function(data){//设置拦截器popNames 改变或者点击事件
    			  	var popName=data.value;
    				 $.ajax({
    		    	  url:"${pageContext.request.contextPath }/mail_getEmpowerMessage.action",
    				  type:"post",
    				  async:"true",
    				  data :"popName="+popName ,
    				  dataType:"json",
    				  success:function(result){
    				   set_select_checked('city_name', result[0]['city_name']);
    					var reg = new RegExp("###","g");//g,表示全部替换。
    					var addressee_email=result[0].addressee_email.replace(reg,';');
    					var cc_addressee_email=result[0].cc_addressee_email.replace(reg,';');
    					$("#addressee_email").val(addressee_email);
    					$("#city_name").val(result[0].city_name);
    					$("#update_city_name").val(result[0].city_name);
    					$("#update_pop_name").val(result[0].pop_name);
    					$("#supplier_name").val(result[0].supplier_name);
    					$("#cc_addressee_email").val(cc_addressee_email);
    					$("#addressee_email").attr("title","谨慎需改，收件人邮箱，多个邮箱小写;分割开");
    					$("#cc_addressee_email").attr("title","谨慎需改，抄送人邮箱，多个邮箱小写;割开");
    				layui.form.render("select");    
    			  }
    			  });	
    		  
        	}); 
          
     	  form.on('select(email_code)', function(data){//设置拦截器email_code 改变或者点击事件
			  	var email_uuid=data.value;
     	  		
		     	  if(email_uuid==null||email_uuid==""){
		     		  return ;
		     	  }
				 $.ajax({
		    	  url:"${pageContext.request.contextPath }/mail_getSendEmailByKey.action",
				  type:"post",
				  async:"true",
				  data :"email_uuid="+email_uuid ,
				  dataType:"json",
				  success:function(result){
					$("#update_email_code").val(result.email_code);
   					$("#email_password").val(result.email_password);
   					$("#email_host").val(result.email_host);
   					$("#protocol").val(result.protocol);
					layui.form.render("select");    
			  }
			  });	
		  
  	}); 
          
          
        });
       <!--use 结束  -->
        
      function  updateKey(){
    	  layer.confirm('是否修改认证口令?', {
              btn: ['修改','取消'] //按钮
          }, function(){
     		 var passwordKeys=$("#passwordKeys").val();
 		    $.ajax({
 		    	  url:"${pageContext.request.contextPath }/mail_updateKey.action",
   			  type:"post",
   			  async:"true",
   			  dataType:"json",
   			  data:"passwordKeys="+passwordKeys,
   			 success:function(result){
 				 layer.msg('密码修改为:'+passwordKeys ,{offset:'t',time:5000, icon:1,btn:['确定']});
 			  }
   			  });
          }, function(){
          });
       	}  
        
      function  updateSendEmail(){
			var email_uuid= 	$("#email_code").val( );
 			var email_code= 	$("#update_email_code").val( );
			var 	email_password=$("#email_password").val( );
			var	email_host=$("#email_host").val( );
			var  protocol= $("#protocol").val( );
    	  layer.confirm('是否修改邮箱内容?', {
              btn: ['修改','取消'] //按钮
          }, function(){
 		    $.ajax({
 		    	  url:"${pageContext.request.contextPath }/mail_updateSendEmail.action",
   			  type:"post",
   			  async:"true",
   			  dataType:"json",
   			 data:{
 	      		"email_code" : email_code ,
 	      		"email_password" : email_password ,
 	      		"email_host" : email_host ,
 	      		"protocol" : protocol ,
 	      		"email_uuid" : email_uuid  
 	      	  },success:function(result){
 				 layer.msg('更新邮箱成功');
 			  }
   			  });
          }, function(){
          });
       	}  
      
        function loadPasswordKeys(){
      	  var passwordKeys=$("#passwordKeys").val();
  		    $.ajax({
  		    	  url:"${pageContext.request.contextPath }/mail_loadPasswordKeys.action",
    			  type:"post",
    			  async:"true",
    			  dataType:"json",
    			  success:function(result){
    				var passwordKey =	result[0].param_value1;
    				$("#passwordKeys").attr("value",passwordKey);//设置值到对应的text
//     				alert(JSON.stringify(result));
    				var str="";//用来打印好看点
    				for(var a in result){
    					for (var value in result[a]){
    						str=str+value+":"+result[a][value]+"<br> ";
    					}
    				}
    			  }
    			  });
        } 
    

       function loadCityNames(){
              $.ajax({//加载所有地区
        	  url:"${pageContext.request.contextPath }/mail_loadCityNames.action",
        	  type:"post",
        	  async:"false",
        	  dataType:"json",
        	  success:function(result){
        		for(var i=0;i<result.length;i++){//设置北上广深在前面
        				var arr=['北京','上海','广州','深圳'];
        				if(arr.indexOf(result[i])>-1){
        					$('#city_name').prepend("<option value='"+result[i]+"'>"+result[i]+"</option>");    // 给select 添加option子标签
        				}else {
        					$('#city_name').append("<option value='"+result[i]+"'>"+result[i]+"</option>");    // 给select 添加option子标签
        				}
        				}	
        				$('#city_name').prepend("	<option value=''>直接选择或点我搜索全部</option>");
        				 $("#city_name").val("");
        	       		 layui.form.render("select");    
        		  }
        		  });
        }
	
        
        function loadPopNames(){
            $.ajax({
            	  url:"${pageContext.request.contextPath }/mail_loadPopNamesList.action",
        		  type:"post",
        		  async:"false",
        		  dataType:"json",
        		  success:function(result){
        			$('#pop_name').append(" <option value='' >直接选择或搜索选择</option> ");  
        			for(var i=0;i<result.length;i++){
        				$('#pop_name').append("<option value='"+result[i]+"'>"+result[i]+"</option>");    // 给select 添加option子标签
        			}
        			 layui.form.render("select");    
        		  }
        		  });
        }
 
        function loadSendEmail(){
    	    $.ajax({//加载所有发送的email
		    	  url:"${pageContext.request.contextPath }/mail_loadSendEmail.action",
			  type:"post",
			  async:"false" ,
			  dataType:"json",
			  success:function(result){
				  for(var i  in result){
					var append="  <option   value='"+  result[i]['email_uuid']  +"'>"+ result[i]['email_code']+"</option>";
					  $('#email_code').append(append);    // 给select 添加option子标签
				  }
     			 layui.form.render("select");    

      		  }
    	    });
      }
        
        function deleteEmail() {
        	var email_uuid=$("#email_code").val();
        	if(email_uuid==null||email_uuid==""){
				layui.layer.msg('请选择邮箱后删除');
        		return false;
        	}
        	  layer.confirm('是否删除这个邮箱?', {
	              btn: ['是','否'] //按钮
	          }, function(){
	        	  $.ajax({//删除邮箱信息
	     	      	  url:"${pageContext.request.contextPath }/mail_delSendEmail.action",
	     	      	  type:"post",
	     	      	  async:"false",
	     	      	  dataType:"json",
	     	      	  data:{
	     	      		"email_uuid" : email_uuid  
	     	      	  }, success:function(result){
	     	      			 layer.msg('邮箱删除成功,请刷新页面');
	     	      		}, error:function( ){
	     	      			 try{
	     	      				layer.msg('邮箱删除失败');
	     	      			 }catch (e) {
	     	      				 layer.msg('出错，请告知管理员：'+e); 
	     					}
	     	      		} 
	     	      		});
	          }, function(){
	          });
		 }
        
        function deletePop(){
        	var pop_name=$("#pop_name").val();
        	var city_name=$("#city_name").val();
        	if(pop_name==null||pop_name==""){
				layui.layer.msg('请选择机房后再进行删除');
        		return false;
        	}
        	  layer.confirm('是否删除这个机房?', {
	              btn: ['是','否'] //按钮
	          }, function(){
	        	  $.ajax({//删除机房信息
	     	      	  url:"${pageContext.request.contextPath }/mail_deleteEmailByPopName.action",
	     	      	  type:"post",
	     	      	  async:"false",
	     	      	  dataType:"json",
	     	      	  data:{
	     	      		"pop_name" : pop_name ,
	     	      		"city_name" : city_name 
	     	      	  }, success:function(result){
	     	      			 layer.msg('机房删除成功,请刷新页面');
	     	      		}, error:function( ){
	     	      			 try{
	     	      				layer.msg('机房删除失败');
	     	      			 }catch (e) {
	     	      				 layer.msg('出错，请告知管理员：'+e); 
	     					}
	     	      		} 
	     	      		});
	          }, function(){
	          });
        	
        }
        
        function updateEmailByPopName(){
        	var update_city_name=$("#update_city_name").val();
        	var update_pop_name=$("#update_pop_name").val();
        	var pop_name=$("#pop_name").val();
        	var city_name=$("#city_name").val();
        	var supplier_name=$("#supplier_name").val();
        	var cc_addressee_email=$("#cc_addressee_email").val();
        	var addressee_email=$("#addressee_email").val();
			if(pop_name==null||pop_name==""){
				layui.layer.msg('请选择机房');
        		return false;
        	}
			if(city_name==null||city_name==""){
				layui.layer.msg('请选择地区');
        		return false;
        	}
			if(update_city_name==null||update_city_name==""){
				layui.layer.msg('改后地区名有误');
        		return false;
        	}
			if(update_pop_name==null||update_pop_name==""){
				layui.layer.msg('改后机房名有误');
        		return false;
        	}
			var cc_addressee_email=$("#cc_addressee_email").val();
			var addressee_email=$("#addressee_email").val();
			var reg =/^(\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*(\;))*\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
			 if(cc_addressee_email!=""&&!reg.test(cc_addressee_email)){
				  layui.layer.msg('你的抄送人邮箱格式有错');
				return;
			}
			 if(addressee_email!=""&&!reg.test(addressee_email)){
				 layui.layer.msg('你的收件人邮箱格式有错');
				return;
				}
            $.ajax({//更改机房信息
	      	  url:"${pageContext.request.contextPath }/mail_updateEmailByPopName.action",
	      	  type:"post",
	      	  async:"false",
	      	  dataType:"json",
	      	  data:{
	      		"pop_name" : pop_name ,
	      		"city_name" : city_name ,
	      		"cc_addressee_email" : cc_addressee_email ,
	      		"addressee_email" : addressee_email ,
	      		"update_pop_name" : update_pop_name ,
	      		"update_city_name" : update_city_name ,
	      		"supplier_name":supplier_name
	      	  }, success:function(result){
	      			 layer.msg('邮箱更新成功,请刷新页面');
	      	       	 layui.form.render("select");    
	      		}, error:function( ){
	      			 try{
	      				layer.msg('邮箱更新失败');
	      			 }catch (e) {
	      				 layer.msg('出错，请告知管理员：'+e); 
					}
	      		} 
	      	 
	      		  });
      }
        

    </script>

  </body>
</html>
