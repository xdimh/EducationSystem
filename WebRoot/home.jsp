<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=8">
<link type="text/css" rel="stylesheet" href="tools/bootstrap/css/bootstrap.css" />
<link type="text/css" rel="stylesheet" href="tools/bootstrap/css/bootstrap-responsive.css" />
<link type="text/css" rel="stylesheet" href="tools/chosen/chosen.css" />
<link type="text/css" rel="stylesheet" href="tools/tip/tipsy.css" />
<link type="text/css" rel="stylesheet" href="css/smoothness/jquery-ui-1.10.0.custom.css" />
<link type="text/css" rel="stylesheet" href="tools/dataTables/media/css/jquery.dataTables.css" />
<link type="text/css" rel="stylesheet" href="tools/dataTables/media/css/jquery.dataTables_themeroller.css" />
<link type="text/css" rel="stylesheet" href="css/home.css" />
<script type="text/javascript" src="js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.10.0.custom.js"></script>
<script type="text/javascript" src="tools/bootstrap/js/bootstrap.js"></script>
<title>首页</title>
</head>

<body>
	<div id="header">
		<ul id="account_info" class="pull-right">
			<li> 
				<img src="images/avatar.png"/>
			</li>
			<li class="setting">
				欢&nbsp;迎!&nbsp;
				<span style="color:#f00;"><s:property value="#session.loginInfo.username" default="test"/></span>
			</li>
			<li class="logout" title="Disconnect">退&nbsp;出</li>
		</ul>
	</div>
	<div id="left_menu">
		<ul id="main_menu" class="main_menu">
			<li class="select">
				<a href="javascript:void(0)" id="condition_search">条件查询</a>
			</li>
			<li >
				<a href="#">添加学校</a>
				<ul class="sub-menu" style="display:none;">
					<li>
						<a href="javascript:void(0)" id="online_add">在线添加</a>
						<div class="arr">
							<span></span>
						</div>
					</li>
					<li>
						<a href="javascript:void(0)" id="upload_file">文件上传</a>
					</li>
				</ul>	
			</li>
			<li>
				<a href="javascript:void(0)" id="personal_set">个人设置</a>	
			</li>
		</ul>
	</div>
	<div id="newcontent">
		<div class=inner>
			<div class="row-fluid">
				<div class="span12 clearfix">
					<div class="logo">
						<span>沁宝教育咨询公司</span>
					</div>
					<ul class="shutcuts">
						<li>
							<a href="#" title="back_to_home">
								<img src="images/home.png" />
								<span>首页</span>
							</a>
						</li>
						<li>
							<a href="#" title="website graph">
								<img src="images/graph.png" />
								<span>图表</span>
							</a>
						</li>
							
						<li>
							<a href="#" title="setting">
								<img src="images/setting.png" />
								<span>设置</span>
							</a>
						</li>
						<li>
							<a href="#" title="messages">
								<img src="images/mail.png" />
								<span>消息</span>
							</a>
							<div class="notification">10</div>
						</li>
					</ul>
				</div>
			</div>
		<div class="row-fluid" style="background-color:white">
			<div class="span12 clearfix well">
				
			</div>
		</div>
	</div>
	<script type="text/javascript" src="js/main.js"></script>
	<script type="text/javascript" src="tools/BlockUI/jquery.blockUI.js"></script>
	<script type="text/javascript" src="tools/chosen/chosen.jquery.js"></script>
	<script type="text/javascript" src="js/jquery.json-2.4.js"></script>
	<script type="text/javascript" src="tools/dataTables/media/js/jquery.dataTables.js"></script>
	<script type="text/javascript" src="tools/tip/jquery.tipsy.js"></script>
	<script type="text/javascript" src="js/online_add.js"></script>
</body>
</html>
