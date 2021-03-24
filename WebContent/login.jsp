<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--     <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  
  <head>
	<meta charset="UTF-8">
	<title>MTP login</title>
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
     <script type="text/javascript" src="./js/Base64.js"></script>

    <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
    <!--[if lt IE 9]>
      <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
      <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

  </head>
  
 
<body class="login-bg">
    
    <div class="login layui-anim layui-anim-up">
        <div class="message">用户登录</div>
        <div id="darkbannerwrap"></div>
        
           <form method="post" class="layui-form"  action="${pageContext.request.contextPath }/login_login.action"  >
            <input  id="username"  name="username" placeholder="用户名"  value="${username }"  type="text" lay-verify="required" class="layui-input" >
            <hr class="hr15">
            <input id="password" name="password" lay-verify="required" placeholder="密码"  type="password" class="layui-input">
            <input id="referrer"  name="referrer" id ="referrer"  placeholder=""  type="hidden" class="layui-input">
            
            <hr class="hr15">
            <input value="登录"   style="width:100%;" type="submit" onclick="check()">
            <p style="font-family:Tahoma; color:red; font-size: 10px;">Company Confidential II</p>

            <hr class="hr20" >
            <span  style="color: red;"  >${tip }</span>
        </form>
    </div>

    
    <script>
    var referrer='${param.referrer}';
    $("#referrer").val(referrer);
/*     alert("document.referrer:"+document.referrer+"\r\n"+
    		"referrer： "+referrer
    ); */
    
    function check(){
        var password= $("#password").val();
        var addStr="addpassword";
        $("#password").val(password+addStr);
//     	alert($("#password").val());
    	return false;
    }
    
        $(function  () {
            layui.use('form', function(){
              var form = layui.form;
              //监听提交
            });
        })

        
    </script>

</body>
</html>