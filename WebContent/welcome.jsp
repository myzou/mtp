<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--     <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  
  <head>
	<meta charset="UTF-8">
	<title>MTP</title>
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
    <div class="x-body layui-anim layui-anim-up">
        <blockquote class="layui-elem-quote">欢迎您：
            <span class="x-red" >${login_user }</span>！当前时间: <span id="time"> </span></blockquote> 
 <!--        <fieldset class="layui-elem-field">
            <legend>数据统计</legend>
            <div class="layui-field-box">
                <div class="layui-col-md12">
                    <div class="layui-card">
                        <div class="layui-card-body">
                            <div class="layui-carousel x-admin-carousel x-admin-backlog" lay-anim="" lay-indicator="inside" lay-arrow="none" style="width: 100%; height: 90px;">
                                <div carousel-item="">
                                    <ul class="layui-row layui-col-space10 layui-this">
                                        <li class="layui-col-xs2" onclick="fortune()" >
                                            <a href="javascript:;" class="x-admin-backlog-body">
                                                <h3  >待处理case</h3>
                                                <p>  <cite id="fortune" >5</cite></p>
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </fieldset>
        <fieldset class="layui-elem-field">
            <legend>系统通知</legend>
            <div class="layui-field-box">
                <table class="layui-table" lay-skin="line">
                    <tbody>
                        <tr>
                            <td >
                                <a class="x-a" href="#" >公告1</a>
                            </td>
                        </tr>
                        <tr>
                            <td >
                                <a class="x-a" href="#" >公告2</a>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </fieldset>
        <fieldset class="layui-elem-field">
            <legend>系统信息</legend>
            <div class="layui-field-box">
                <table class="layui-table">
                    <tbody>
                        <tr>
                            <th>测试版本1</th>
                            <td>1.0.180420</td></tr>
                        <tr>
                            <th>服务器地址</th>
                            <td>127.0.0.1</td></tr>
                        <tr>
                            <th>操作系统</th>
                            <td>Win7  64</td></tr>
                        <tr>
                            <th>MYSQL版本</th>
                            <td>5.6</td></tr>
                        <tr>
                            <th>前端框架</th>
                            <td>layui</td></tr>
                        <tr>
                            <th>后端框架</th>
                            <td>spring/struts2/hibernate</td></tr>
                    </tbody>
                </table>
            </div>
        </fieldset>
        <fieldset class="layui-elem-field">
            <legend>开发团队</legend>
            <div class="layui-field-box">
                <table class="layui-table">
                    <tbody>
                        <tr>
                            <th>版权所有</th>
                            <td>开发者所有
                                <a href="http://www.baidu.com/" class='x-a' target="_blank">访问官网</a></td>
                        </tr>
                        <tr>
                            <th>开发者</th>
                            <td>开发人员(123456789@qq.com)</td></tr>
                    </tbody>
                </table>
            </div>
        </fieldset>
        <blockquote class="layui-elem-quote layui-quote-nm">感谢layui,百度Echarts,jquery,本系统由x-admin提供技术支持。</blockquote> -->
    </div>
    <script type="text/javascript">
    window.onload=function(){
        setInterval(function(){
        	fnDate();
            }  ,1000)
    }
    function fortune(){
    	var fortune=document.getElementById("fortune");
    	fortune.innerHTML=Number(fortune.innerHTML)+1;
    }
  //js 获取当前时间
    function fnDate(){
    	var timeDiv=document.getElementById("time");
        var dateTime = new Date();
        var year=dateTime.getFullYear();
        var month=dateTime.getMonth()+1;
        var ri=dateTime.getDate();
        var day;
        if(dateTime.getDay()==6){
        day="六";}
        if(dateTime.getDay()==1){
        day="一";}
        if(dateTime.getDay()==2){
        day="二";}
        if(dateTime.getDay()==3){
        day="三";}
        if(dateTime.getDay()==4){
        day="四";}
        if(dateTime.getDay()==5){
        day="五";}
        if(dateTime.getDay()==0){ day="日";}
        var hours = dateTime.getHours();
        var minutes = dateTime.getMinutes();
        var seconds = dateTime.getSeconds();
        var date=fnW(year)+"年"+fnW(month)+"月"+fnW(ri)+"日  "+fnW(hours) + ":" + fnW(minutes) + ":" + fnW(seconds)+ "  星期"+day;
        timeDiv.innerHTML=date;
   	 }
	  //补位 当某个字段不是两位数时补0
  	  function fnW(str){
	    var num;
	    str>=10?num=str:num="0"+str;
	    return num;
	    } 

    </script>
    </body>
</html>