// JavaScript Document

$(document).ready(
	function () {
		var $login,$registerLink,loaderStr = "请稍等！正在登录......",
			triggerMark = 0;
		//triggerMark 为了使检查用户名是否存在时以及邮箱是否已经注册过使用blockUI
		$(document).ajaxSend(
				function() {
					if(triggerMark == 0){
							$.blockUI({ css: { 
					            border: 'none', 
					            padding: '15px', 
					            backgroundColor: '#000', 
					            '-webkit-border-radius': '10px', 
					            '-moz-border-radius': '10px', 
					            opacity: .5, 
					            color: '#fff' 
								},message:'<h4><img src="./images/preloader.gif" />&nbsp;&nbsp;' + loaderStr + '<h4>' });
					}
				}
		).ajaxComplete(
				function () {
					$.unblockUI();
				}
		);
		
		$(".tip input").tipsy({trigger:'focus',gravity:'w',live:true});
		
		
		$("#register a").click(function(){
			alertHide();
			$("#login").find(".error-msg-tip").remove();
			$login= $("#formLogin").detach();
			$registerLink = $("#register").parent().detach();
			var $registerForm = $("#register-form form").clone();
			$($registerForm).hide().appendTo(".formLogin").fadeIn('slow');
			//$("#login").animate({marginTop:-160},500,function(){$(this).animate({marginTop:-158},200)});
			$("#login").animate({marginTop:-165},'fast');
			//switchRegistBtn();
			return false;
			}
	 	);
		//IE 360 最好不用动画
		$("button#backLogin").live('click',
			function() {
				alertHide();
				$(".formLogin form#user-regist").remove();
				
				//$login.hide().appendTo(".formLogin").fadeIn('slow');
				$login.appendTo(".formLogin");

				$registerLink.insertAfter(".formLogin");
			//	$("#login").animate({marginTop:-146},500,function(){$(this).animate({marginTop:-148},200)});
				$("#login").animate({marginTop:-148},'fast');
				//清空密码
				$("#username_id").add("#password").val("").focus();
				
			}
		);
		
		  function alertHide(){
				 $('#alertMessage').each(function(index) {	 
						$(this).animate({ opacity: 0,right: '30'}, 500,function(){ $(this).remove(); });	
				});	
	  }
	  
	  // Create Alert Message Box
	  function alertMessage(type,str){
				//Hidden All  Alert Message Before
				alertHide();
				// type is a success ,info, warning ,error
				var $alertbox = $('<div id="alertMessage" class="message '+type+'">');
				$alertbox.html(str).prepend('<button type="button" class="close">×</button>');
				$("#versionBar").before($alertbox);
				
				$alertbox.show().animate({ opacity: 1,right: '10' },500);
	  }	  //alertMessage
	  
	   // Hide All  Alert Message Before
	$('.alertMessage').live('click',function(){
		alertHide();
	});
	  
	  // Loading 
	  function loading(name,overlay) { 
		  	
			$('body').append('<div id="overlay"></div><div id="preloader">'+name+'</div>');
			if(overlay==1){
					$('#overlay').css('opacity',0.4).fadeIn(400,function(){  $('#preloader').fadeIn(400);	});
			  	return  false;
			 }
			$('#preloader').fadeIn();	  
	   }
	  
	     // Unloading 
	  function unloading() { 
		  	
			$('#preloader').fadeOut(400,function(){ $('#overlay').fadeOut();}).remove();
	  }
	  
	  function input_incorrect(type,str)
	  {
		  	  alertMessage(type,str);
			  $('.inner').jrumble({ x: 4,y: 0,rotation: 0 });	
			  $('.inner').trigger('startRumble');
			  setTimeout('$(".inner").trigger("stopRumble")',500);
	  }
	  
	 //不使用 jrumble 插件 在360中 样式出现点问题
	/*  $('#login_btn').click(function(e){				
		  if($("input#username_id").val() == "" || $("input#password").val() == ""){
			  input_incorrect("alertMessage","请输入用户名或密码");
			  return false;
		  }	
		 alertHide();
		 loading("正在登录",1);	
		 setTimeout(unloading, 2000);
	//	 setTimeout( "Login()", 2500 );
	     return false;
	});	*/
		
		//loading("正在登录",1);
		
	//用户注册表单验证部分
	
	function setLoaderGif(input) {
		input.next().removeClass()
		.addClass("mesg_icon loader_gif").show();
	}
	  
	function setRightPng(input)
	{
		input.next().removeClass()
		.addClass("mesg_icon right_png").show();
	}
	
	function setErrorPng(input,errorStr)
	{
		input.parent().next().text(errorStr)
		.show().end().end().next().removeClass()
		.addClass("mesg_icon error_png").show();
	}
	  
	$("input#regist_user").live('blur',
		function () {
			var $curInput = $(this),
				username = $curInput.val();
			triggerMark = 1;
			if(username == null || username=="")
			{
				setErrorPng($curInput,"用户名不能为空");
			} else {
				$.ajax(
						{
							url:'user_existedCheck',
							type:'post',
							data:{
								username:username
							},
							beforeSend:function () {
								setLoaderGif($curInput);
							},
							success:function (data) {
								if(data.resultCode == 4)
								{
									setRightPng($curInput);
								}else {
									setErrorPng($curInput,"用户名已被注册");
								}
							}
						}
				);
			}
			//switchRegistBtn();
		}
	);
	 
	
	$("input#regist_passwd").live('blur',
			function () {
				var $curInput = $(this),
					password = $curInput.val();
				if(password == null || password=="")
				{
					setErrorPng($curInput,"密码不能为空");
				} else if(password.length<6 || password.length>16){
					setErrorPng($curInput,"密码长度为6~16位");
				}else {
					setRightPng($curInput);
				}
				//switchRegistBtn();
			}
		);
	
	
	$("input#regist_repasswd").live('blur',
			function () {
				var $curInput = $(this),
					repassword = $curInput.val(),
					password = $("#regist_passwd").val();
				if(password != repassword)
				{
					setErrorPng($curInput,"输入密码不一致");
				} else if(repassword == null || repassword == ""){
					setErrorPng($curInput,"请输入确认密码");
				}else {
					setRightPng($curInput);
				}
				//switchRegistBtn();
			}
		);
	
	$("input#regist_email").live('blur',
			function () {
				var $curInput = $(this),
					email = $curInput.val();
				if(email.match(/^[a-z0-9]([a-z0-9]*[-_]?[a-z0-9]+)*@([a-z0-9]*[-_]?[a-z0-9]+)+[\.][a-z]{2,3}([\.][a-z]{2})?$/i) == null)
				{
					setErrorPng($curInput,"邮箱输入不正确");
				} else {
					setRightPng($curInput);
				}
				//switchRegistBtn();
			}
		);
	
	$("#user-regist input").live('focus',
			function(){
				var $curInput = $(this),
					$i = $curInput.next(),
					$error_msg = $curInput.parent().next();
					$i.hasClass("error_png") ?$i.hide():"";
					$error_msg.fadeOut("fast");
			}
	);
	
	//测试所有注册条件是否都已通过
	function isAllRight()
	{
		var right = 1;
		$("#user-regist input:visible").each(
				function(index){
					var $i = $(this).next();
					console.log("hasClass right_png:" + $i.hasClass("right_png"));
					console.log($i.get(0));
					if($i.hasClass("error_png")||!$i.hasClass("right_png"))
					{
						right = 0;
						return false;
					}
				}
		);
		return right;
	}
	
	function switchRegistBtn()
	{
		var $registBtn = $("#regist_btn:visible");
		isAllRight()?$registBtn.removeClass("disabled").attr("disabled",false):$registBtn.addClass("disabled").attr("disabled",true);
		/*if(isAllRight())
		{
			$registBtn.removeClass("disabled").css("disbaled",false);
		}*/
	
	}
	
	//
	var internal,dotCount = 0;
	
	function genDot(count){
		var dotStr = "";
		for(var i = 0; i < count; i++)
		{
			dotStr+=".";
		}
	}
	
	function loginProcess()
	{
		
	}
	
	function btnOnLogin()
	{
		var dotCount = 0;
		
	}
	
	function login(){
		window.clearInterval(intervalId);
	}
	
	$("button#regist_btn").live('click',
			function () {
				var $registBtn = $(this),
					i = 0;
				loaderStr = "请稍等！正在注册......";
				triggerMark = 0;
				//$registBtn.attr("disabled",true);
				if(isAllRight()){
					$.ajax(
							{
								"url":'regist',
								"type":"post",
								"data":{
									"username":$("#regist_user").val(),
									"password":$("#regist_passwd").val(),
									"useremail":$("#regist_email").val()
								},
								"beforeSend":function () {},
								"success":function (data){
									window.location.replace("qbec_home");
								}
							}
					);
				}else{
					return false;
				}
			}
	);
	
	
	function generateErrorMsgTip(str)
	{
		$("#login").find(".error-msg-tip").remove();
		$('<div id="no-such-user"></div>').text(str).addClass("error-msg-tip").prependTo("#login");
	}
	
	
	$("#username_id,#password").focus(
			function () {
				$("#login").find(".error-msg-tip").remove();
			}
	);
	
	$("#login_btn").click(
			function () {
				var username = $("#username_id").val(),
					password = $("#password").val();
				triggerMark = 0;
				if(username == "" || username == null)
				{
					generateErrorMsgTip("用户名不能为空");
					return false;
				}else if(password == "" || password == null)
				{
					generateErrorMsgTip("密码不能为空");
					return false;
				}else {
					$.ajax(
							{
								url:'user_validateUser',
								type:"post",
								data:{
									"username":username,
									"password":password
								},
								beforeSend:function () {},
								success:function (data){
									if(data.resultCode == 1)
									{
										window.location.replace("qbec_home");
									}else {
										generateErrorMsgTip("用户名不存在或密码错误");
										return false;
									}
								}
							}
					);
				}
			}
	);
	
	}//ready
);

