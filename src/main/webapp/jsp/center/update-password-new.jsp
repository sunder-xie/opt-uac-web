<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="zh-cn">
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
  <div class="finished"><!--圆圈蓝色 current-->
    <div class="wrap">
      <div class="round"><i class="icon-pencil"></i></div>
      <div class="bar"></div>
    </div>
    <label>2.设置新密码</label>
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
          <p class="word">新密码</p>
          <p><input type="password"class="int-medium" placeholder=""></p>
          <div class="Set-password">
          <p class="low">
          <span class="f00"></span>
          <span>低</span>
          </p>
           <p class="in">
          <span class="eb6100"></span>
          <span>中</span>
          </p>
          <p class="gao">
          <span class="green"></span>
          <span>高</span>
          </p>
          
          </div>
         
          </li>
          
          <li class="user">
          <p class="word">确认密码</p>
          <p><input type="password" class="int-medium" placeholder=""><span class="regsiter-note"><i class="icon-caret-left"></i><img src="${_base}/theme/baas/images/correct.png">密码必须由字母和数字/符号组成，不能低于6个字符</span></p>
          </li>
       
         
         <li><input type="button" class="Submit-btn" value="提  交" onclick="location.href='账户安全-修改密码-完成.html';" ></li>
       
          </ul>
        
        
        </div>
    
    
    
    </div>
  </div>
  <%@ include file="/inc/foot.jsp"%>
</body>
</html>