<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--     <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  
  <head>
    <meta charset="UTF-8">
    <title>授权申请页面</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!--   <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" /> -->
    <link rel="stylesheet" href="./css/font.css">
    <link rel="stylesheet" href="./css/xadmin.css">
    <script type="text/javascript" src="./js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="./lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="./js/xadmin.js"></script>
    <style type="text/css">
    .w90{
    width: 90%;
     }
   .w89{
    width: 89%;
     }
    .w50{
    width: 50%;
     }
     .w15{
     width: 15%;
     }
     .ml10{
     margin-left: 10%
     }
     .text3 {
	  font-family: tahoma, helvetica;
	  font-size: 10pt;
	  color: #3333cc;
	  background-color: #DDDDDD;
	  text-align:center;
	}
	 .w66{
	width: 66%     
	}
	
	body{
	background-color: 	#F8F8F8;
	}
	.elem1 {
		  width:165px;
		  height:20px;
		  text-align:right;
	}
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

    <form class="layui-form" action="">
   		 <br>
	     <div class="layui-form-item"  style="text-align:center;">
<!-- 	    申请信息 -->
	     </div>
	     
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
                  <input type="text" id="case_id" name="case_id" required="" lay-verify="required"
                  autocomplete="off" class="layui-input">
              </div>
              
              <!-- 隐藏授权申请auth_id -->
              <input type="hidden" class ="layui-hidden" id ="auth_id"  name="auth_id"  value="" /> 
              
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
              <label   class="layui-form-label"  style="font-size: 12px;">
                  <span class="x-red">*</span>供应商名称：
              </label>
              <div class="layui-input-block" style="width: 53.3%">
               <input  id="supplier_name"  name="supplier_name" lay-verify="supplier_name" autocomplete="off"   placeholder="供应商名称"  value="" class="layui-input">
              </div>
              
	          </div>
          
             <div class="layui-form-item">
	            <label for="start" class="layui-form-label">
	                  <span class="x-red">*</span>开始时间：
	             </label>
	            <div class="layui-input-inline">
	 				<input type="text" class="layui-input" id="start_time" name="start_time"   autocomplete="off"    lay-verify="required"   value=""/>            
	          	</div>
	          
	           <label for="start" class="layui-form-label"   >
	                  <span class="x-red">*</span>结束时间：
	              </label>
	            <div class="layui-input-inline">
	 				<input type="text" class="layui-input" id="end_time"  name="end_time"   autocomplete="off" lay-verify="required"   value=""/>          
	          </div>
	<!--        <div class="layui-input-inline">
	       		<input type="button"  onclick="get()"  value="获取时间" />
	       </div> -->
          </div>
          
              <div class="layui-form-item">
              <label for="cityName" class="layui-form-label">
                  <span class="x-red">*</span>姓名：
              </label>
              <div class="layui-input-inline">
               <input   id="name"   name="name" lay-verify="" autocomplete="off"   placeholder="请输入申请人姓名" class="layui-input"  lay-verify="required"  value=""/>          
              </div>
              
                <label   class="layui-form-label">
                  <span class="x-red">*</span>部门：
              </label>
              <div class="layui-input-inline" >
                  <select  id="company"  name="company"  lay-search=""        lay-filter="dept"  lay-verify="required"    >
						<option value="Sales">Sales</option>
						<option value="Product">Product</option>
						<option value="Helpdesk">Helpdesk</option>
						<option value="Engineering">Engineering</option>
						<option value="Account">Account</option>
						<option value="NOC">NOC</option>
						<option value="CS-Admin">CS-Admin</option>
						<option value="CS-PM">CS-PM</option>
						<option value="Others">Others</option>
                  </select>
              </div>
          </div>
          
         <div class="layui-form-item">
         	<label for="cityName" class="layui-form-label">
                  <span class="x-red">*</span>地区：
              </label>
              <div class="layui-input-inline">
                  <select lay-search=""  id="city_name"  name="city_name"   lay-filter="cityNames"    lay-verify="required" >
                  </select>
              </div>
         
                <label for="popName" class="layui-form-label">
                  <span class="x-red">*</span>访问机房：
              </label>
              <div class="layui-input-inline" > 
                  <select   lay-search=""  id="pop_name" style=""  name="pop_name"   lay-filter="popNames"  lay-verify="required"    >
                  </select>
              </div>
              
<!--               <input type="button" onclick="default1()" value="测试"> -->
          </div>
          
          <div class="layui-form-item">
              <label   class="layui-form-label font12">
                  <span class="x-red">*</span>收件人邮箱：
              </label>
              <div class="layui-input-block w66">
                  <input type="text" id="addressee_email" name="addressee_email" required="" lay-verify="required|addressee_email"
                  autocomplete="off" class="layui-input" >
              </div>
          </div>
          
       <div class="layui-form-item">
              <label   class="layui-form-label font12" >
                  <span class="x-red">*</span>抄送人邮箱：
              </label>
              <div class="layui-input-block w66">
                  <input type="text" id="cc_addressee_email" name="cc_addressee_email" required="" lay-verify="cc_addressee_email"
                  autocomplete="off" class="layui-input layui-unselect"  title="邮箱格式为xx@xx.com;xx@xx.com  多个邮箱用小写;割开"  >
              </div>
         </div> 
          
           <div class="layui-form-item">
          <label for="cityName" class="layui-form-label" >
                  <span class="x-red">*</span>操作：
              </label>
              <div class="layui-input-block w66">
               <input   id="op_type"   name="op_type"  autocomplete="off"   placeholder="此次进入机房的操作" class="layui-input"  lay-verify="required"  value=""/>          
              </div>
    		</div>
    		
    	<div class="layui-form-item">  
            <label   class="layui-form-label">
                  <span class="x-red">*</span>机柜信息：
              </label>
              <div class="layui-input-block w66">
                  <input type="text" id="cabinet" name="cabinet" required="" 
                  autocomplete="off" class="layui-input"  lay-verify="required" >
              </div>
          </div>
          
    		
    	 <div class="layui-form-item">
		    <label class="layui-form-label">
		    	<span class="x-red">*</span>Email:</label>
		    <div class="layui-input-block w66"  >
		      <input type="text"   id="email"   name="email" lay-verify="required" autocomplete="off" 
		      placeholder="请输入申请人Email信息" class="layui-input"  lay-verify="required"  value=""/>          
		    </div>
		  </div>
		  
          
             <div class="layui-form-item">
              <label  class="layui-form-label">
                   备注：
              </label>
              <div class="layui-input-block w66"  >
               <input   id="remarks"   name="remarks" lay-verify="" autocomplete="off"   placeholder="可以通过查看备注信息进行更改对应信息"   class="layui-input">
              </div>
          </div>
		  
		  <div style="margin-bottom:5px"  >
		       <input  type="button"  id="addRow1" class="ml10 layui-btn layui-btn-xs "    value="Add Row"   />
			   <input type="button" class="layui-btn layui-btn-xs" value="Delete Row" onclick="deleteRow('intoPersonnelList')" />
			   <label  style="padding-bottom: 10px">进入机房人员：</label>
    	  </div>
    
		    <table class="ml10"    border="1" cellspacing="0" cellpadding="4" width="50%"  id="intoPersonnelList"  name="intoPersonnelList">
		    <thead>
		      <tr>
		      <th class="text3" style="padding: 10px" ></th>
		        <th class="text3">姓名</th>
		        <th class="text3">id</th>
		        <th class="text3">部门</th>
		          <th class="text3">其他部门</th>
		      </tr> 
		    </thead>
		    <tbody>
		    </tbody>
		  </table>
		  
		    <br>
     		<div class="ml10"  style="margin-bottom:5px"   >
			     <input type="button"  id="addRow2" class="layui-btn layui-btn-xs" value="Add Row"   />
			    <input type="button" class="layui-btn layui-btn-xs" value="Delete Row" onclick="deleteRow('carryFacilityList')" />
			    <label >携带设备信息</label>
    	  </div>
    	  
		    <table class="ml10"  lay-size="sm"  border="1" cellspacing="0" cellpadding="4" width="50%"  id="carryFacilityList" name="carryFacilityList">
		    <thead>
		      <tr>
		      <th class="text3"  ></th>
		        <th class="text3">生产厂家</th>
		        <th class="text3">设备型号信息</th>
		        <th class="text3">序列号/车牌号</th>
		        <th class="text3">用途</th>
		      </tr> 
		    </thead>
		    <tbody>
		    </tbody>
		  </table>
	  
	    <!-- <div class="layui-form-item">
          	<label  class="layui-form-label">
                  上传文件：
              </label>
          <div   class="layui-col-md12">
			<div class="modal fade" id="uploadModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			 <div class="modal-dialog">
			  <div class="modal-content">
			   <div class="modal-header">
			   </div>
			    <div class="layui-upload">
					  <button type="button" class="layui-btn layui-btn-normal" id="chooseFile">选择附件</button> 
					 <button type="button" class="layui-btn" id="upload">开始上传</button> layui-hide
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
					  <button type="button" class="layui-btn" id="back">返回</button>
			   </div>
			 </div>/.modal
			</div>
             </div>
          </div>
          </div> -->
			<!-- 隐藏input ,存放对应的上传表单信息-->
  			 <input   type="hidden"  id="upLoadFiles"  name="upLoadFiles"  autocomplete="off" value=""    />
        	<!-- 隐藏input ,存放文件是否已经上传了-->
  			 <input   type="hidden"  id="upYesOrNo"  name="upYesOrNo"  autocomplete="off" value="false"    />
  			 <!-- 隐藏input ,存放手动改之前的抄送人value-->
  			 <input   type="hidden"  id="cc_addressee_email_hid"  name="cc_addressee_email_hid"  autocomplete="off" value=""    />
     		<input   type="hidden"  id="create_time"  name="create_time"      />	
     		
     	<!--  <div class="layui-form-item">
	    <div class="layui-input-block">
	      <button id ="add" class="layui-btn" lay-submit=""   type="button" lay-filter="add" >立即提交</button>
	      <button type="reset" class="layui-btn layui-btn-primary">重置</button>
	    </div>
	  </div> -->
  
</form>
	<!--     防止重复提交 -->
    <input id="submit_flag" type="hidden" value="false"/> 
    
    <script>
    
    
    function deleteRow(tableID) {
        try {
        var table = document.getElementById(tableID);
        var rowCount = table.rows.length;
        for(var i=0; i<rowCount; i++) {
            var row = table.rows[i];
            var checked = row.cells[0].childNodes[0];
            if(null != checked && row.cells[0].getElementsByTagName("div")[0].className.indexOf('layui-form-checked')>-1) {
                if(rowCount <= 2) {
                    alert("无法删除所有行。");
                    break;
                }
                table.deleteRow(i);
                rowCount--;
                i--;
            }
        }
        }catch(e) {
            alert(e);
        	}
  		  }
    
    window.onload=function(){
    }
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
    var form,layedit,layer,laydate,$; 
    layui.use([ 'jquery','form', 'layedit','layer','laydate','table'], function(){
        $ = layui.jquery;
       form = layui.form;
       layer = layui.layer;
       laydate = layui.laydate;  
       var table = layui.table;  
       var nowTime = new Date().valueOf();  
       loadPopNames();
       loadCityNames();
       default1();
       form.render();
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
       
       $(document).on('click','#addRow1',function(){
    	     var table = document.getElementById('intoPersonnelList');
    	        var rowCount = table.rows.length;
    	        var row = table.insertRow(rowCount);
    	        var colCount = table.rows[0].cells.length;
    	        for(var i=0; i<colCount; i++) {
    	            var newcell = row.insertCell(i);
    	            newcell.innerHTML = table.rows[1].cells[i].innerHTML;
    	            newcell.setAttribute("class", "text3");
    	            //alert(newcell.childNodes);
    	            var type=newcell.childNodes[0].type;
    	            switch(type) {
    	                case "text":
    	                        newcell.childNodes[0].value = "";
    	                        break;
    	                case "checkbox":
    	                        newcell.childNodes[0].checked = false;
    	                        break;
    	                case "select-one":
    	                        newcell.childNodes[0].selectedIndex = 0;
    	                        break;
    	            }
    	        }
    	   form.render();
    	   });
       
       $(document).on('click','#addRow2',function(){
  	     var table = document.getElementById('carryFacilityList');
  	        var rowCount = table.rows.length;
  	        var row = table.insertRow(rowCount);
  	        var colCount = table.rows[0].cells.length;
  	        for(var i=0; i<colCount; i++) {
  	            var newcell = row.insertCell(i);
  	            newcell.innerHTML = table.rows[1].cells[i].innerHTML;
  	            newcell.setAttribute("class", "text3");
  	            //alert(newcell.childNodes);
  	            var type=newcell.childNodes[0].type;
  	            switch(type) {
  	                case "text":
  	                        newcell.childNodes[0].value = "";
  	                        break;
  	                case "checkbox":
  	                        newcell.childNodes[0].checked = false;
  	                        break;
  	                case "select-one":
  	                        newcell.childNodes[0].selectedIndex = 0;
  	                        break;
  	            }
  	        }
  	   form.render();
  	   });
       
  	 //自定义验证规则
       form.verify({
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
       	   }
      }); 
       
       form.on('submit(add)', function(data){
    	var field=data.field;
       	var JsonStr=  JSON.stringify(data.field);
       	var table_json1="[";
       	var trs= document.getElementById("intoPersonnelList").getElementsByTagName("tr");
       	for(var i=1;i<trs.length;i++){
       		var tds=trs[i].getElementsByTagName('td');
       		var p_name=tds[1].getElementsByTagName('input')[0].value;
   			var p_id=tds[2].getElementsByTagName('input')[0].value;
   			var select=document.getElementById('intoPersonnelList').getElementsByTagName('tr')[i].getElementsByTagName('td')[3].getElementsByTagName('select');
   			var p_company=select[0].value;
   			var p_other_company=tds[4].getElementsByTagName('input')[0].value;
       		if(p_name!=null&&p_name!=""){
       			table_json1+="{\"p_name\":"+"\""+p_name+"\",";
       			table_json1+="\"p_id\":"+"\""+p_id+"\",";
       			table_json1+="\"p_company\":"+"\""+p_company+"\",";
       			table_json1+="\"p_other_company\":"+"\""+p_other_company+"\"},";
       		}
       	}
     	if(table_json1.length>2){
       		table_json1=table_json1.substr(0,table_json1.length-1);
     	}
       		table_json1+="]";
	       	 var tableArr1=eval("("+table_json1+")");
	    	
	    	var table_json2="[";
	       	var trs= document.getElementById("carryFacilityList").getElementsByTagName("tr");
	       	for(var i=1;i<trs.length;i++){
	       		var tds=trs[i].getElementsByTagName('td');
	       		var c_manufacturer=tds[1].getElementsByTagName('input')[0].value;
	   			var c_model=tds[2].getElementsByTagName('input')[0].value;
	   			var c_serial_no=tds[3].getElementsByTagName('input')[0].value;
	   			var c_operate=tds[4].getElementsByTagName('input')[0].value;
	       		if(c_model!=null&&c_model!=""){
	       			table_json2+="{\"c_manufacturer\":"+"\""+c_manufacturer+"\",";
	       			table_json2+="\"c_model\":"+"\""+c_model+"\",";
	       			table_json2+="\"c_serial_no\":"+"\""+c_serial_no+"\",";
	       			table_json2+="\"c_operate\":"+"\""+c_operate+"\"},";
	       		}
	       	}
	    	if(table_json2.length>2){
	       		table_json2=table_json2.substr(0,table_json2.length-1);
	     	}
	       		table_json2+="]";
	       	    var tableArr1=eval("("+table_json1+")");
	    	    var tableArr2=eval("("+table_json2+")");
	    		field. intoPersonnelList =tableArr1;
	    		field. carryFacilityList =tableArr2;
// 	    		layer.alert(JSON.stringify(field));
	    		
  		var submit_flag=$("#submit_flag").val();
	    		if(submit_flag!="false"){
	    			layer.confirm('您已经提交过这个表单了，再次提交吗？', {
		    			  btn: ['确定','取消'] //按钮
		    			}, function(){
		    	 			$("#submit_flag").val("true");
			    			$.ajax({
								url:"${pageContext.request.contextPath }/mail_authEmailSave.action",
								type:"post",
								async:"true",
								data:	"JsonStr="+JSON.stringify(field),
								dataType:"json",
								success:function(result){
									$("#submit_flag").val("true");
									  layer.msg("增加成功", {icon: 6},function () {
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
								url:"${pageContext.request.contextPath }/mail_authEmailSave.action",
								type:"post",
								async:"true",
								data:	"JsonStr="+JSON.stringify(field),
								dataType:"json",
								success:function(result){
								$("#submit_flag").val("true");
							      layer.msg("增加成功", {icon: 6},function () {
				 		                // 获得frame索引
				 		                var index = parent.layer.getFrameIndex(window.name);
				 		                //关闭当前frame
				 		                parent.layer.close(index);
				 		               window.parent.location.reload();
				 		            });
				  				  }
		  						  });
			    		}
	    	 
		          });
       
       form.on('select(cityNames)', function(data){//设置拦截器cityNames 改变或者点击事件
 		  var popName=data.value;
//  			  alert (popName);
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
// 		   				 alert(append);
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
			layui.form.render("select");    
		  }
		  });	
	  
	}); 
       
       
    	  });
    <!--  use结束 -->
    
    var auth_id="${param.auth_id}";
    var email;
    $.ajax({
		  url:"${pageContext.request.contextPath}/mail_getAuthorizationEmail.action",
		  type:"post",
		  data:"auth_id="+auth_id,
		  dataType:"json",
		  success:function(result){
//			layer.alert(JSON.stringify(result[0]));
			  	email=result[0];
			  	$().val();
			  	$("#auth_id").val(email.auth_id);
				$("#case_id").val(email.case_id);
				$("#start_time").val(email.start_time);
				$("#end_time").val(email.end_time);
				var reg = new RegExp("###","g");//g,表示全部替换。
				$("#addressee_email").val(email.addressee_email.replace(reg,';'));
				$("#cc_addressee_email").val(email.cc_addressee_email.replace(reg,';'));
				$("#addressee_email").attr("disabled","disabled");
				$("#addressee_email").addClass("layui-disabled");
				$("#cabinet").val(email.cabinet);
				$("#supplier_name").val(email.supplier_name);
				$("#name").val(email.name);
	        	 $("#op_type").val(email.op_type);
	        	 $("#email").val(email.email);
	        	 $("#remarks").val(email.remarks);
	        	 $("#create_time").val(email.create_time);
	        	 if(email.au_status=='D'){
	        		 $("#add").addClass("layui-btn-disabled");
	        		 $("#add").attr('disabled',true);
	        		 $("#add").attr('title','该单已经删除，无法编辑发送');
	        	 }
// 	        	 拼接人员
	        	 if(email.intoPersonnelList==null||email.intoPersonnelList==""||email.intoPersonnelList.length==0){
	        			var htmlList="<tr>";
	            		htmlList+=  "<td  class='text3'> <input  type='checkbox'  /> </td>";
	            		htmlList+= "<td class='text3' name='p_name'><input type='text'  value= '' ></td>";
	            		htmlList+= "<td class='text3' name='p_id'><input type='text'  value='''  ></td>";	
	            		htmlList+="<td class=''text3'' > " ;
	            		htmlList+=" <select     class='elem1'   lay-ignore  >";
	            		var companyArr=new Array("Client","Sales","Per-Sales","Product","Account","NOC","CS-Admin","CS-PM","Others");
	            		  for(var  a=0;a<companyArr.length;a++){
	            				htmlList+="<option value="+companyArr[a]+">"+companyArr[a]+"</option>";
	            				}
	  						htmlList+=" </select></td>";
	  						htmlList+=" <td class='text3' name= '' ><input type='text'/></td> </tr>";
	  						$("#intoPersonnelList tr:last").after(htmlList);
		        	 }else{
	            	for(var inp in email.intoPersonnelList){
	            		var intoPersonnel=email.intoPersonnelList[inp];
	            		var htmlList="<tr>";
	            		htmlList+=  "<td  class='text3'> <input  type='checkbox'  /> </td>";
	            		htmlList+= "<td class=text3 name='p_name'><input type='text'  value="+intoPersonnel.p_name+" ></td>";
	            		htmlList+= "<td class=text3 name='p_id'><input type='text'  value="+intoPersonnel.p_id+"  ></td>";	
	            		htmlList+="<td class='text3' > " ;
	            		htmlList+=" <select     class='elem1'   lay-ignore  >";
	            		var companyArr=new Array("Client","Sales","Per-Sales","Product","Account","NOC","CS-Admin","CS-PM","Others");
	            		for(var  a=0;a<companyArr.length;a++){
	            			if(intoPersonnel.p_company==companyArr[a]){
	            				htmlList+="<option value="+intoPersonnel.p_company+">"+intoPersonnel.p_company+"</option>";
	            			} 
	            		}
            		  for(var  a=0;a<companyArr.length;a++){
            				if(intoPersonnel.p_company!=companyArr[a]){
            				htmlList+="<option value="+companyArr[a]+">"+companyArr[a]+"</option>";
            				} 
            				}
  						htmlList+=" </select></td>";
  						htmlList+=" <td class='text3' name='p_other_company' ><input type='text' value='"+intoPersonnel.p_other_company+"'/></td> </tr>";
  						$("#intoPersonnelList tr:last").after(htmlList);
            	} 
	        	}
// 				拼接设备	        	
	        	 if(email.carryFacilityList==null||email.carryFacilityList==""||email.intoPersonnelList.length==0){
	        			var htmlList="<tr> <td  class='text3'> <input  type='checkbox'  /> </td>";
	            		htmlList+= "<td class=text3 name='c_manufacturer'><input type='text'  value= '' ></td>";
	            		htmlList+= "<td class=text3 name='c_model'><input type='text'  value='''  ></td>";	
	            		htmlList+= "<td class=text3 name='c_serial_no'><input type='text'  value='''  ></td>";	
	            		htmlList+= "<td class=text3 name='c_operate'><input type='text'  value='''  ></td> </tr>";	
	  					$("#carryFacilityList tr:last").after(htmlList);
	        	 }else{
	         	for(var inp in email.carryFacilityList){
	         		var carryFacility=email.carryFacilityList[inp];
	         		var htmlList="<tr> <td  class='text3'> <input  type='checkbox'  /> </td>";
	        		htmlList+= "<td class=text3 name='c_manufacturer'><input type='text'  value="+carryFacility.c_manufacturer+" ></td>";
	        		htmlList+= "<td class=text3 name='c_model'><input type='text'  value="+carryFacility.c_model+"  ></td>";	
	        		htmlList+= "<td class=text3 name='c_serial_no'><input type='text'  value="+carryFacility.c_serial_no+"  ></td>";	
	        		htmlList+= "<td class=text3 name='c_operate'><input type='text'  value="+carryFacility.c_operate+"  ></td> </tr>";	
					$("#carryFacilityList tr:last").after(htmlList);
	         		} 
		        	}
	        	 layui.form.render("select");    
	        	 },
		  error:function(res){
			  alert("出错了："+JSON.stringify(res));
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
	  }
	  });

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
  				$("#pop_name").find("option[ value='"+ email.pop_name+"']").attr("selected", true);
  				$("#company").find("option[ value='"+ email.company+"']").attr("selected", true);
  				 layui.form.render("select");    
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
				       	 	$("#city_name").find("option[ value='"+email.city_name+"']").attr("selected", true);
				       		$("#cabinet").val(email.cabinet);
				       		 layui.form.render("select");    
				  }
				  });
	      }
	      
	     function default1() {
				layui.form.render("select");    
		}

      function test(){
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
        
           upload = layui.upload;
// 	      layui.use('upload', function(){
// 	       var $ = layui.jquery,upload = layui.upload;
	       var demoListView = $('#proImageList');
	       uploadListIns = upload.render({
	        elem: '#chooseFile', //选择文件的按钮
	        url: '${pageContext.request.contextPath }/mail_uploadImg.action', //后台处理文件长传的后台方法
	        data:{'serviceName':'外协订单供应商上传检验报告','tableName':'T_OUTSOURCE_ORDER','fileType':'图片'},//传到后台的方法
	        accept: 'file', 
// 	        size:'5120',
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
// 	         console.info(res);
// 	        alert(JSON.stringify(res));
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