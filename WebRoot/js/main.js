$(document).ready(
	function () {
		
		
		/*default: page condition_search.jsp is displayed
		 * when the whole web page is first loaded 
		 */
		//loadPageContent("condition_search");
		
		/*
		 * when the brower is based on IE kernel 
		 * the animation is forbidded 
		 * */
		if($.browser.msie){
			$(".shutcuts li").hover(function() {
				$(this).find("a").css({"text-decoration":"underline","color":"#FF9900"});
		  },function(){
			  	$(this).find("a").css({"text-decoration":"none","color":"#4c535b"});
		  });
		}else {
			$(".shutcuts li").hover(function() {
				 var e = this;
				 //$(".shutcuts li div.notification").trigger("mouseenter");
				$(e).stop().animate({ marginTop: "-7px" }, 200, function() {
						$(e).animate({ marginTop: "-5px" }, 200);
				});
			  },function(){
				 var e = this;
				$(e).stop().animate({ marginTop: "2px" }, 200, function() {
						 $(e).animate({ marginTop: "0px" }, 200);
				});
			  });
		}
			
		
	  /*
	   * start : left menu added by zzy 2013-04-05
	   * the code below is the operation related to left menu 
	   * such as click,hover etc.
	   */
		
		$("#main_menu li").hover(
				 function () {
					 var $oLi = $(this),
					 	 $oUl = $oLi.find("ul");
					 if($oUl.size != 0)
					 {
						 $oUl.show();
						 
					 }
				 },
				 function () {
					 var $oLi = $(this),
					 	 $oUl = $oLi.find("ul");
					 if($oUl.size != 0)
					 {
						 $oUl.hide();
						 
					 }
				 }
		 );
		
		function removeClassSelect() {
			$("#main_menu li").each(
					   function (index) {
						   $(this).removeClass("select");
					   }
			   );
		}
		
		function loadPageContent(pageName) {
			$.ajax({
				   url:'qbec_onClickMenu',
				   type:'post',
				   dataType:"html",
				   data:{
						whichJsp:pageName
				   },
				   beforeSend:function () {
					   $.blockUI({ css: { 
				            border: 'none', 
				            padding: '15px', 
				            backgroundColor: '#000', 
				            '-webkit-border-radius': '10px', 
				            '-moz-border-radius': '10px', 
				            opacity: .5, 
				            color: '#fff' 
							},message:'<h4><img src="./images/preloader.gif" />&nbsp;&nbsp;正在加载页面......<h4>' });
				   },
				   success:function (data) {
					   $.unblockUI();
					   if(data)
					   {
						   $("div.inner").find(".row-fluid").eq(0).nextAll().remove()
						   .end().end().end().append(data);
						   
					   }
				   }
			   });
		}
		
	   $("#main_menu>li").each(
			   function (index) {
				   var $li = $(this),$ulInLi = $li.find("ul"); 
				  
				   if($ulInLi != null && $ulInLi.size() > 0)
				   {
					   $ulInLi.find("li").click(
										function (event) {
										   var idValue = $(this).find("a").attr("id"); 
										   removeClassSelect();
										   $li.addClass("select");
										   loadPageContent(idValue);
									   }
								   );
				   }else{
					   $li.click(
							   function (event) {
								   var idValue = $(this).find("a").attr("id"); 
								   removeClassSelect();
								   $(this).addClass("select");
								   loadPageContent(idValue);
							   }
					   );
				   }
			   }
	   );
	  
	 //end :left menu
	   
	   //back to top
	   	var backToTop_m = 0;
		$('<div id="backToTop" title="返回顶部"></div>').appendTo("body");
		$("#backToTop").hide().live('click',function(){
			 			backToTop_m = 1;
						$("#backToTop").hide();
						$("html,body").animate({"scrollTop":"0"},500);
						}
					);
		 window.onscroll = function(){
			 y =(document.body.scrollTop||document.documentElement.scrollTop);
			 if(y == 0)
				 backToTop_m = 0;
			 if(y > 5&&!backToTop_m)
				 $("#backToTop").show();
			 else
				 $("#backToTop").hide(); 
		 }
		 $("#backToTop").tipsy({trigger:'hover',gravity:'s',live:true});
		 if($.browser.msie){
			 $("#alph_code").tipsy({trigger:'foucs',gravity:'n',live:true});
		 }
		 
		 $('#condition_search').trigger('click');
		 
		 
	}//ready
);