<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html >
<head>
<%@ include file="/inc/inc.jsp"%>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width; initial-scale=0.8;  user-scalable=0;" />
    <title>无标题文档</title>
     <link href="${_base}/theme/baas/css/bootstrap.css" rel="stylesheet" type="text/css">
     <link href="${_base}/theme/baas/css/font-awesome.css" rel="stylesheet" type="text/css">
     <link href="${_base}/theme/baas/css/frame.css" rel="stylesheet" type="text/css">
     <link href="${_base}/theme/baas/css/global.css" rel="stylesheet" type="text/css">
     <link href="${_base}/theme/baas/css/modular.css" rel="stylesheet" type="text/css">
     <script type="text/javascript" src="${_base}/theme/baas/js/jquery-1.11.1.min.js" ></script>
     <script type="text/javascript" src="${_base}/theme/baas/js/bootstrap.js" ></script>
     <script type="text/javascript" src="${_base}/theme/baas/js/frame.js" ></script>
     <script type="text/javascript" src="${_base}/theme/baas/js/comp.js" ></script>
</head>

<body>
  <%@ include file="/inc/head-user.jsp"%>
  <%@ include file="/inc/head-logonav.jsp"%>
  <div class="wrapper">
  <div class="Retrieve-password">
    
         <div class="Retrieve-steps">
         <div class="Retrieve-steps-round">
  <div class="finished"><!--蓝色圆圈带蓝线 finished-->
    <div class="wrap">
      <div class="round"><i class="icon-user"></i></div>
      <div class="bar"></div>
    </div>
    <label>1.身份验证</label>
  </div>
  <div class="todo"><!--圆圈蓝色 current-->
    <div class="wrap">
      <div class="round"><i class="icon-pencil"></i></div>
      <div class="bar"></div>
    </div>
    <label>2.绑定邮箱</label>
  </div>
  <div class="todo"><!--圆圈灰色 todo-->
    <div class="wrap">
      <div class="round"><i class=" icon-ok"></i></div>
      
    </div>
    <label>3.完成</label>
  </div>

</div>
 </div><!--步骤结束-->
         
     <!--表单验证-->
    <div class="Retrieve-cnt">
            <ul>
          <li class="user">
          <p class="word">已验证手机</p>
         <p>150****1010</p> 
          <p class="tong"><a href="#">通过已验证邮箱验证</a></p>
         </li>
         <li class="user">
          <p class="word">图形验证码</p>
          <p><input type="text" class="int-medium" placeholder=""></p>
          <p><img src="${_base}/theme/baas/images/ret-yzm.png"></p>
          <p><a href="#">看不清?换一换</a></p>
         </li>
         <li class="user">
          <p class="word">短信校验码</p>
          <p><input type="text" class="int-medium" placeholder=""></p>
          <p class="huoqu"><a href="#">获取短信校验码</a></p>
         </li>
         
         <li><input type="button" class="Submit-btn" value="提  交" onclick="location.href='邮箱绑定.html';"></li>
       
          </ul>
        </div>
    
    
    
    </div>
   
  </div>
  <%@ include file="/inc/foot.jsp"%>
</body>
</html>
