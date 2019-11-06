<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--     <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  
  <head>
    <meta charset="UTF-8">
    <title>emailAdd页面</title>
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
    <div class="x-body">
        <form class="layui-form">
        
         <div class="layui-form-item">
              <label for="email_code" class="layui-form-label">
                  <span class="x-red">*</span>发送邮箱：
              </label>
              <div class="layui-input-inline">
                  <select lay-search=""  id="email_uuid"  name="email_uuid"       lay-filter="email_code"    lay-verify="required" >
                    <option value="">直接选择或搜索选择</option>
                  </select>
              </div>
              
              
               <label   class="layui-form-label font10"   title="模版类型对应为邮件发送时候的语言"    >
                  <span class="x-red">*</span>模版类型：
              </label>
              <div class="layui-input-inline"  > 
                  <select    id="emailMode"  name="emailMode"  lay-verify="required"    >
                    <option value="简体中文">简体中文</option>
                     <option value="繁体中文">繁体中文</option>
                       <option value="English">English</option>
                  </select>
              </div>
              
          </div>
        
          <div class="layui-form-item">
              <label for="caseID" class="layui-form-label">
                  <span class="x-red">*</span>caseID：
              </label>
              <div class="layui-input-inline">
                  <input type="text" id="caseID" name="caseID" required="" lay-verify="required"
                  autocomplete="off" class="layui-input">
              </div>
              
      <!-- 		 <label for="insType" class="layui-form-label"> 20181113删除 主题上内容
                  <span class="x-red">*</span>操作类型：
              </label>
              <div class="layui-input-inline" >
              		 <input type="text" id="insType" name="insType" lay-verify="required"
                  autocomplete="off" class="layui-input"   value="维护">
               </div> -->
                  <label for="yesOrNo" class="layui-form-label">
                  <span class="x-red">*</span>是否发送：
              </label>
              <div class="layui-input-inline"  >
                  <select    id="yesOrNo"  name="yesOrNo"      lay-verify="required"    >
                     <option value="是">是</option>
                    <option value="否">否</option>
                  </select>
              </div>
          </div>
          
     <div class="layui-form-item">
	            <label for="start" class="layui-form-label">
	                  <span class="x-red">*</span>开始时间：
	             </label>
	            <div class="layui-input-inline">
	 				<input type="text" class="layui-input" id="start_time" name="start_time"   lay-filter="start_time" autocomplete="off"    lay-verify="required" />          
	          	</div>
	          
	           <label for="start" class="layui-form-label"   >
	                  <span class="x-red">*</span>结束时间：
	              </label>
	            <div class="layui-input-inline">
	 				<input type="text" class="layui-input" id="end_time"  name="end_time"   lay-filter="end_time"  autocomplete="off" lay-verify="required|end_time"   />          
	          </div>
	<!--        <div class="layui-input-inline">
	       		<input type="button"  onclick="get()"  value="获取时间" />
	       </div> -->
	       
          </div>
          
         <div class="layui-form-item">
              <label for="cityName" class="layui-form-label">
                  <span class="x-red">*</span>地区：
              </label>
              <div class="layui-input-inline">
                  <select lay-search=""  id="cityNames"  name="cityNames"   lay-filter="cityNames"    lay-verify="required" >
                  </select>
              </div>
              
                <label for="popName" class="layui-form-label">
                  <span class="x-red">*</span>机房名称：
              </label>
              <div class="layui-input-inline" >
                  <select   lay-search=""  id="popNames"  name="popNames"   lay-filter="popNames"  lay-verify="required"    >
                  <option value="">直接选择或搜索选择</option>
                  </select>
              </div>
          </div>
          
          <!-- 设置添加附件   style="display:none;"-->
   	<div class="layui-form-item" id="addAppendix"  style="display:none;">
              <label  class="layui-form-label">
                  <span class="x-red">*</span>自动填单：
              </label>
              <div class="layui-input-inline">
                  <select   id="voluntarily"  name="voluntarily"  lay-verify="required|voluntarily"   >
                  	<option value="手动">手动</option>
                 	 <option value="自动">自动</option>
                  </select>
              </div>
              
                <label  class="layui-form-label">
                  <span class="x-red">*</span>发单人：
              </label>
                <div class="layui-input-inline" >
              		 <input type="text" id="voluntarilyName" name="voluntarilyName" 
                  autocomplete="on" class="layui-input"  value="陈科宏">
               </div>
          </div>
		<!--   备注 -->
          <div class="layui-form-item" id="addRemarks"  style="display:none;">
              <label  class="layui-form-label">
                  备注：
              </label>
               <div class="layui-input-block">
                  <input type="text" id="remarks" name="remarks"    autocomplete="off" class="layui-input"  title="填单中的备注信息"  >
              </div>
          </div>  
    

         <div class="layui-form-item">
              <label for="caseID" class="layui-form-label font12">
                  <span class="x-red">*</span>收件人邮箱：
              </label>
              <div class="layui-input-block">
                  <input type="text" id="addressee_email" name="addressee_email" required=""  lay-verify="required|addressee_email"
                  autocomplete="off" class="layui-input" >
              </div>
          </div>
          
       <div class="layui-form-item">
              <label   class="layui-form-label font12" >
                  <span class="x-red">*</span>抄送人邮箱：
              </label>
              <div class="layui-input-block">
                  <input type="text" id="cc_addressee_email" name="cc_addressee_email" required="" lay-verify="cc_addressee_email"
                  autocomplete="off" class="layui-input layui-unselect"  title="邮箱格式为xx@xx.com;xx@xx.com  多个邮箱用小写;割开"  >
              </div>
          </div> 
                    
        <div class="layui-form-item">  
            <label for="cabinet" class="layui-form-label">
                  <span class="x-red">*</span>机柜信息：
              </label>
              <div class="layui-input-block">
                  <input type="text" id="cabinet" name="cabinet" required="" 
                  autocomplete="off" class="layui-input"  lay-verify="required" >
              </div>
          </div>
          
            <div class="layui-form-item">
           		 <label  class="layui-form-label">
                  <span class="x-red">*</span>操作详细 :
                  </label>
              <div class="layui-input-block">
                  <input type="text" id="opType" name="opType" lay-verify="required"  autocomplete="off" class="layui-input"  value="设备维护">
              </div>
          </div>
          
             <div class="layui-form-item">
           		 <label  class="layui-form-label">
                   备注 :
                  </label>
              <div class="layui-input-block">
                  <input type="text" id="remarks" name="remarks"    autocomplete="off" class="layui-input"  >
              </div>
          </div>
          
            <div class="layui-form-item">  
            <label for="intoPersonnel" class="layui-form-label" >
                  <span class="x-red">*</span>相关人员：
              </label>
              <!-- <div class="layui-input-block">
                  <input type="text" id="intoPersonnel" name="intoPersonnel" lay-verify="required"
                  autocomplete="off" class="layui-input">
              </div> -->
                   <div class="layui-input-block">
                  <textarea placeholder="申请相关人员信息：" id="intoPersonnel" name="intoPersonnel" class="layui-textarea"		lay-verify="required|intoPersonnel" ></textarea>
              </div>
         	 </div>
          
            <div class="layui-form-item">  
            <label for="carryFacility" class="layui-form-label">
                  <span class="x-red">*</span>携带设备：
              </label>
                <div class="layui-input-block">
                 <textarea id="carryFacility" name="carryFacility"   class="layui-textarea"	 autocomplete="off"	 lay-verify="required|carryFacility"
                  autocomplete="off"   >无</textarea>
              </div>
<!--               <input  type="button"  onclick="test()" value="测试" /> -->
          </div>
          
 
             <div class="layui-form-item">
          	<label  class="layui-form-label">
                  上传文件：
              </label>
          <div   class="layui-col-md12">
			<div class="modal fade" id="uploadModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			 <div class="modal-dialog">
			  <div class="modal-content">
			   <div class="modal-header">
			   </div>
			   <div class="">
			    <div class="layui-upload">
					  <button type="button" class="layui-btn layui-btn-normal" id="chooseFile">选择附件</button> 
					 <button type="button" class="layui-btn" id="upload">开始上传</button><!--  layui-hide -->
					  <div class="layui-upload-list">
					  	<label  class="layui-form-label">
            			  </label>
					    <table class="layui-table"  lay-size="sm">
					      <thead>
					        <tr><th>文件名</th>
					        <th>大小</th>
					        <th>状态</th>
					        <th>操作</th>
					      </tr></thead>
					      <tbody id="proImageList"></tbody>
					    </table>
					  </div>
<!-- 					  <button type="button" class="layui-btn" id="back">返回</button> -->
			   </div>
			  </div><!-- /.modal-content -->
			 </div><!-- /.modal -->
			</div>
             </div>
          </div>
          
				<!-- 隐藏input ,存放对应的上传表单信息-->
     			 <input   type="hidden"  id="upLoadFiles"  name="upLoadFiles"  autocomplete="off" value=""    />
           		<!-- 隐藏input ,存放文件是否已经上传了-->
     			 <input   type="hidden"  id="upYesOrNo"  name="upYesOrNo"  autocomplete="off" value="false"    />
     			 <!-- 隐藏input ,存放手动改之前的抄送人value-->
     			 <input   type="hidden"  id="cc_addressee_email_hid"  name="cc_addressee_email_hid"  autocomplete="off" value=""    />
     			 <!--     防止重复提交 -->
     			  <input id="submit_flag" type="hidden" value="false"/> 
     			 
	         <div class="layui-form-item">
	              <label   class="layui-form-label">
	              </label>
	              <button  class="layui-btn" type="button" lay-filter="add"  lay-submit="">
	                  发送email
	              </button>
	          </div>
             <!--       <div class="layui-form-item layui-form-text">
              <label for="desc" class="layui-form-label"> 描述
              </label>
              <div class="layui-input-block">
                  <textarea placeholder="请输入内容" id="desc" name="desc" class="layui-textarea"></textarea>
              </div>
          </div> -->
    
             
    </div>
          
      </form>
      
      		
      
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
    
    function sleep(n) {
        var start = new Date().getTime();
        //  console.log('休眠前：' + start);
        while (true) {
            if (new Date().getTime() - start > n) {
                break;
            }
        }
//         console.log('休眠后：' + new Date().getTime());
    }
 
   

    
    function get() {
		var start_time =$('#start_time').val();
		var end_time =$('#end_time').val();
		alert(end_time>start_time);
		alert("开始时间：（"+start_time+"），结束时间：（"+end_time+")");
	}
      

    var form,layedit,layer,laydate,$; 
        layui.use([ 'jquery','form', 'layedit','layer','laydate'], function(){
            $ = layui.jquery;
           form = layui.form;
           layer = layui.layer;
           laydate = layui.laydate;  
           date =new Date();
	        var nowTime = new Date().valueOf();  
			InitializationData();  	//初始化 加载select数据
			form.render();
			  //日期
			  var start = laydate.render({ // 开始时间
			         elem: '#start_time',
			         min:nowTime,
			         type: 'datetime',  
			         format:'yyyy-MM-dd HH:mm:ss',
			         done:function(value,date){
			        	 $('#start_time').change(); 
			             endMax = end.config.max;
			             end.config.min = date;
			             end.config.min.month = date.month -1;
			         }
			     });
			  var end = laydate.render({ // 结束时间
			        elem: '#end_time',
			        min : nowTime,
			       type: 'datetime',  
			        format:'yyyy-MM-dd HH:mm:ss',
			        done:function(value,date){
			        	 $('#end_time').change(); 
			            if($.trim(value) == ''){
			                var curDate = new Date();
			                date = {'date': curDate.getDate(), 'month': curDate.getMonth()+1, 'year': curDate.getFullYear()};
			            }
			            min=nowTime;
			            start.config.max = date;
			            start.config.max.month = date.month -1;
			        }
			     });
		 	 
          
	  form.on('select(cityNames)', function(data){//设置拦截器cityNames 改变或者点击事件
		  var popName=data.value;
// 			  alert (popName);
			  if(popName==""){
				 $.ajax({//加载所有的城市
		   	    	  url:"${pageContext.request.contextPath }/mail_loadEmpowerMessageByDao.action",
		   			  type:"post",
		   			  async:"true",
		   			  data :"" ,
		   			  dataType:"json",
		   			  success:function(result){
		   				$('#popNames').empty();
		   			  $('#popNames').append(" <option value='' >直接选择或搜索选择</option> ");    // 给select 添加option子标签
		   				for(var val1 in result){
		   				 var append="  <option   value='"+  result[val1]['pop_name']  +"'>"+result[val1]['pop_name']+"</option>";
//		   				 alert(append);
		   				$('#popNames').append(append);    // 给select 添加option子标签
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
	    				  $('#popNames').empty();
	    				  $('#popNames').append(" <option value='' >直接选择或搜索选择</option> ");    // 给select 添加option子标签
	    				for(var i=0;i<result.length;i++){
	    						$('#popNames').append("<option value='"+result[i]+"'>"+result[i]+"</option>");    // 给select 添加option子标签
	    				}
	    				layui.form.render("select");    
	    			  }
	    			  });	
			  }
        	}); 
 	 
 	  form.on('select(popNames)', function(data){//设置拦截器popNames 改变或者点击事件
			  	var popName=data.value;
	// 			  alert (popName);
				 $.ajax({
		    	  url:"${pageContext.request.contextPath }/mail_getEmpowerMessage.action",
				  type:"post",
				  async:"true",
				  data :"popName="+popName ,
				  dataType:"json",
				  success:function(result){
// 				   layer.alert(JSON.stringify(result[0]) );
// 				  alert(result[0]['city_name']);
// 					var aa="option:contains('"+result[0]['city_name']+"')";
// 					var sss=$("#cityNames").find("option[value='"+result[0]['city_name']+"']");
// 				   $("#cityNames").find(aa).attr("selected",true);
				   set_select_checked('cityNames', result[0]['city_name']);
					 var reg = new RegExp("###","g");//g,表示全部替换。
					var addressee_email=result[0].addressee_email.replace(reg,';');
					var cc_addressee_email=result[0].cc_addressee_email.replace(reg,';');
					$("#addressee_email").val(addressee_email);
					$("#cc_addressee_email").val(cc_addressee_email);
					$("#cc_addressee_email_hid").val(cc_addressee_email);
					if(addressee_email==""){//如果没有收件人邮箱 改为可以编辑
						$("#addressee_email").removeAttr("disabled");
						$("#addressee_email").removeClass("layui-disabled");
						$("#addressee_email").attr("title","请手动输入正确的收件人邮箱，多个邮箱小写;割开");
					}else{
						$("#addressee_email").attr("disabled","disabled");
						$("#addressee_email").addClass("layui-disabled");
						$("#addressee_email").attr("title","不可编辑，需要增加邮箱请增加在抄送人邮箱");
					}
					if(popName=="北京中信机房"){
						$("#addAppendix").attr("style","display:block;");//显示addAppendix
						$("#addRemarks").attr("style","display:block;");//显示addRemarks
						
					}
					
// 					alert("addressee_email:"+addressee_email);
// 					alert("cc_addressee_email:"+cc_addressee_email);
					
				
				layui.form.render("select");    
			  }
			  });	
		  
    	}); 
 	  
		 	  form.on('select(end_time)', function(data){//设置拦截器popNames 改变或者点击事件
		 		 var start_time =$('#start_time').val();
		 		var end_time =$('#end_time').val();
		 		if(end_time<start_time){
		 			layer.alert("结束时间必须大于开始时间");
		 		}
 	
 	 }); 

  	 //自定义验证规则
           form.verify({
        	   end_time:function(value){
        		 	var s=$("#start_time").val();
                	var end=$("#end_time").val();
                	if(s>=end){
                		return '结束时间要大于开始时间';
                	}
        	   },
        	   cc_addressee_email:function(value){
        		var cc_addressee_email=$("#cc_addressee_email").val();
               	var cc_addressee_email_hid=$("#cc_addressee_email_hid").val();
            	var reg =/^(\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*(\;))*\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
       				 if(cc_addressee_email!=""&&!reg.test(cc_addressee_email)){
       					     return '你的抄送人邮箱格式有错';
       					  } 
       				if(cc_addressee_email_hid!=""&&!(cc_addressee_email.indexOf(cc_addressee_email_hid)>-1)){
       					     return '请不要修改原来的抄送人邮箱信息';
       					}
        		   
        	   },
        	   addressee_email:function(value){
           		var addressee_email=$("#addressee_email").val();
           				var reg =/^(\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*(\;))*\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
          				 if(addressee_email!=""&&!reg.test(addressee_email)){
          						return '你的收件人邮箱格式有错';
          					  } 
           	   },intoPersonnel :function(value){
           		 if(value.length>1000){
         	        return '相关人员文字长度不能大于1000';
         	      }
		       } ,carryFacility :function(value){
	          		 if(value.length>1000){
	          	        return '携带设备文字长度不能大于1000';
	          	      }
	       		 } , nikename: function(value){
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
        	var JsonStr=  JSON.stringify(data.field);
        	  var trList=$('#proImageList').find('tr');
        	  JsonStr=  htmlEncodeByRegExp(JsonStr);
        	  var error="";
              for(var i=0;i<trList.length;i++){
              	var tdArr=trList.eq(i).find("td");
              	if(!(tdArr.eq(2).text().indexOf("上传成功")>-1)){//如果没有上传成功 就点击上传按钮 上传文件到后台
              		error="false";
//               		layer.alert(tdArr.eq(0).text());
              		layer.msg("有文件没有上传，正在上传...",{time:1000});
              		$("#upload").trigger("click");
              		return false;
              	}
              }
           
              var submit_flag=$("#submit_flag").val();
          		if(submit_flag!="false"){
          		$("#submit_flag").val("true");
    			layer.confirm('您已经提交过这个表单了，再次提交吗？', {
	    			  btn: ['确定','取消'] //按钮
	    			}, function(){
		    			    $.ajax({
		   			    	  url:"${pageContext.request.contextPath }/mail_sendEmail.action",
		   	  			  type:"post",
		   	  			  async:"true",
		   	  			  data:	"JsonStr="+JsonStr,
		   	  			  dataType:"json",
		   	  			  success:function(result){
		   	  			   //发异步，把数据提交给php
		          	 	  		layer.msg("增加成功，已经发送邮件", {icon: 6,time:1000},function () {
		   	 		            // 获得frame索引
		   	 		            var index = parent.layer.getFrameIndex(window.name);
		   	 		            //关闭当前frame
		   	 		            parent.layer.close(index);
		   	 		          	window.parent.location.reload();
		   	 		            });
		   	 		          
		   	  			  }
		   	  			  });
	    			}, function(){
	    			});
		    		}else{
		    			   $("#submit_flag").val("true");
		    			   $.ajax({
			   			    	  url:"${pageContext.request.contextPath }/mail_sendEmail.action",
			   	  			  type:"post",
			   	  			  async:"true",
			   	  			  data:	"JsonStr="+JsonStr,
			   	  			  dataType:"json",
			   	  			  success:function(result){
			   	  			   //发异步，把数据提交给php
			          	 	  		layer.msg("增加成功，已经发送邮件", {icon: 6,time:1000},function () {
			   	 		            // 获得frame索引
			   	 		            var index = parent.layer.getFrameIndex(window.name);
			   	 		            //关闭当前frame
			   	 		            parent.layer.close(index);
			   	 		          	window.parent.location.reload();
			   	 		            });
			   	 		          
			   	  			  }
			   	  			  });
		    		}
              
//         	alert(JsonStr);
        		
		          });
          
       
          
        });
        
        function test(){
        	var s=$("#start_time").val();
        	var end=$("end_time").val();
        	alert(end);
        	alert(s);
        	if(s>end){
        		alert("开始时间大于结束时间");
        	}
        	  var cc_addressee_email=$("#cc_addressee_email").val();
        	 var cc_addressee_email_hid=$("#cc_addressee_email_hid").val();
			  var reg = /^((([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6}\;))*(([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})))$/;
/* 			if(cc_addressee_email_hid!=""){
				 if(!reg.test(cc_addressee_email)){
					  alert("你的抄送人邮箱格式有错");
					  }else{
					   alert("你的抄送人邮箱格式正确");
					  }
				if(!(cc_addressee_email.indexOf(cc_addressee_email_hid)>-1)){
					  alert("请不要修改原来的抄送人邮箱信息");
				}
			} */
			
			 
        	var upLoadFiles= $("#upLoadFiles").val(); 
      	 var upLoadStrings=  upLoadFiles.split("###");
//       	 alert(upLoadFiles);
      	 var upLoadStrings=  upLoadFiles.split("###");
      	 for(var a in upLoadStrings){
//       		alert(upLoadStrings[a]);
      	 }
        	
	      }

        /** 开始就加载cityNames 地区**/
        function InitializationData(){
        	
		    $.ajax({//加载所有地区
		    	  url:"${pageContext.request.contextPath }/mail_loadCityNames.action",
    			  type:"post",
    			  async:"true",
    			  dataType:"json",
    			  success:function(result){
    				
    				for(var i=0;i<result.length;i++){//设置北上广深在前面
	    					var arr=['北京','上海','广州','深圳'];
							if(arr.indexOf(result[i])>-1){
		    					$('#cityNames').prepend("<option value='"+result[i]+"'>"+result[i]+"</option>");    // 给select 添加option子标签
							}else {
		    					$('#cityNames').append("<option value='"+result[i]+"'>"+result[i]+"</option>");    // 给select 添加option子标签
							}
    						}	
    							$('#cityNames').prepend("	<option value=''>直接选择或点我搜索全部</option>");
    							 $("#cityNames").val("");
    							layui.form.render("select");    
    			  }
    			  });
		    	
			    $.ajax({//加载所有发送的email
			    	  url:"${pageContext.request.contextPath }/mail_loadSendEmail.action",
				  type:"post",
				  async:"true",
				  dataType:"json",
				  success:function(result){
					  for(var prop  in result){
								 var append="  <option   value='"+  result[prop]['email_uuid']  +"'>"+ result[prop]['email_code']+"</option>";
						  $('#email_uuid').append(append);    // 给select 添加option子标签
					  }
					  layui.form.render("select");    
				  }
				  });
			    
				 $.ajax({//加载所有的城市
		   	    	  url:"${pageContext.request.contextPath }/mail_loadEmpowerMessageByDao.action",
		   			  type:"post",
		   			  async:"true",
		   			  data :"" ,
		   			  dataType:"json",
		   			  success:function(result){
		   				for(var val1 in result){
		   				 var append="  <option   value='"+  result[val1]['pop_name']  +"'>"+result[val1]['pop_name']+"</option>";
		   				$('#popNames').append(append);    // 给select 添加option子标签
		   					}
		   				layui.form.render("select");    
		   			  }
		   			  });	
				 
        }
        function htmlEncodeByRegExp(str){  
            var s = "";
            if(str.length == 0) return "";
            s = str.replace(/&/g,"%26");
            return s;  
      }

    </script>
	     <script>
        
	        var uploadListIns;
	      //多文件列表示例  图片上传
	      
	      var form,layedit,layer,laydate,$; 
        layui.use([ 'jquery', 'layedit','layer','laydate','upload'], function(){
            $ = layui.jquery;
           layer = layui.layer;
           laydate = layui.laydate;
           form = layui.form; 
           form.render();
           upload = layui.upload;
	       var demoListView = $('#proImageList');
	       uploadListIns = upload.render({
	        elem: '#chooseFile', //选择文件的按钮
	        url: '${pageContext.request.contextPath }/mail_uploadImg.action', //后台处理文件长传的后台方法
	        data:{},//传到后台的方法
	        accept: 'file', 
// 	        size:'5120',//最大文件限制
	        method:'post' ,
	        multiple: true,  //是否允许多文件上传
// 	        acceptMime: 'image/*', //规定打开文件选择框时，筛选出的文件类型
	        field:'file',  //设定文件域的字段名
	        drag:'true',//是否能接受拖拽文件上传
	        auto: false, //选择文件后是否自动上传
	        bindAction: '#upload', //用来触发上传的按钮ID
	        before:function(obj){
// 	        	 console.log('接口地址：'+ this.url, this.item, {tips: 1});
// 	        	layer.load();
	        },
	        choose: function(obj){ //选择文件后的回调函数，本例中在此将选择的文件进行展示
	        	$("#upload").trigger("click");
	         var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
	         //读取本地文件
	         obj.preview(function(index, file, result){
	          var tr = $(['<tr id="upload-'+ index +'">'
	           ,'<td>'+ file.name +'</td>'
	           ,'<td>'+ (file.size/1014).toFixed(1) +'kb</td>'
	           ,'<td>等待上传</td>'
	           ,'<td>'
	           ,'<button class="layui-btn layui-btn-xs demo-reload layui-hide">重传</button>'
	           ,'<button class="layui-btn layui-btn-xs layui-btn-danger demo-delete">删除</button>'
	           ,'</td>'
	           ,'</tr>'].join(''));
	        
	          //单个重传
	          tr.find('.demo-reload').on('click', function(){
	           obj.upload(index, file);
	          });
	        
	          //删除
	          tr.find('.demo-delete').on('click', function(){
	        	  var upLoadFiles= $("#upLoadFiles").val(); 
	        	 var upLoadStrings=  upLoadFiles.split("###");
	        	 var newUpLoadFiles="";
	          	 for(var a in upLoadStrings){
	          		 var delFileName=tr.eq(0).find("td").eq(0).text();
	          		 var sub=upLoadStrings[a].substring(upLoadStrings[a].lastIndexOf("\\")+1);
	          		 if(delFileName!=sub){
	          			 if(newUpLoadFiles==""){
	          				newUpLoadFiles=newUpLoadFiles+upLoadStrings[a];
		          		 }else{
		          			newUpLoadFiles=newUpLoadFiles+"###"+upLoadStrings[a];
		          		 }
	          		 }
	          	 }
	          	$("#upLoadFiles").val(newUpLoadFiles); 
	           delete files[index]; //删除对应的文件
	           tr.remove();
	           uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
	          });
	          demoListView.append(tr);
	         });
	        },  
	        done: function(res, index, upload){    //多文件上传时，只要有一个文件上传成功后就会触发这个回调函数
	         if(res.status == "success"){ //上传成功
	          var tr = demoListView.find('tr#upload-'+ index)
	           ,tds = tr.children();
	          tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
// 	       tds.eq(3).html('<a href="'+res.url+'" rel="external nofollow" >查看</a>'); //清空操作
// 			tds.eq(3).html('<button class="layui-btn layui-btn-xs layui-btn-danger demo-delete">删除</button>');
				var upLoadFiles= $("#upLoadFiles").val(); 
				var fullPath=res.fullPath;
				if(upLoadFiles.indexOf(fullPath)<0){
				if(upLoadFiles==""){
						$("#upLoadFiles").val(upLoadFiles+fullPath);//第一次不分割 
				}else{
					$("#upLoadFiles").val(upLoadFiles+"###"+fullPath );//###分割开  
				}
				}
	          return delete this.files[index]; //删除文件队列已经上传成功的文件
	         }else{//如果status不是success
	          alert(res.message);
	         }
	         this.error(index, upload);
	        },
	        allDone: function(obj){ //当文件全部被提交后，才触发
// 	        	 console.log(JSON.stringify(obj));
	         if(obj.total > obj.successful){
	          layer.msg("有文件上传失败，暂不更新生产进度，请重试或联系管理员");
	         }else {
	          //更新生产进度
// 	          updateProductionSchedule(currentId, currentSchedule);
	         }
	        },
	        error: function(index, upload){
	         var tr = demoListView.find('tr#upload-'+ index)
	          ,tds = tr.children();
	         tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
	         tds.eq(3).find('.demo-reload').removeClass('layui-hide'); //显示重传
	        }
	       });
	       $(".layui-upload-file").hide();
	      });

	
      </script>


  </body>

</html>
