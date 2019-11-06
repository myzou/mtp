<%@page import="sun.awt.OSInfo.OSType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--     <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  
  <head>
    <meta charset="UTF-8">
    <title>empower_message页面</title>
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
              <label class="layui-form-label"  title="类型为新增机房是在原来有的地区上增加机房，新增地区是新增加一个地区">
                  <span class="x-red" >*</span>增加类型
              </label>
              <div class="layui-input-inline">
                 <select    id="stem_from"  name="add_type"  lay-verify="required"   lay-filter="add_type" >
                     <option value="新增机房">新增机房</option>
                     <option value="新增地区">新增地区</option>
                  </select>
              </div>
              
              
          </div>
          
           <div class="layui-form-item">
              <label  class="layui-form-label"  >
                  <span class="x-red">*</span>地区：
              </label>
              <div class="layui-input-inline">
              		<div class="newCity" style="display: none;" >
		             	     <input   type="text" id="newCity" name="newCity" required="" lay-verify="city_name"  class="layui-input" >
              		</div>
	                <div class="oldCity">
	                  <select  id="oldCity"  name="oldCity"  lay-verify="city_name" lay-search=""   lay-search=""  >
	                  </select>
	                </div>
	          </div>
              
               <label  class="layui-form-label" for="popNames">
                  <span class="x-red">*</span>所属类型：
              </label>
              <div  class="layui-input-inline"    >
                      <select    id="stem_from"  name="stem_from"  lay-verify="required"   lay-filter="" >
	                     <option value="中国大陆">中国大陆</option>
	                     <option value="港台湾及海外">香港台湾及海外</option>
                 	 </select>
              </div>
          </div>
          
           <div class="layui-form-item"> 
           <label  class="layui-form-label" for="popNames">
                  <span class="x-red">*</span>机房名称：
              </label>
              <div  class="layui-input-block"    >
                   <input type="text" id="pop_name" name="pop_name" required="" lay-verify="required"
                  autocomplete="off" class="layui-input" >
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
          InitializationData();
    	 layui.form.render ();   
          
          //自定义验证规则
          form.verify({
        	  city_name  :function(){
        		var oldCity=  $('#oldCity').val();
        		var newCity=  $('#newCity').val();
				if((oldCity==null||oldCity=="")&&(newCity==""||newCity==null)){
					 return '地区不能为空';
				}
        	  },cc_addressee_email :function(value){
          		var cc_addressee_email=$("#cc_addressee_email").val();
                 	var cc_addressee_email_hid=$("#cc_addressee_email_hid").val();
     				var reg =/^(\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*(\;))*\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
         				 if(cc_addressee_email!=""&&!reg.test(cc_addressee_email)){
         					     return '你的抄送人邮箱格式有错';
         				}
          	   },
          	   addressee_email :function(value){
             		var addressee_email=$("#addressee_email").val();
     				 var reg =/^(\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*(\;))*\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
            				 if(addressee_email!=""&&!reg.test(addressee_email)){
            					     return '你的收件人邮箱格式有错';
            					}
             	   },  nikename: function(value){
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
        		  url:"${pageContext.request.contextPath}/mail_addEmpowerMessage.action",
        		  type:"post",
        		  data:"JsonStr="+JSON.stringify(json),
        		  dataType:"json",
        		  success:function(result){
        		        layer.msg("增加机房成功", {icon: 6,time:1000},function () {
              	            // 获得frame索引
              	            var index = parent.layer.getFrameIndex(window.name);
              	            //关闭当前frame
              	            parent.layer.close(index);
              	           window.parent.location.reload();
              	            });
              	            
        		  } 
        	  	});  
        	
          });
          
          form.on('select(add_type)', function(data){//设置拦截器stem_from 改变或者点击事件
        	  var stem_from=data.value;
        	  if(stem_from=="新增机房"){
				  $('.newCity').hide();
        		  $('.oldCity').show();
//         		  $('#newCity').val('');
        		  form.render();
        	  }else if (stem_from=="新增地区") {
        		  $('.oldCity').hide();
        		  $('.newCity').show();
//         		  $('.newCity').removeClass('layui-hide');
        		  $('#oldCity').val(0);
        		   form.render();
			}
        		 
          });
       
          
        });
        <!--use end -->
        
        /** 开始就加载cityNames 地区**/
        function InitializationData(){
   	
	    $.ajax({//加载所有地区
	    	  url:"${pageContext.request.contextPath }/mail_loadCityNames.action",
			  type:"post",
			  async: "false" ,
			  dataType:"json",
			  success:function(result){
				for(var i=0;i<result.length;i++){//设置北上广深在前面
//					alert("<option value='"+result[i]+"'>"+result[i]+"</option>");
   					var arr=['北京','上海','广州','深圳','luke'];
   					var a=arr.indexOf(result[i]);
						if(arr.indexOf(result[i])>-1){
	    					$('#oldCity').prepend("<option value='"+result[i]+"'>"+result[i]+"</option>");    // 给select 添加option子标签
						}else {
	    					$('#oldCity').append("<option value='"+result[i]+"'>"+result[i]+"</option>");    // 给select 添加option子标签
						}
						}	
							$('#oldCity').prepend("	<option value=''>直接选择或点我搜索全部</option>");
							$('#oldCity').val(0);
					    	form.render();   
			  }
			  });
        	
        }
        
    </script>

  </body>

</html>