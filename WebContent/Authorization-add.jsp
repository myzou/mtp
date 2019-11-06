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
        <a href="">授权信息</a>
        <a>  <cite>授权申请</cite></a>
      </span>
        <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right" href="javascript:location.replace(location.href);" title="刷新">
        <i class="layui-icon" style="line-height:30px">ဂ</i></a>
    </div>

    <form class="layui-form" action="">
   		 <br>
	     <div class="layui-form-item"  style="text-align:center;">
<!-- 	    申请信息 -->
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
          </div>
          
             <div class="layui-form-item">
              <label   class="layui-form-label"  style="font-size: 12px;">
                  <span class="x-red">*</span>供应商名称：
              </label>
              <div class="layui-input-block" style="width: 48%">
               	<input  id="supplier_name"  name="supplier_name" lay-verify="supplier_name" autocomplete="off"   placeholder="供应商名称"  value="" class="layui-input">
              </div>
              
	          </div>
          
             <div class="layui-form-item">
	            <label for="start" class="layui-form-label">
	                  <span class="x-red">*</span>开始时间：
	             </label>
	            <div class="layui-input-inline">
	 				<input type="text" class="layui-input" id="start_time" name="start_time"   autocomplete="off"    lay-verify="required"  />            
	          	</div>
	          
	           <label for="start" class="layui-form-label"   >
	                  <span class="x-red">*</span>结束时间：
	              </label>
	            <div class="layui-input-inline">
	 				<input type="text" class="layui-input" id="end_time"  name="end_time"   autocomplete="off" lay-verify="required"    />          
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
               <input   id="name"   name="name" lay-verify="" autocomplete="off"   placeholder="请输入申请人姓名" class="layui-input"  lay-verify="required" value="${login_user }"  readonly="readonly" />          
              </div>
              
                <label   class="layui-form-label">
                  <span class="x-red">*</span>部门：
              </label>
              <div class="layui-input-inline" >
                  <select   name="company"  lay-search=""        lay-filter="dept"  lay-verify="required"    >
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
          <label for="cityName" class="layui-form-label" >
                  <span class="x-red">*</span>操作：
              </label>
              <div class="layui-input-block w66">
               <input   id="op_type"   name="op_type"  autocomplete="off"   placeholder="此次进入机房的操作" class="layui-input"  lay-verify="required"  />          
              </div>
    		</div>
    		
    	<div class="layui-form-item">  
            <label   class="layui-form-label">
                  <span class="x-red">*</span>机柜信息：
              </label>
              <div class="layui-input-block w66">
                  <input type="text" id="cabinet" name="cabinet" required=""   autocomplete="off" class="layui-input"  lay-verify="required" >
              </div>
          </div>
          
    		
    	 <div class="layui-form-item">
		    <label class="layui-form-label">
		    	<span class="x-red">*</span>Email:</label>
		    <div class="layui-input-block w66"  >
		      <input type="text"   id="email"   name="email" lay-verify="required" autocomplete="off" 
		      placeholder="请输入申请人Email信息" class="layui-input"  lay-verify="required"   />          
		    </div>
		  </div>
		  
          
             <div class="layui-form-item">
              <label  class="layui-form-label">
                   备注：
              </label>
              <div class="layui-input-block w66"  >
               <input   id="remarks"   name="remarks" lay-verify="" autocomplete="off"    class="layui-input">
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
		      <tr>
		        <td  class="text3"> <input  type="checkbox"  />         </td>
		        <td class="text3" name="p_name"><input type="text"  value=""/></td>
		        <td class="text3" name="p_id"><input type="text"  value=""/></td>
		        <td class="text3"  > 
		        <select   name=""     class="elem1"   lay-ignore  >
						<option value="Client">Client</option>
						<option value="Sales">Sales</option>
						<option value="Per-Sales">Per-Sales</option>
						<option value="Product">Product</option>
						<option value="Account">Account</option>
						<option value="NOC">NOC</option>
						<option value="CS-Admin">CS-Admin</option>
						<option value="CS-PM">CS-PM</option>
						<option value="Others">Others</option>
                  </select></td>
		        <td class="text3" name="p_other_company"><input type="text"/></td>
		      </tr>
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
		      <tr>
		      <td  class="text3"> <input  type="checkbox"/></td>
		        <td class="text3"  name="c_manufacturer"><input type="text"  value=""/></td>
		        <td class="text3" name="c_model"><input type="text" value=""/></td>
		        <td class="text3" name="c_serial_no"><input type="text"/></td>
		        <td class="text3" name="c_operate"> 
		        <select   name=""     class="elem1"   lay-ignore  >
		        		<option value="测试工具">测试工具</option>
		        		<option value="车辆">车辆</option>
						<option value="上架">上架</option>
						<option value="下架">下架</option>
						<option value="其他">其他</option>
                  </select
		        </td>
		      </tr>
		    </tbody>
		  </table>
    
  <div class="layui-form-item">
    <div class="layui-input-block">
      <button class="layui-btn" lay-submit=""   type="button" lay-filter="add" >立即提交</button>
<!--       <button type="reset" class="layui-btn layui-btn-primary">重置</button> -->
    </div>
  </div>
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
//    			var index =select.selectedIndex;
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
// 	 		alert(tableArr1);
	    	
	    	var table_json2="[";
	       	var trs= document.getElementById("carryFacilityList").getElementsByTagName("tr");
	       	for(var i=1;i<trs.length;i++){
	       		var tds=trs[i].getElementsByTagName('td');
	       		var c_manufacturer=tds[1].getElementsByTagName('input')[0].value;
	   			var c_model=tds[2].getElementsByTagName('input')[0].value;
	   			var c_operate=document.getElementById('carryFacilityList').getElementsByTagName('tr')[i].getElementsByTagName('td')[4].getElementsByTagName('select')[0].value;

	   			var c_serial_no=tds[3].getElementsByTagName('input')[0].value;
// 	   			var c_operate=tds[4].getElementsByTagName('input')[0].value;
	       		if(c_model!=null&&c_model!=""){
	       			table_json2+="{\"c_manufacturer\":"+"\""+c_manufacturer+"\",";
	       			table_json2+="\"c_model\":"+"\""+c_model+"\",";
	       			table_json2+="\"c_serial_no\":"+"\""+c_serial_no+"\",";
	       			table_json2+="\"c_operate\":"+"\""+c_operate+"\"},";
	       		}
	//        		document.getElementById('intoPersonnelList').getElementsByTagName('tr')[1].getElementsByTagName('td')[1].getElementsByTagName('input')[0].value
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
								url:"${pageContext.request.contextPath }/mail_authorizationAdd.action",
								type:"post",
								async:"true",
								data:	"JsonStr="+JSON.stringify(field),
								dataType:"json",
								success:function(result){
									$("#submit_flag").val("true");
								layer.msg("增加成功", {icon: 6},function () {
				  				  });
				  				  }
		  			  });
		    			}, function(){
		    			});
			    		}else{
			     			$("#submit_flag").val("true");
			    			$.ajax({
								url:"${pageContext.request.contextPath }/mail_authorizationAdd.action",
								type:"post",
								async:"true",
								data:	"JsonStr="+JSON.stringify(field),
								dataType:"json",
								success:function(result){
								$("#submit_flag").val("true");
								layer.msg("增加成功", {icon: 6},function () {
				  				  });
				  				  }
		  						  });
			    		}
	    
	    	
		            //发异步，把数据提交给php
		 /*            layer.msg("增加成功", {icon: 6},function () {
		                // 获得frame索引
		                var index = parent.layer.getFrameIndex(window.name);
		                //关闭当前frame
		                parent.layer.close(index);
		               window.parent.location.reload();
		            });
		            return false; */
       		 
       		
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
				$("#supplier_name").val(result[0].supplier_name);
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
				
//				alert("addressee_email:"+addressee_email);
//				alert("cc_addressee_email:"+cc_addressee_email);
				
			
			layui.form.render("select");    
		  }
		  });	
	  
	}); 
       
       
    	  });
    <!--  use结束 -->
    

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
//   				$("#pop_name").find("option[ value='"+"luke测试机房"+"']").attr("selected", true);
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
// 				       	 	$("#city_name").find("option[ value='"+"luke"+"']").attr("selected", true);
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

  </body>
  
</html>