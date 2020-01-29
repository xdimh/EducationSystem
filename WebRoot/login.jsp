<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=8">
<link type="text/css" rel="stylesheet" href="css/login.css" />
<link type="text/css" rel="stylesheet" href="tools/bootstrap/css/bootstrap.css" />
<link type="text/css" rel="stylesheet" href="tools/tip/tipsy.css" />
<title>高考志愿填报咨询系统</title>
</head>

<body>
	<div id="header-bar">
	</div>
	<div id="login">
		<s:if test="#sInvalidateMsg!=null" >
			<div id="sInvalidateMsg" class="error-msg-tip"><s:property value="#sInvalidateMsg"/></div>
		</s:if>
		<div class="ribbon"></div>
		<div class="inner">
			<div class="logo">
				<span>沁宝教育咨询公司</span>
			</div>
			<div class="formLogin" >
				<form name="formLogin" id="formLogin" method="post" style="margin-bottom:0px;" >
					<div class="tip">
						<input name="username" type="text" id="username_id" placeholder="请输入用户名" autocomplete="off" maxlength="25">
					</div>
					<div class="tip">
						<input name="password" type="password" id="password" placeholder="请输入密码" maxlength="25">
					</div>
					<div class="loginButton">
						<div class="save-password">
							<label class="checkbox">
    						  <input type="checkbox">记住我？
   							 </label>
						</div>
						<div class="btn-group pull-right">
						  <button type="button" id="login_btn" class="btn">登录</button>
						  <button type="button" id="forget_btn" class="btn">忘记密码</button>
						</div>
					</div>
					<div class="clear"></div>
				</form>
			</div>
			<div class="row-fluid">
				<div id="register" class="span2">
							<a href="javascript:void(0)">用户注册</a>
				</div>
			</div>
		</div>
		<div class="shadow">	
		</div>
	</div>
	
	<div id="versionBar">
		<div class="copyright"> © Copyright 2012  All Rights Reserved</div>
	</div>
	<div id="register-form" style="display:none">
		<form id="user-regist" method="post">
					<div class="controls postition_reltive">
						<div class="input-prepend" style="margin-bottom:0px;">
						  <span class="add-on"><i class="icon-user" style="margin-top:3px;"></i></span>
						  <input class="span3" id="regist_user" type="text" placeholder="用户名">
						  <i class="mesg_icon"></i>
						</div>
						<div class="error_mesg">用户密码不能为空</div>
					 </div>
					 
					 <div class="controls postition_reltive">
						<div class="input-prepend" style="margin-bottom:0px;">
						  <span class="add-on"><i class="icon-lock" style="margin-top:3px;"></i></span>
						  <input class="span3" id="regist_passwd" type="password" placeholder="密码">
						  <i class="mesg_icon"></i>
						</div>
						<div class="error_mesg">用户密码不能为空</div>
					 </div>
					 <div class="controls postition_reltive">
						<div class="input-prepend" style="margin-bottom:0px;">
						  <span class="add-on"><i class="icon-lock" style="margin-top:3px;"></i></span>
						  <input class="span3" id="regist_repasswd" type="password" placeholder="重复密码">
						  <i class="mesg_icon"></i>
						</div>
						<div class="error_mesg">用户密码不能为空</div>
					 </div>
					 <div class="controls postition_reltive">
						<div class="input-prepend" style="margin-bottom:0px;">
						  <span class="add-on"><i class="icon-envelope" style="margin-top:3px;"></i></span>
						  <input class="span3" id="regist_email" type="text" placeholder="邮箱">
						  <i class="mesg_icon"></i>
						</div>
						<div class="error_mesg">用户密码不能为空</div>
					 </div>
					 <div class="clear"></div>
					 <button type="button" class="btn" id="backLogin"><i class="icon-backward" style="margin-right:3px;"></i>返回</button>
					 <button type="button" id="regist_btn" class="btn btn-danger white "><i class="icon-hand-up icon-white"></i> 注册 </button>
				</form>
	</div>
	<script type="text/javascript" src="js/jquery-1.8.2.js"></script>
	<script type="text/javascript" src="tools/tip/jquery.tipsy.js"></script>
	<script type="text/javascript" src="tools/bootstrap/js/bootstrap.js"></script>
	<script type="text/javascript" src="tools/jquery.jrumble.1.3/jquery.jrumble.1.3.js"></script>
	<script type="text/javascript" src="tools/BlockUI/jquery.blockUI.js"></script>
	<script type="text/javascript" src="js/custom.tip.js"></script>
	
</body>
</html>
