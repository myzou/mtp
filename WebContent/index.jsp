<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--     <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  
  <head>
	<meta charset="UTF-8">
	<title>mtp</title>
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<!--     <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" /> -->
    <meta http-equiv="Cache-Control" content="no-siteapp" />

    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" href="./css/font.css">
	<link rel="stylesheet" href="./css/xadmin.css">
    <script type="text/javascript" src="./js/jquery-3.2.1.min.js"></script>
    <script src="./lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="./js/xadmin.js"></script>

    <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
    <!--[if lt IE 9]>
      <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
      <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

  </head>
  
 
<body>
    <!-- 顶部开始 -->
    <div class="container">
        <div class="logo"><a href="index.jsp">MTP系统</a></div>
        <div class="left_open">
            <i title="展开左侧栏" class="iconfont">&#xe699;</i>
        </div>
      <!--   <ul class="layui-nav left fast-add" lay-filter="">
          <li class="layui-nav-item">
            <a href="javascript:;">+新增</a>
            <dl class="layui-nav-child"> 二级菜单
              <dd><a onclick="x_admin_show('资讯','http://www.baidu.com')"><i class="iconfont">&#xe6a2;</i>资讯</a></dd>
              <dd><a onclick="x_admin_show('图片','http://www.baidu.com')"><i class="iconfont">&#xe6a8;</i>图片</a></dd>
               <dd><a onclick="x_admin_show('用户','http://www.baidu.com')"><i class="iconfont">&#xe6b8;</i>用户</a></dd>
            </dl>
          </li>
        </ul> -->

        <ul class="layui-nav right" lay-filter="">
            <li class="layui-nav-item">
                <p style="font-family:Tahoma; color:red; font-size: 10px;">Company Confidential II</p>
            </li>
            <li class="layui-nav-item">
                <a href="javascript:;"> ${login_user }</a>
                <dl class="layui-nav-child"> <!-- 二级菜单 -->
                    <!--               <dd><a onclick="x_admin_show('个人信息','http://www.baidu.com')">个人信息</a></dd> -->
                    <dd><a onclick="loginOut()">切换帐号</a></dd>
                    <dd><a onclick="loginOut()">退出</a></dd>
                </dl>
            </li>
            <!--           <li class="layui-nav-item to-index"><a >前台首页</a></li> -->
        </ul>
        
    </div>
    <!-- 顶部结束 -->
    <!-- 中部开始 -->
     <!-- 左侧菜单开始 -->
    <div class="left-nav">
      <div id="side-nav">
        <ul id="nav">
<!--         	权限管理start -->
        <!-- 	      <li>
                <a href="javascript:;">
                    <i class="iconfont">&#xe723;</i>
                    <cite>权限管理</cite>
                    <i class="iconfont nav_right">&#xe697;</i>
                </a>
                <ul class="sub-menu">
						<li><a _href="menu_list.jsp"> <i class="iconfont">&#xe6a7;</i>
								<cite>菜单管理</cite>
						</a></li>
						<li><a _href="role_list.jsp"> <i class="iconfont">&#xe6a7;</i>
								<cite>角色管理</cite>
						</a></li>
						<li><a _href="user_list.jsp"> <i class="iconfont">&#xe6a7;</i>
								<cite>用户管理</cite>
						</a></li>
					</ul>
            </li>
        	
        	权限管理ned
        	  email管理start
                <li>
                <a href="javascript:;">
                    <i class="iconfont">&#xe723;</i>
                    <cite>email管理</cite>
                    <i class="iconfont nav_right">&#xe697;</i>
                </a>
                <ul class="sub-menu">
						<li><a _href="email-list.jsp"> <i class="iconfont">&#xe6a7;</i>
								<cite>case列表</cite>
						</a></li>
						<li><a _href="Authorization-list.jsp"> <i class="iconfont">&#xe6a7;</i>
								<cite>授权信息审核</cite>
						</a></li>
						<li><a _href="email-updateKey.jsp"> <i class="iconfont">&#xe6a7;</i>
								<cite>口令修改</cite>
						</a></li>
					</ul>
            </li>
            
			email管理end
			授权信息填写start
                <li>
                <a href="javascript:;">
                    <i class="iconfont">&#xe723;</i>
                    <cite>授权信息</cite>
                    <i class="iconfont nav_right">&#xe697;</i>
                </a>
                <ul class="sub-menu">
                    <li>
                        <a _href="Authorization-add.jsp">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>授权申请</cite>
                        </a>
                        </li >
                </ul>
            </li> -->
<!-- 			授权信息填写end -->
        
        
        </ul>
      </div>
    </div>
    <!-- <div class="x-slide_left"></div> -->
    <!-- 左侧菜单结束 -->
    <!-- 右侧主体开始 -->
    <div class="page-content">
        <div class="layui-tab tab" lay-filter="xbs_tab" lay-allowclose="false">
          <ul class="layui-tab-title">
            <li class="home"><i class="layui-icon">&#xe68e;</i>我的桌面</li>
          </ul>
          <div class="layui-tab-content">
            <div class="layui-tab-item layui-show">
                <iframe src='./welcome.jsp' frameborder="0" scrolling="yes" class="x-iframe"></iframe>
            </div>
          </div>
        </div>
    </div>
    <div class="page-content-bg"></div>
    <!-- 右侧主体结束 -->
    <!-- 中部结束 -->
    <!-- 底部开始 -->
<!--     <div class="footer">
        <div class="copyright">测试：<a href="#"  >测试版本001</a></div>  
    </div> -->
    <!-- 底部结束 -->

</body>

<script type="text/javascript">
	
	
	function loginOut(){
		
/* 		alert ("aaa");
		 var arr = [ "one", "two", "three", "four"];     
		 $.each(arr, function(){     
		    var aa=this;
		    alert(this+"1>"+(aa=="two"));     
		    if(aa=="two"){
				 return false ;
		    }
		 }); 
		 return ; */
	 	$.ajax( {
		   	 url:"${pageContext.request.contextPath}/login_loginOut.action",
		       type : 'post',
		       dataType : 'json',
		       success : function(data) {
		    	 document.location.href='${pageContext.request.contextPath}/login.jsp'
		       }
		   });  
	}

  
  	$(function () { 
		var login_user ='${login_user }';
  		 layui.use(['form','element'], function() {
	        layer = layui.layer;
	    	 var   element = layui.element;
			$.ajax( {
				   	 url:"${pageContext.request.contextPath}/menu_loadMenuByUserName.action",
				       type : 'post',
				       dataType : 'json',
				       data:"login_user="+login_user,
				       success : function(data) {
				           var menu = ""; //定义变量存储
			          for(var i = 0;i<data.length;i++){
				             if(data[i].parent_m_id == 0){ //取出父元素的菜单，拼进页面
				                   menu +=  "<li ><a href='javascript:;'> <cite>"+data[i].menu_name+"</cite>   <i class='iconfont nav_right'>&#xe697;</i></a>"
				                   menu+=" <ul class='sub-menu'  >";
				                   for(var j = 0;j<data.length;j++){ //继续遍历这几条数据
				                       if(data[j].parent_m_id == data[i].m_id  ){ //取出这个父元素所对应的子元素
				                           menu +=    "<li><a   _href='"+data[j].menu_path+"'>";
				                           menu +=    "<i class='iconfont'>&#xe6a7;</i>";
				                           menu +=  "<cite>"+data[j].menu_name+"</cite>";
				                           menu +=        "</a></li>";
				                       }
				                   }
				                   menu +=    "</ul>";
					               menu +=    "</li>";
				               }
				           }  
			          $("#nav").html(menu);
			          element.render("nav");
				       }
				   });
	    }); 
		
	});  
 


</script>
</html>