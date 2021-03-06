var json_data = null;
var json_map = {};
var con_selectedMap = {};
var con_selectedIndex = 0;
var getObjectClass = function (obj) {
  if (obj && obj.constructor && obj.constructor.toString()) {

    /*
     *    for browsers which have name property in the constructor
     * of the object,such as chrome
     */
    if (obj.constructor.name) {
      return obj.constructor.name;
    }
    var str = obj.constructor.toString();
    /*
     * executed if the return of object.constructor.toString() is
     * "[object objectClass]"
     */

    if (str.charAt(0) == '[') {
      var arr = str.match(/[\w+\s*(\w+)]/);
    } else {
      /*
       * executed if the return of object.constructor.toString() is
       * "function objectClass () {}"
       * for IE Firefox
       */
      var arr = str.match(/function\s*(\w+)/);
    }
    if (arr && arr.length == 2) {
      return arr[1];
    }
  }
  return undefined;
};

/*$(document).ready(
	function () {
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
);*/

//tab 插件
$(document).ready(
    function () {
      //main_menu
      /* $("#main_menu li").hover(
               function () {
                   console.log("I'm invoked");
                   $(this).prev().css("border-bottom","none");
               },
               function () {
                   $(this).prev().css({'border-bottom': '1px solid #212123'});
               }
      );*/

      $.ajax({
        url: 'school_getLastThreeYear',
        type: 'post',
      })
          .done(function (data) {

            var $lis = $('#year li:gt(0)');
            year = jQuery.parseJSON(data.schoolInfoData);
            $.each($lis, function (index, v) {

              var elem_child = $(this).get(0).childNodes;//得到参数元素的所有子元素
              for (var i = 0; i < elem_child.length; i++) { //遍历子元素
                if (elem_child[i].nodeName == "#text") {
                  $(this).get(0).removeChild(elem_child[i])
                }
              }
              $(this).append(year[index] + '年');
            });
            console.log(data);
          })
          .fail(function (err) {

          });

      $('#bl_result').hide();
      $('#begin_validate').bind('click', function (event) {
        var _this = $(this),
            $bl_result = $('#bl_result').toggle();
        if ($('#isvlidate').val()) {
          $('#isvlidate').val(1);
        } else {
          $('#isvlidate').val(0);
        }
        _this.toggleClass('red');
      });
      $('#post').bind('blur', function (event) {
        if ($('#pre').val()) {
          $('#bl_result').text((parseInt($("#post").val()) / parseInt($("#pre").val())).toFixed(3));
        }
      });
      $('#pre').bind('blur', function (event) {
        if ($('#post').val()) {
          $('#bl_result').text((parseInt($("#post").val()) / parseInt($("#pre").val())).toFixed(3));
        }
      });
      $("#tabs").tabs();
      //start:重置查询条件

      $("button#reset_btn").click(
          function (event) {

            $("div#condition").find(":input").val("").end()
                .find('a[title="province"],a[title="city"]').find("span").text("不限")
                .end().end().find("ul.search_condition li span").removeClass()
                .addClass("checkbox_not_checked").attr("data-value", "0")
                .end().find("ul.radio li:nth-child(1)").eq(0).trigger('click')
                .end().find("span").removeClass().addClass("radio_selected")
                .end().nextAll().find("span").removeClass().addClass("radio_not_selected").attr("data-value", "0")
                .end().end().end().find("ul.con_selected").remove();

            con_selectedMap = {};
            con_selectedIndex = 0;

            event.stopPropagation();
          }
      );

      //end:重置查询条件


      $("ul#province").find("li").on('click',
          function (event) {
            alert(json_data);
          }
      );
      $.ajax(
          {
            'url': 'data/province.json',
            'dataType': 'json',
            'type': 'get',
            'success': function (data) {
              if (data) {
                json_data = data;
                //alert(data);

                var $select = $("select.province").empty();
                for (var s in data) {
                  if (typeof data[s] == "string") {
                    json_map[data[s]] = s;
                    $('<option value="' + data[s] + '" >' + data[s] + '</option>').appendTo($select);
                  } else if (getObjectClass(data[s]) == "Object") {
                    //以下貌似可以抽取
                    for (var i in data[s]) {
                      json_map[i] = s;
                      $('<option value="' + i + '" >' + i + '</option>').appendTo($select);
                    }
                  } else {
                    for (var j = 0; j < data[s].length; j++) {
                      for (var l in data[s][j]) {
                        json_map[l] = s;
                        $('<option value="' + l + '" >' + l + '</option>').appendTo($select);
                      }
                    }
                  }
                }
                $("select.province").chosen();
                $("select.city").chosen();
                if ($.browser.msie) {
                  $("div.chzn-container").css("top", "8px");
                }
                $("div.chzn-drop").css("z-index", "1");
                //$(".chzn-search input").unbind();
                $("ul#province").find("li").bind('click',
                    function (event) {

                      var con_selectedStr = "con_selected";
                      var $select_city = $("select.city");
                      $select_city.children().filter(':gt(0)').remove();
                      var province = $(this).text();
                      var jc = json_map[province];

                      var temp_obj = json_data[jc];

                      //已经存在时
                      if (!con_selectedMap[province]) {
                        if (province != "不限" && $("ul.con_selected").size() > 0 && $("ul.con_selected li").size() == 2 && $("ul.con_selected li:eq(0)").find("span").text() == "不限") {
                          delete con_selectedMap[$("ul.con_selected li:eq(0)").find("span").text()];
                          $("ul.con_selected").remove();
                        } else if (province == "不限" && $("ul.con_selected").size() > 0) {
                          $("ul.con_selected").remove();
                          con_selectedMap = {};
                        }
                        con_selectedStr = con_selectedStr + (++con_selectedIndex);
                        con_selectedMap[province] = con_selectedStr;
                        $t_ul = $('<ul id="' + con_selectedStr + '" class="con_selected"></ul>');
                        $('<li class="first"><span>' + province + '</span><i>x</i></li>').appendTo($t_ul);
                        $('<li class="unlimit"><span>不限</span><i>x</i></li>').appendTo($t_ul);
                        $('div#address').before($t_ul);
                      }
                      var marin_m = 0;
                      for (var e in con_selectedMap) {
                        marin_m++;
                      }
                      if (marin_m != 0) {
                        $("#address").css("marginTop", "20px");
                      }
                      if (getObjectClass(temp_obj) == "Object") {
                        for (var i = 0, t = temp_obj[province]; i < t.length; i++) {
                          $('<option value="' + t[i] + '" >' + t[i] + '</option>').appendTo($select_city);
                        }
                      } else {
                        for (var j = 0; j < temp_obj.length; j++) {
                          for (var p in temp_obj[j]) {
                            if (p == province) {
                              for (var l = 0, t = temp_obj[j][province]; l < t.length; l++) {
                                $('<option value="' + t[l] + '" >' + t[l] + '</option>').appendTo($select_city);
                              }
                            }
                          }

                        }
                      }
                      $("select.city").trigger("liszt:updated");

                      var $li_array = $("ul#city").find("li");
                      $li_array.bind('click',
                          function () {
                            var province = $('a[title="province"]').find("span").text();//获取省的值
                            var u_id = con_selectedMap[province];
                            var city = $(this).text();
                            var $aLi = $("ul#" + u_id).find("li");
                            var i = 0;
                            if ($aLi.length > 1) {
                              $aLi.slice(1).each(
                                  function (index) {
                                    if (city == $(this).find("span").text()) {
                                      i = 1;
                                      return false;
                                    }
                                  }
                              );

                              if (i == 0) {
                                if ($aLi.eq(1).find("span").text() == "不限") {
                                  $aLi.eq(1).remove();
                                }
                                if (city == "不限") {
                                  $aLi.slice(1).remove();
                                }
                                var i = 0;
                                $('<li><span>' + city + '</span><i>x</i></li>').appendTo("#" + u_id);
                              }
                            } else {
                              $('<li><span>' + city + '</span><i>x</i></li>').appendTo("#" + u_id);
                            }
                          });
                    }
                );

              }


            }//ajax success
          }
      );
      //地址条件贴条删除
      $("ul.con_selected li").live("click",
          function () {
            if ($(this).hasClass("first")) {
              var province = $(this).find("span").text();
              $(this).parent().remove();
              delete con_selectedMap[province];
              var i = 0;
              for (var e in con_selectedMap) {
                i++;
              }
              if (i == 0) {
                $("#address").css("marginTop", "0");
              }
            } else {
              $(this).remove();
            }
          }
      );
    }
);


$(document).ready(
    function () {

    }
);

//高级查询部分
$(document).ready(
    function () {
      $('<span class="arrow"></span>')
          .addClass("arrow-down").prependTo("#accordion h3");
      $("#accordion h3").click(
          function () {
            $(this).find("span").toggleClass("arrow-right")
                .toggleClass("arrow-down").end()
                .next().slideToggle('fast').prev().toggleClass("fold");
            $("div.dataTables_wrapper").slideUp();
            if ($("#accordion h3").hasClass("fold")) {
              $("button#reset_btn").addClass("disabled");
            } else {
              $("button#reset_btn").removeClass("disabled");
            }
          }
      );
    }
);


//check_box
$(document).ready(
    function () {
      var isAllChecked = function (a) {
        var i = 0;
        a.each(
            function (index, value) {
              if ($(this).attr("data-value") == 0)
                return false;
              else
                i = i + 1;

            }
        );
        if (i == a.length)
          return true;
        else
          return false;
      };

      $("ul.search_condition").find("span")
          .addClass("checkbox_not_checked")
          .attr("data-value", "0").parent()
          .click(
              function (event) {
                var $span = $(this).find("span");
                var $aSpan = $(this).siblings().find("span");
                var data = $span.attr("data-value");
                $span.removeClass();
                if (data == "0") {
                  $span.addClass("checkbox_checked").attr("data-value", "1");
                  if ($span.attr("mark") == "attr_all") {
                    $aSpan.removeClass().addClass("checkbox_checked").attr("data-value", "1");
                  }
                } else {
                  $span.addClass("checkbox_not_checked").attr("data-value", "0");
                  if ($span.attr("mark") == "attr_all") {
                    $aSpan.removeClass().addClass("checkbox_not_checked").attr("data-value", "0");
                  }
                }
                if ($span.attr("mark") != "attr_all") {
                  if (isAllChecked($(this).parent().find("li:gt(0)").find("span"))) {
                    $(this).parent().find("li").eq(0).find("span").removeClass().addClass("checkbox_checked").attr("data-value", "1");
                  } else {
                    $(this).parent().find("li").eq(0).find("span").removeClass().addClass("checkbox_not_checked").attr("data-value", "0");
                  }
                }
                $(this).trigger("mouseenter");
              }
          )
          .hover(
              function (event) {
                var $span = $(this).find("span");
                var $aSpan = $(this).siblings().find("span");
                var data = $span.attr("data-value");
                $span.removeClass();
                if (data == "0") {
                  $span.addClass("checkbox_hover_in");
                  if ($span.attr("mark") == "attr_all") {
                    $aSpan.each(
                        function (index) {
                          if ($(this).attr("data-value") == "0") {
                            $(this).removeClass().addClass("checkbox_hover_in");
                          } else {
                            $(this).removeClass().addClass("checkbox_checked_hover_in");
                          }
                        }
                    );
                  }
                } else {
                  $span.addClass("checkbox_checked_hover_in");
                  if ($span.attr("mark") == "attr_all") {
                    $aSpan.each(
                        function (index) {
                          if ($(this).attr("data-value") == "0") {
                            $(this).removeClass().addClass("checkbox_hover_in");
                          } else {
                            $(this).removeClass().addClass("checkbox_checked_hover_in");
                          }
                        }
                    );
                  }
                }
              },
              function (event) {
                var $span = $(this).find("span");
                var $aSpan = $(this).siblings().find("span");
                var data = $span.attr("data-value");
                $span.removeClass();
                if (data == "1") {
                  $span.addClass("checkbox_checked");
                  if ($span.attr("mark") == "attr_all") {
                    $aSpan.removeClass().addClass("checkbox_checked");
                  }
                } else {
                  $span.addClass("checkbox_not_checked");
                  if ($span.attr("mark") == "attr_all") {
                    $aSpan.each(
                        function (index) {
                          if ($(this).attr("data-value") == "0") {
                            $(this).removeClass().addClass("checkbox_not_checked");
                          } else {
                            $(this).removeClass().addClass("checkbox_checked");
                          }
                        }
                    );
                  }
                }
              }
          );
    }//ready
);


//radio
$(document).ready(
    function () {
      $("#bl ul.radio").find("span")
          .addClass("radio_not_selected")
          .attr("data-value", "0").parent()
          .filter(":nth-child(1)").find("span")
          .removeClass().addClass("radio_selected")
          .attr("data-value", "1")
          .end().end()
          .click(
              function () {
                var $aUl = $(this).parent().nextAll().slice(0, -1);
                if ($aUl.length > 1) {
                  if ($(this).find("span").attr("data-value") != "1") {
                    $aUl.slideToggle('fast');
                  }
                }
                $(this).find("span").removeClass().addClass("radio_selected")
                    .attr("data-value", "1").end().trigger("mouseenter").siblings().find("span")
                    .removeClass().addClass("radio_not_selected").attr("data-value", "0");
              }
          )
          .hover(
              function () {
                var $span = $(this).find("span");
                if ($span.attr("data-value") == "1") {
                  $span.removeClass().addClass("radio_selected_hover_in");
                } else {
                  $span.removeClass().addClass("radio_hover_in");
                }
              },
              function () {
                var $span = $(this).find("span");
                if ($span.attr("data-value") == "1") {
                  $span.removeClass().addClass("radio_selected");
                } else {
                  $span.removeClass().addClass("radio_not_selected");
                }
              }
          );
    }
);


//得到查询条件构造查询json的传递到action
$(document).ready(
    function () {
      var dataTable_m = 0;
      var oTable = null;
      var school_array = null;
      $("div#alert").hide();


      jQuery.fn.dataTableExt.oSort['sortFed-asc'] = function (x, y) {
        console.log('x:', x);
        console.log('y:', y);
      };

      jQuery.fn.dataTableExt.oSort['sortFed-desc'] = function (x, y) {
        console.log('x:', x);
        console.log('y:', y);
      };
      jQuery.fn.dataTableExt.oSort['sortRatio-asc'] = function (x, y) {
        x = parseInt(x.substring(0, x.length - 1), 10);
        y = parseInt(y.substring(0, y.length - 1), 10);
        return x > y ? -1 : 1;
      };

      jQuery.fn.dataTableExt.oSort['sortRatio-desc'] = function (x, y) {
        x = parseInt(x.substring(0, x.length - 1), 10);
        y = parseInt(y.substring(0, y.length - 1), 10);
        return x > y ? 1 : -1;
      };

      oTable = $("#test").dataTable(
          {
            "bFilter": false,
//						"bSort": false,
            "aaData": school_array,
            "bProcess": true,
            "bJQueryUI": true,
            "bAutoWidth": true,
            "sPaginationType": "full_numbers",
            "oLanguage": {
              "sLengthMenu": "每页显示 _MENU_ 条记录",
              "sZeroRecords": "没有找到匹配的记录",
              "sInfo": "显示 _START_ 到 _END_ 总共 _TOTAL_ 条记录",
              "sInfoEmpty": "显示 0 到 0 总共 0 条记录",
              "sInfoFiltered": "",
              "sSearch": "搜索",
              "oPaginate": {
                "sFirst": "首页",
                "sLast": "尾页",
                "sNext": "下一页",
                "sPrevious": "上一页"
              }
            },
            "aoColumns": [
              {"mDataProp": "sname", "sWidth": "25%", "bSortable": false},
              {"mDataProp": "modify", "sWidth": "15%", "sClass": "center", "bSortable": false},
              {
                "mDataProp": "address",
                "sWidth": "15%",
                "sClass": "center",
                "bSortable": true,
                "aDataSort": [2, 6]
              },
              {"mDataProp": "attr", "sWidth": "10%", "sClass": "center", "bSortable": false},
              {"mDataProp": "sclass", "sWidth": "15%", "sClass": "center", "bSortable": false},
              {"mDataProp": "remark", "sWidth": "25%", "sClass": "center", "bSortable": false},
              {"mDataProp": "ratio", "sWidth": "5%", "sClass": "center", "bSortable": true, "sType": "sortRatio"}
            ]
          });
      oTable.fnSetColumnVis(6, false);
      $("div.dataTables_wrapper").css("display", "none");
      //按字母编码查询
      $("#query").click(
          function () {
            oTable.fnSetColumnVis(6, false);
            $("html,body").animate({"scrollTop": "200"}, 50);
            var alph_code = $("#alph_code").val();
            if (alph_code) {
              $("#accordion h3").find("span").removeClass("arrow-down").addClass("arrow-right")
                  .end().next().slideUp('fast').prev().addClass("fold");
              var json_data = {};
              if (alph_code.match(/\w+/))
                json_data['sno'] = alph_code;
              else
                json_data['sname'] = alph_code;
              var sentData = {};
              sentData["sentData"] = json_data;
              $.ajax(
                  {
                    'url': 'school_search',
                    'data': {queryData: JSON.stringify($.toJSON(sentData))},
                    'type': 'post',
                    'dataType': 'json',
                    'beforeSend': function () {
                      $.blockUI({
                        css: {
                          border: 'none',
                          padding: '15px',
                          backgroundColor: '#000',
                          '-webkit-border-radius': '10px',
                          '-moz-border-radius': '10px',
                          opacity: .5,
                          color: '#fff'
                        }, message: '<h4><img src="./images/preloader.gif" />&nbsp;&nbsp;正在查询......<h4>'
                      });
                    },
                    'success': function (data) {
                      $.unblockUI();
                      school_array = eval('(' + data.aaData + ')');

                      oTable.fnClearTable();
                      oTable.fnAddData(school_array);
                      oTable.fnPageChange("first");
                      $("div.dataTables_wrapper").slideDown();
                    }
                  }
              );
            } else {
              $("#alph_code").focus();
            }
          }
      );

      $("div.beginSearch").click(
          function () {
            oTable.fnSetColumnVis(6, false);
            //构造查询json
            var dataToQuery = {};
            dataToQuery["addr"] = [];
            $("#condition ul.con_selected").each(
                function (index) {
                  var temp_array = [];
                  $(this).find("span").each(
                      function () {
                        temp_array.push($(this).text());
                      }
                  );
                  dataToQuery["addr"].push(temp_array);
                }
            );

            /*	var province = $('a[title="province"]').find('span').text();
                var city = $('a[title="city"]').find('span').text();
                console.log("province:" + province + "city:" + city);
                dataToQuery["province"] = province;
                dataToQuery["city"] = city;*/

            var aUl = $("ul.search_condition").slice(0, 4);

            for (var i = 0; i < aUl.length; i++) {
              var m = 0;
              var key = $(aUl[i]).attr("id");
              var t_array = [];
              var aLi = $(aUl[i]).find("li");
              for (var j = 0; j < aLi.length; j++) {
                if ($(aLi[j]).find('span').attr("data-value") == "1") {
                  t_array[m++] = $(aLi[j]).text();
                }
              }
              dataToQuery[key] = t_array;
            }

            if (dataToQuery["category"] && dataToQuery["category"][0] == "全部")
              delete dataToQuery["category"];
            if (dataToQuery["attr"] && dataToQuery["attr"][0] == "全部")
              delete dataToQuery["attr"];
            if (dataToQuery.subject.length && dataToQuery["subject"][0] == "全部" || dataToQuery.subject.length == 0) {
              delete dataToQuery["subject"];
              dataToQuery["lk"] = [];
              dataToQuery["wk"] = [];

            } else if (dataToQuery.subject.length && dataToQuery["subject"][0] == "分数") {
              delete dataToQuery["subject"];
              dataToQuery["wk"] = [];
            } else if (dataToQuery.subject.length && dataToQuery["subject"][0] == "排名") {
              delete dataToQuery["subject"];
              dataToQuery["lk"] = [];
            }

            if (dataToQuery.pici.length) {
              if (dataToQuery["pici"][0] == "全部") {
                dataToQuery["pici"].shift();
              }
              if (dataToQuery["wk"])
                dataToQuery["wk"] = dataToQuery["pici"];
              if (dataToQuery["lk"])
                dataToQuery["lk"] = dataToQuery["pici"];
            }

            delete dataToQuery["pici"];
            if ($("#keyWords").val()) {
              dataToQuery["remark"] = $("#keyWords").val();
            }
            if ($("#pre").val()) {
              dataToQuery["pre"] = $("#pre").val();
            }
            if ($("#post").val()) {
              dataToQuery["post"] = (parseInt($("#post").val()) / parseInt($("#pre").val())).toFixed(3);
              dataToQuery["pt"] = parseInt($("#post").val());
              delete dataToQuery["pre"];

            }
            if (dataToQuery.hasOwnProperty("pre") || dataToQuery.hasOwnProperty("post")) {
              dataToQuery["bl"] = [];
              dataToQuery["extra"] = [];
              $("#bl ul.js-bl:visible").find("span").each(
                  function () {
                    if ($(this).attr("data-value") == "1") {
                      dataToQuery["bl"].push($(this).parent().text());
                    }
                  }
              );
              $("#bl ul#extra.search_condition").find("span").each(
                  function () {
                    if ($(this).attr("data-value") == "1") {
                      dataToQuery["extra"].push($(this).parent().text());
                    }
                  }
              );
            }
            console.log(dataToQuery);
            if ((dataToQuery.hasOwnProperty("pre") && !dataToQuery["pre"].match(/^\d\.\d{1,3}$/)) || (dataToQuery.hasOwnProperty("post") && !dataToQuery["post"].match(/^\d\.\d{1,3}$/))) {
              $("#alert").find("span").text("比例输入有误，请输入正确的比例，小数点在得小于3位").end().show();
            } else {
              $("#alert").hide();
              if (dataToQuery.hasOwnProperty("bl") && dataToQuery.hasOwnProperty("lk") && dataToQuery.hasOwnProperty("wk")) {
                if (dataToQuery["lk"].length == 0 && dataToQuery["wk"].length == 0) {
                  $("#alert").find("span").text("如果查询中加入比例这个条件，则分数和排名必须选中一个！且只能选中一个！")
                      .end().show();
                } else {
                  $("#alert").find("span").text("如果查询中加入比例这个条件，则分数和排名必须选其中一个！且只能选中一个！")
                      .end().show();
                }
              } else if (dataToQuery.hasOwnProperty("bl")) {
                if (dataToQuery.hasOwnProperty("lk") && (dataToQuery["lk"].length > 1 || dataToQuery["lk"].length == 0)) {
                  $("#alert").find("span").text("如果查询中加入比例这个条件，则分数和排名必须选其中一个！且只能选中一个！")
                      .end().show();
                } else if (dataToQuery.hasOwnProperty("wk") && (dataToQuery["wk"].length > 1 || dataToQuery["wk"].length == 0)) {
                  $("#alert").find("span").text("如果查询中加入比例这个条件，则分数和排名必须选其中一个！且只能选中一个！")
                      .end().show();
                } else {
                  $("#accordion h3").trigger('click');
                  var sentData = {};
                  sentData["sentData"] = dataToQuery;

                  $.ajax(
                      {
                        url: 'school_search',
                        data: {queryData: JSON.stringify($.toJSON(sentData))},
                        type: 'post',
                        dataType: 'json',
                        beforeSend: function () {
                          $.blockUI({
                            css: {
                              border: 'none',
                              padding: '15px',
                              backgroundColor: '#000',
                              '-webkit-border-radius': '10px',
                              '-moz-border-radius': '10px',
                              opacity: .5,
                              color: '#fff'
                            },
                            message: '<h4><img src="./images/preloader.gif" />&nbsp;&nbsp;正在查询......<h4>'
                          });
                        },
                        success: function (data) {
                          $.unblockUI();
                          $("html,body").animate({"scrollTop": "200"}, 50);
                          school_array = eval('(' + data.aaData + ')');

                          oTable.fnClearTable();
                          oTable.fnAddData(school_array);
                          oTable.fnPageChange("first");
                          if (school_array[0] && school_array[0]["ratio"] != "1")
                            oTable.fnSetColumnVis(6, true);
                          else if (dataToQuery.hasOwnProperty("bl"))
                            oTable.fnSetColumnVis(6, true);
                          $("div.dataTables_wrapper").slideDown();
                        },
                        error: function (data) {
                        }
                      }
                  );
                }
              } else {
                $("#alert").hide();
                $("#accordion h3").trigger('click');
                var sentData = {};
                sentData["sentData"] = dataToQuery;
                $.ajax(
                    {
                      url: 'school_search',
                      data: {queryData: JSON.stringify($.toJSON(sentData))},
                      type: 'post',
                      dataType: 'json',
                      beforeSend: function () {
                        $.blockUI({
                          css: {
                            border: 'none',
                            padding: '15px',
                            backgroundColor: '#000',
                            '-webkit-border-radius': '10px',
                            '-moz-border-radius': '10px',
                            opacity: .5,
                            color: '#fff'
                          }, message: '<h4><img src="./images/preloader.gif" />&nbsp;&nbsp;正在查询......<h4>'
                        });
                      },
                      success: function (data) {
                        $.unblockUI();
                        $("html,body").animate({"scrollTop": "200"}, 50);
                        school_array = eval('(' + data.aaData + ')');

                        oTable.fnClearTable();
                        oTable.fnAddData(school_array);
                        oTable.fnPageChange("first");
                        if (school_array[0] && school_array[0]["ratio"] != "1")
                          oTable.fnSetColumnVis(6, true);
                        else if (dataToQuery.hasOwnProperty("bl"))
                          oTable.fnSetColumnVis(6, true);
                        $("div.dataTables_wrapper").slideDown();
                      }
                    }
                );
              }
            }

          }//function
      );//click
    }
);





