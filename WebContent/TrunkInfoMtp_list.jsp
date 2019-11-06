<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--     <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  
  <head>
    <meta charset="UTF-8">
    <title>mtplist页面</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!--   <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" /> -->
    <link rel="stylesheet" href="./css/font.css">
    <link rel="stylesheet" href="./css/xadmin.css">
    <script type="text/javascript" src="./js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="./lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="./js/xadmin.js"></script>
    
    <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
    <!--[if lt IE 9]>
      <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
      <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	  <style type="text/css">
  	  .layui-table-cell {
	    height: auto; 
	    line-height: 15px;
	    margin:0 2px;
	    padding: 0 5px;
	    position: relative;
	    overflow: hidden;
	    text-overflow: ellipsis;
	    white-space: normal;
	    box-sizing: border-box;
	  }  
	  </style>
  </head>
  
 
  <body>
    <div class="x-nav">
      <span class="layui-breadcrumb">
        <a href="">MTP</a>
        <a>
          <cite>mtp管理</cite></a>
      </span>
      <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right" href="javascript:location.replace(location.href);" title="刷新">
        <i class="layui-icon" style="line-height:30px">ဂ</i></a>
    </div>
    
     <div class="x-body"> 
    
      <div class="layui-row">
        <form class="layui-form layui-col-md12 x-so">
            <input type="text"    name="trunk_id" id="trunk_id"   placeholder="trunk_id" autocomplete="off" class="layui-input">
             <input type="text"    name="a_end_full_name" id="a_end_full_name"   placeholder="a_end_full_name" autocomplete="off" class="layui-input" value="">
           <input type="text"    name="a_end_interface" id="a_end_interface"   placeholder="a_end_interface" autocomplete="off" class="layui-input">
           <input type="text"    name="z_end_full_name" id="z_end_full_name"   placeholder="z_end_full_name" autocomplete="off" class="layui-input">
           <input type="text"    name="z_end_interface" id="z_end_interface"   placeholder="z_end_interface" autocomplete="off" class="layui-input">
            <input type="text"    name="provider_circuit_num" id="provider_circuit_num"   placeholder="provider_circuit_num" autocomplete="off" class="layui-input">
           <input type="hidden"    name="fuzzyQuery" id="fuzzyQuery"   placeholder="fuzzyQuery" autocomplete="off" class="layui-input" value="fuzzyQuery">
            
             <button id="Sreach" class="layui-btn"   type="button" data-type="reload">搜索</button>
             
<!--         <span id="time"> </span> -->
			  <br>
<!-- 			   <input type="text"    name="end_full_name" id="end_full_name"   placeholder="end_full_name" autocomplete="off" class="layui-input">
			   <input type="text"    name="end_interface" id="end_interface"   placeholder="end_full_name" autocomplete="off" class="layui-input">
              <button type="button"  onclick="test()" value="测试">测试</button>
			  <input type="hidden"    name="return_status" id="return_status"   placeholder="return_status" autocomplete="off" class="layui-input"> -->
              
                </form>
         </div>

  

	        
      
    <table class="layui-hide" id="tableList"   lay-data="height:10px " lay-filter="tableList"></table>
     

<script type="text/html" id="toolbarDemo">
  <div class="layui-btn-container">
	<button class="layui-btn"  onclick="TrunkInfoMtpAdd()"><i class="layui-icon"></i>添加线路</button>
	<button class="layui-btn"  id="fuzzyQueryButton" onclick="fuzzyQuery()">模糊</button>


  </div>
</script>

<script type="text/html" id="barDemo">
<!--		    <a class="layui-btn layui-btn-xs" lay-event="detail">查看</a>		-->
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-xs" lay-event="del">删除</a>
</script>

    </div>    
    <script>
    
    window.onload=function(){
    }
		
    
    function set_select_checked(select, checkValue){  
        for (var i = 0; i < select.options.length; i++){  
            if (select.options[i].value == checkValue){  
                select.options[i].selected = true;  
                break;  
            }  
        }  
    }
    function fuzzyQuery(){
    	var fuzzyQuery=$("#fuzzyQuery").val();
//     	alert(fuzzyQuery);
    	if(fuzzyQuery=="fuzzyQuery"){
    		$("#fuzzyQuery").val("");
    		$("#provider_circuit_num").attr('placeholder','provider_circuit_num');
    	}else{
    		$("#fuzzyQuery").val("fuzzyQuery");
    		$("#provider_circuit_num").attr('placeholder','fuzzyQuery_provider_circuit_num');
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
       form.render();
       
       
       table.render({
    	    elem: '#tableList'
    	    ,url:'${pageContext.request.contextPath }/timtp_trunkInfoMtpList.action'
    	    ,toolbar: '#toolbarDemo'
    	    ,page:  { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
    	      layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局 布局顺序 true 都默认
    	      //,curr: 5 //设定初始在第 5 页
    	    ,limits:  [ 100,2000,10000]
  	        ,limit:100
    	    }  , id:'testReload'
    	    ,cols: [[
//     	         {type:'checkbox', fixed: 'left'}
    	    	 {field:'edit', title: '操作', width:177,align: 'center',toolbar:"#barDemo"}
    	     	,{field:'trunkId',  title: 'trunk_id',align: 'center'   ,width: 100   }
    	       ,{field:'AEndFullName',  title: 'a_end_full_name',align: 'center'  ,width:160  }
    	       ,{field:'AEndInterface',  title: 'a_end_interface',align: 'center'  ,width:150 }
    	       ,{field:'AEndInterfaceIp',  title: 'a_end_interface_ip',align: 'center'  ,width:160  }
    	       ,{field:'ZEndFullName',  title: 'z_end_full_name',align: 'center'   ,width:160  }
    	       ,{field:'ZEndInterface',  title: 'z_end_interface',align: 'center'   ,width:150  }
    	       ,{field:'ZEndInterfaceIp',  title: 'z_end_interface_ip',align: 'center'  ,width:160  }
    	       ,{field:'providerCircuitNum',  title: 'provider_circuit_num',align: 'center'  ,width:200  }
    	       ,{field:'internalCircuitNum',  title: 'internalCircuitNum',align: 'center'   ,width:200 }
    	       ,{field:'provider',  title: 'provider',align: 'center'   ,width:200 }
//     	       ,{field:'trunkName',  title: 'trunk_name',align: 'center'   ,width:200 }
    	       ,{field:'linkType',  title: 'link_type',align: 'center'   ,width:200  }

    	    ]],done:function(res, curr, count){  //res 接口返回的信息
  	    	  $("[data-field = 'status']").children().each(function(){//根据对应的字段值进行显示内容
  	    	    if($(this).text() == '0'){
  	    	      $(this).text("可用");
  	    	    }else if($(this).text() == '1') {
  	    	       $(this).text("不可用");
  	    	    } 
  	    	  });
    	    
  	    	} 
    	  }); 

       //执行一个laydate实例
       laydate.render({
         elem: '#start_time' ,//指定元素
         type: 'datetime',  
        format:'yyyy-MM-dd HH:mm:ss'
       });

       //执行一个laydate实例
       laydate.render({
         elem: '#end_time' ,//指定元素
         type: 'datetime',  
        	format:'yyyy-MM-dd HH:mm:ss'
       });
       
       var $ = layui.$, active = {
    		    reload: function(){
    		      //执行重载
    		      table.reload('testReload', {
    		        page: {
    		          curr: 1 //重新从第 1 页开始
    		        }
    		        ,where: {
    		        	trunk_id:   	$('#trunk_id').val()  ,
    		        	a_end_full_name:   	$('#a_end_full_name').val()  ,
    		        	a_end_interface:   	$('#a_end_interface').val()  ,
    		        	z_end_full_name:   	$('#z_end_full_name').val()  ,
    		        	z_end_interface:   	$('#z_end_interface').val()  ,
    		        	provider_circuit_num:   	$('#provider_circuit_num').val()  ,
    		        	fuzzyQuery :  $("#fuzzyQuery").val()
    		        	
    		        }
    		      });
    		    }
    		  };
    		  
    		  $('#Sreach').on('click', function(){
    		    var type = $(this).data('type');
    		    active[type] ? active[type].call(this) : '';
    		  });
    		  
    		  //监听行单击事件（单击事件为：rowDouble）
    		  table.on('row(tableList)', function(obj){
    		    var data = obj.data;
    		    
    		    layer.alert(JSON.stringify(data), {
    		      title: '当前行数据：'
    		    });
    		    
    		    //标注选中样式
    		    obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
    		  });
    		  
    		  function getCheckData(a){
  		        alert(JSON.stringify(a));

    		  }
    		  
    		//头工具栏事件
    		  table.on('toolbar(tableList)', function(obj){
  		        layer.alert(JSON.stringify(obj));
    		    var checkStatus = table.checkStatus(obj.config.id);
    		    switch(obj.event){
    		      case 'getCheckData':
    		        var data = checkStatus.data;
    		        layer.alert(JSON.stringify(data));
    		      break;
    		      case 'getCheckLength':
    		        var data = checkStatus.data;
    		        layer.msg('选中了：'+ data.length + ' 个');
    		      break;
    		      case 'isAll':
    		        layer.msg(checkStatus.isAll ? '全选': '未全选');
    		      break;
    		    };
    		  });
    		  
    		  
    		  <!--tableList开始 -->
    		  table.on('tool(tableList)', function(obj){
//     	            console.log(obj)
    	            var data = obj.data;
//     	            alert(JSON.stringify(data));
    	            if(obj.event === 'edit'){
	    	          	var content="./TrunkInfoMtp_edit.jsp?trunkId="+data.trunkId;
			    	 	var ind=    layer.open({ 
			    	 					type: 2,
			    	 			        fix: false, //不固定
			    	 			        shade:0.4,
			    	 			        type: 2,//弹出框类型
			    	 			        title: 'TrunkInfoMtp_edit',
			    	 			        maxmin: true,
			    	 			        shadeClose: false, //点击遮罩关闭层
			    	 			        area : ['90%' , '100%'],
			    	 			    	shift:1,
			    	 					anim:3,
			    	 			        content:  content ,//将结果页面放入layer弹出层中
			    	 			         success: function(layero, index){//这个是已经加载完成数据了的回调函数
			    	 						 layui.form.render( ); 	
			    	 			           }, yes: function(layero,index ){
			    	 	          		    console.log(layero);
			    	 	          		    layer.close(index); //如果设定了yes回调，需进行手工关闭
			    	 	          		  }
				 	     							 });  
     			 	layui.form.render('select'); 	
    	            } else if(obj.event === 'del'){
    	              layer.confirm('删除ID为:('+data.trunkId+ ')的线路吗?', function(index){
    	            	  $.ajax({
    	            		  url:"${pageContext.request.contextPath}/timtp_delTrunkInfoMtp.action",
    	            		  type:"post",
    	            		  data:"trunkId="+data.trunkId,
    	            		  dataType:"json",
    	            		  success:function(result){
    	            			  var i=result[0];
    	            			  if(i>0){
    	            				  layer.msg('删除caseID成功<br/>'+ data.user_id);
    	            			  }
    	            			  $(".layui-laypage-btn").click();//刷新页面
    	            		  },
    	            		  error:function(res){
    	            			  alert("出错了："+res);
    	            		  }
    	            	  });
    	                obj.del();
    	                layer.close(index);
    	              });
    	            } else if(obj.event === 'detail'){ 
    	               layer.alert('查看行数据:<br>'+ JSON.stringify(data));
    	        	    var str="caseId:"+data.case_id+"<br>";//用封装显示的数据
	  	            	str=str+"机房名称:"+data.pop_name+"<br>";
	  	            	str=str+"发送人邮箱:"+data.send_email+"<br>";
	  	            	str=str+"收件人邮箱:"+data.addressee_email+"<br>";
	  	            	str=str+"抄送人邮箱:"+data.cc_addressee_email+"<br>";
// 	  	            	str=str+"操作类型:"+data.ins_type+"<br>";
	  	            	str=str+"授权进入时间:"+data.start_time+"<br>";
	  	            	str=str+"授权离开时间:"+data.end_time+"<br>";
	  	            	str=str+"操作详细:"+data.op_type+"<br>";
	  	            	var aa=data.into_personnel;
	  	            	var bb=aa.replace('\n','<br>');
	  	            	str=str+"申请进入相关人员信息:"+"<br>"+data.into_personnel.replace('\n','<br>')+"<br>";
	  	            	str=str+"机柜信息:"+data.cabinet+"<br>";
	  	            	str=str+"携带设备:"+data.carry_facility+"<br>";
	  	            	
	  	               layer.alert(str,{offset:'',time:200000, icon:1} );      
      				}
    		  });
    		  <!--email_list结束 -->		
    		  });
    <!--  use结束 -->
    
  function TrunkInfoMtpAdd(){
      var content="./TrunkInfoMtp_add.jsp";
//       layer.alert(JSON.stringify(cases));
	var ind=     	 layer.open({ 
					type: 2,
			        fix: false, //不固定
			        shade:0.4,
			        type: 2,//弹出框类型
			        title: 'TrunkInfoMtp添加页面',
			        maxmin: true,
			        shadeClose: false, //点击遮罩关闭层
			        area : ['90%' , '90%'],
			    	shift:1,
					anim:3,
			        content:  content ,//将结果页面放入layer弹出层中
			         success: function(layero, index){//这个是已经加载完成数据了的回调函数
						 layui.form.render('select'); 	
			           }, yes: function(layero,index ){
	          		    console.log(layero);
	          		    layer.close(index); //如果设定了yes回调，需进行手工关闭
	          		  }
	     		 });  
  }
      
      function sleep(n) {
          var start = new Date().getTime();
          //  console.log('休眠前：' + start);
          while (true) {
              if (new Date().getTime() - start > n) {
                  break;
              }
          }
//           console.log('休眠后：' + new Date().getTime());
      }
      
      function test(){
    	  var data=new Object();
    	  if($('#end_full_name').val()==''||$('#end_interface').val()==''){
    		  alert("end_full_name或end_interface不能为空");
    		  return false;
    	  }
    	  data.end_full_name=$('#end_full_name').val();
    	  data.end_interface=$('#end_interface').val();
    	  $.ajax({
    		  url:"${pageContext.request.contextPath}/timtp_test.action",
    		  type:"post",
    		  data:"jsonStr="+JSON.stringify(data),
    		  dataType:"text",
    		  success:function(result){
    				  var alert1=layer.alert(result);
    					 layer.style(alert1, {
    						width:'1000px',
    						high:'600px'
    				     });
    		  },
    		  error:function(res){
    			  alert("出错了："+JSON.stringify(res));
    		  }
    	  });
    	  
    	  
    	  
    	  
      }
      
    </script>

  </body>
  
</html>