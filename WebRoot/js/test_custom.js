// JavaScript Document
$(document).ready(
		function () {
			$(".tip input").tipsy({trigger:'focus',gravity:'w',live:true});
			$(".chzn-select").chosen({no_results_text: "没有匹配结果"});
		}
);

