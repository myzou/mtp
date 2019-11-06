<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--     <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  
  <head>
    <meta charset="UTF-8">
    <title> user_list 用户list页面</title>
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

  </head>
  
 
  <body>
    <div class="x-nav">
      <span class="layui-breadcrumb">
        <a href="">权限管理</a>
        <a>
          <cite>用户管理</cite></a>
      </span>
      <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right" href="javascript:location.replace(location.href);" title="刷新">
        <i class="layui-icon" style="line-height:30px">ဂ</i></a>
    </div>
    
     <div class="x-body"> 
    
      <div class="layui-row">
        <form class="layui-form layui-col-md12 x-so">
            <input type="text"    name="user_name" id="user_name"   placeholder="用户名称" autocomplete="off" class="layui-input">
             <button id="Sreach" class="layui-btn"   type="button" data-type="reload">搜索</button>
<!--         <span id="time"> </span> -->
<!--               <button type="button"  onclick="test()" value="测试">测试</button> -->
                </form>
         </div>

  

	        
      
    <table class="layui-hide" id="tableList"   lay-data="height:10px " lay-filter="tableList"></table>
     
<script type="text/html" id="toolbarDemo">
  <div class="layui-btn-container">
	<!--<button class="layui-btn layui-btn-danger" onclick="delAll()"><i class="layui-icon"></i>批量删除</button>-->
	<button class="layui-btn"  onclick="userAdd()"><i class="layui-icon"></i>添加用户</button>
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
    	    ,url:'${pageContext.request.contextPath }/user_sysUserList.action'
    	    ,toolbar: '#toolbarDemo'
    	    ,page:  { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
    	      layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局 布局顺序 true 都默认
    	      //,curr: 5 //设定初始在第 5 页
    	    }  , id:'testReload'
    	    ,cols: [[
// 			  {type: 'checkbox', fixed: 'left'},
				{field:'user_id',  title: '用户id',align: 'center'  /* ,width: 80  */ }
    	       ,{field:'user_name',  title: '用户名称',align: 'center'  /* ,width: 80  */ }
//     	       ,{field:'password',  title: '密码',align: 'center'  /*  ,width:80 */  }
    	      ,{field:'status', title: '状态', width:177,align: 'center',toolbar:"#barDemo"}
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
    		        	user_name:   	$('#user_name').val()  
    		        }
    		      });
    		    }
    		  };
    		  
    		  $('#Sreach').on('click', function(){
    		    var type = $(this).data('type');
    		    active[type] ? active[type].call(this) : '';
    		  });
    		  
    		  <!--tableList开始 -->
    		  table.on('tool(tableList)', function(obj){
//     	            console.log(obj)
    	            var data = obj.data;
//     	            alert(JSON.stringify(data));
    	            if(obj.event === 'edit'){
    	          var content="./user_edit.jsp?user_id="+data.user_id;
		    	 	var ind=    layer.open({ 
			    	 					type: 2,
			    	 			        fix: false, //不固定
			    	 			        shade:0.4,
			    	 			        type: 2,//弹出框类型
			    	 			        title: '用户编辑页面',
			    	 			        maxmin: true,
			    	 			        shadeClose: false, //点击遮罩关闭层
			    	 			        area : ['600px' , '400px'],
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
    	              layer.confirm('删除ID为:('+data.user_id+ ')的用户吗?', function(index){
    	            	  $.ajax({
    	            		  url:"${pageContext.request.contextPath}/user_delSysUserByUserId.action",
    	            		  type:"post",
    	            		  data:"user_id="+data.user_id,
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
    
  function userAdd(){
      var content="./user_add.jsp";
//       layer.alert(JSON.stringify(cases));
	var ind=     	 layer.open({ 
					type: 2,
			        fix: false, //不固定
			        shade:0.4,
			        type: 2,//弹出框类型
			        title: '用户添加页面',
			        maxmin: true,
			        shadeClose: false, //点击遮罩关闭层
			        area : ['600px' , '400px'],
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
    </script>

  </body>
  
</html>