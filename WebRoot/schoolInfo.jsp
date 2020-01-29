<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<html>
  <head>
    <title>Bootstrap 101 Template</title>
    <link href="./bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script src="http://code.jquery.com/jquery.js"></script>
    <script type="text/javascript" src="js/jquery.json-2.4.js"></script>
    <style type="text/css">
        td.c-width {
            width: 47px;
            height: 25px;
            word-wrap:break-word;
            vertical-align: middle;
            text-align:center;
            cursor: text;
        }

       #school_info .j-input {
            cursor:pointer;
        }

    </style>
    <script type="text/javascript">
        $(function(){
            $('#school_info').on('click.td','td.j-input',function(event){
                var _target = $(event.target),
                    id = _target.attr('id'),
                    width = _target.width(),
                    height = _target.height(),
                    value = _target.text(),
                    input = $('<input type="text" id="' + id + '"/>').val(value)
                            .css({
                                width : width,
                                height : height,
                                margin : 0
                            });
                _target.html(input);
                input.focus();
            });
            $('#school_info').on('blur.input','td.j-input input',function(event){
                var _target = $(event.target),
                    id = _target.attr('id'),
                    value = _target.val(),
                    pTd = _target.parents('td');
                _target.remove();
                pTd.text(value);
            });


          function displayDataInTable(data,$table) {
            var $skxTrs = $('.j-skx',$table);
            $.each(data,function(key,value){
                if(typeof value == "string" || typeof value == "number") {
                     $("#" + key,$table).text(value);
                } else {
                    
                    if(key == "year") {
                        $("#year .j-input",$table).each(function(index){
                            $(this).text(data['year'][index]+'年');
                            $(this).attr({
                                'data-oldyear' : data['year'][index]
                            });
                        });
                    }
                }
               
            });

            $.each(data['year'],function(index,value){
                var $tr,$tds,start,end,
                    skxData = data[value];
                $.each(skxData,function(i,v){
                     $tr = $skxTrs.eq(i);
                     start = 4*index;
                     end = start + 4;
                     $tds = $tr.find("td:gt("+ start + "):lt(" + end+ ")");
                     
                     $tds.eq(0).text(v['wk']);
                     $tds.eq(1).text(v['wk_num']);
                     $tds.eq(2).text(v['lk']);
                     $tds.eq(3).text(v['lk_num']);
                });
            });
          }

		  $.ajax({
                url : 'school_getSchoolInfo',
                type : 'post',
                data : {
                    sid : $('#sid').val()
                }
          })
          .done(function(data){
            console.log(data);
            displayDataInTable(jQuery.parseJSON(data.schoolInfoData),$('#school_info'));
          })
          .fail(function(err){

          });

          $('#save_modify').bind('click',function(event){

                var newSchoolInfo = {},oldyear=[],year = [],$skxTrs;
                $skxTrs = $('.j-skx','#school_info');

                $("#school_info tr")
                .not('.j-skx,#year')
                                    .find('td.j-input')
                                    .each(function(){
                                        var _this = $(this);
                                            id = _this.attr('id'),
                                            value = _this.text();
                                        newSchoolInfo[id] = value;
                                    });


                $('#year td:gt(0)').each(function(){

                    var _this = $(this),
                        value = _this.text().trim().substring(0,4);
                    year.push(value);
                    oldyear.push(_this.data('oldyear'));

                });

                newSchoolInfo['year'] = year;
                newSchoolInfo['oldyear'] = oldyear;
			
                $.each(year,function(i,v){
                    var start = 4*i,
                        end = 4,
                        tArray = [];
                    $skxTrs.each(function(){
                        var _this = $(this),
                            tObject = {},
                            $tds = _this.find('td:gt(' + start + '):lt(' + end + ')');
                        $.each($tds,function(){
                            tObject[$(this).get(0).className.split(' ').pop()] = $(this).text();
                       });
                       tArray.push(tObject);
                    });
                    newSchoolInfo[v] = tArray;
                    
                });





		
                $.ajax({
                    url : 'school_updateSchoolInfo',
                    type : 'post',
                    data  : {
                        queryData:JSON.stringify($.toJSON(newSchoolInfo)),
                        sid : $('#sid').val()
                    }
                })
                .done(function(data){
                    console.log(data);
                    if(data.schoolInfoData == "success") {
                        alert('修改成功');
                    }
                })
                .fail(function(err){

                });



          });

          $('#delete_btn').bind('click',function(event){
        	  $.ajax({
                  url : 'school_deleteSchool',
                  type : 'post',
                  data : {
                  	sid : $('#sid').val()
              	  }
              })
              .done(function(data){
            	  if(data.schoolInfoData == "success") {
                      alert('删除成功');
                  }
              })
              .fail(function(err){
    			
                  
              });
  		  });
         
            
        });
    </script>
  </head>
  <% String sid = request.getParameter("sid"); %>
  <body>
    <input type="hidden" id="sid" value="<%= sid %>"/>
    <div class="container-fluid" style="margin-top:40px;">
       
        <div class="row-fluid" style="margin-top:20px;">
            <div class="offset2 span8">
                <table id="school_info" class="table table-bordered">

                         <tr>
                            <td id="sno" class="c-width input" colspan="2" style="font-weight:bold;">编号：AH019</td>
                            <td id="sname" class="j-input c-width" colspan="8" style="font-size:20px;font-weight:bold;">安徽医科大学临床医学院</td>
                            <td  class="c-width" colspan="3" style="font-weight:bold;">NO.</td>
                        </tr>
                       
                         <tr>
                            <td class="c-width">地址</td>
                            <td id="city" class="j-input c-width" colspan="7">芜湖市</td>
                            <td class="c-width" colspan="3">性质</td>
                            <td id="attr" class="c-width j-input" colspan="2">公办</td>
                        </tr>
                       
                       
                       
                        <tr>
                            <td class="c-width" colspan="8">规模</td>
                            <td class="c-width" colspan="3">学校类别</td>
                            <td id="category" class="c-width j-input" colspan="2">理工类</td>
                        </tr>
                        <tr>
                            <td class="c-width" colspan="2" style="text-align:center;">占地</td>
                            <td class="c-width" colspan="2" style="text-align:center;">在校生</td>
                            <td class="c-width" colspan="2" style="text-align:center;">硕士点</td>
                            <td class="c-width" colspan="2" style="text-align:center;">博士点</td>
                            <td rowspan="2" style="width: 20px;vertical-align: middle;">段次</td>
                            <td id="wk" class="c-width j-input" rowspan="2" colspan="4" style="text-align:center;">三</td>
                        </tr>
                        <tr>
                            <td id="area" class="c-width j-input" colspan="2" ></td>
                            <td id="stu_num" class="c-width j-input" colspan="2" ></td>
                            <td id="m_point" class="c-width j-input" colspan="2" ></td>
                            <td id="d_point" class="c-width j-input" colspan="2" ></td>
                        </tr>
                        <tr>
                            <td  class="c-width" colspan="2" >备注</td>
                            <td id="remark" class="c-width j-input" colspan="11">不退档</td>
                        </tr>
                        <tr>
                             <td class="c-width" colspan="13" style="height:250px;font-size:20px;font-weight:bold;">学校简介(章程)</td>
                        </tr>
                        <tr>
                             <td class="c-width" colspan="13" style="font-size:20px;font-weight:bold;">浙江近三年各批次省控线及该校投档线、人数</td>
                        </tr>
                        <tr id="year">
                            <td class="c-width" rowspan="2" >年度</td>
                            <td class="c-width j-input" colspan="4" >2010年</td>
                            <td class="c-width j-input" colspan="4" >2011年</td>
                            <td class="c-width j-input" colspan="4" >2012年</td>
                        </tr>
                        <tr> 
                            <td class="c-width" >分数</td>
                            <td class="c-width" >最高</td>
                            <td class="c-width" >排名</td>
                            <td class="c-width" >最高</td>
                            <td class="c-width" >分数</td>
                            <td class="c-width" >最高</td>
                            <td class="c-width" >排名</td>
                            <td class="c-width" >最高</td>
                            <td class="c-width" >分数</td>
                            <td class="c-width" >最高</td>
                            <td class="c-width" >排名</td>
                            <td class="c-width" >最高</td>
                        </tr>
                        <tr class="j-skx">
                            <td class="c-width" >一段</td>
                            <td class="c-width j-input wk" >590·</td>
                            <td class="c-width j-input wk_num" ></td>
                            <td class="c-width j-input lk" ></td>
                            <td class="c-width j-input lk_num" ></td>
                            <td class="c-width j-input wk" >590·</td>
                            <td class="c-width j-input wk_num" ></td>
                            <td class="c-width j-input lk" ></td>
                            <td class="c-width j-input lk_num" ></td>
                            <td class="c-width j-input wk" >590·</td>
                            <td class="c-width j-input wk_num" ></td>
                            <td class="c-width j-input lk" ></td>
                            <td class="c-width j-input lk_num" ></td>
                        </tr>
                        <tr class="j-skx">
                            <td class="c-width" >二段</td>
                            <td class="c-width j-input wk" >590·</td>
                            <td class="c-width j-input wk_num" ></td>
                            <td class="c-width j-input lk" ></td>
                            <td class="c-width j-input lk_num" ></td>
                            <td class="c-width j-input wk" >590·</td>
                            <td class="c-width j-input wk_num" ></td>
                            <td class="c-width j-input lk" ></td>
                            <td class="c-width j-input lk_num" ></td>
                            <td class="c-width j-input wk" >590·</td>
                            <td class="c-width j-input wk_num" ></td>
                            <td class="c-width j-input lk" ></td>
                            <td class="c-width j-input lk_num" ></td>
                        </tr>
                        <tr class="j-skx">
                            <td class="c-width" >三段</td>
                            <td class="c-width j-input wk" >590·</td>
                            <td class="c-width j-input wk_num" ></td>
                            <td class="c-width j-input lk" ></td>
                            <td class="c-width j-input lk_num" ></td>
                            <td class="c-width j-input wk" >590·</td>
                            <td class="c-width j-input wk_num" ></td>
                            <td class="c-width j-input lk" ></td>
                            <td class="c-width j-input lk_num" ></td>
                            <td class="c-width j-input wk" >590·</td>
                            <td class="c-width j-input wk_num" ></td>
                            <td class="c-width j-input lk" ></td>
                            <td class="c-width j-input lk_num" ></td>
                        </tr>
                </table>
            </div>
	        <div class="row-fluid" style="margin-bottom:40px;">
	            <button id="save_modify" type="button" class="offset2 span4 btn btn-primary" style="font-size:20px;font-weight:bold;height: 40px;">保存修改</button>
	            <button id="delete_btn" type="button" class="span4 btn btn-primary" style="font-size:20px;font-weight:bold;height: 40px;">删除学校</button>
	        </div>
        </div>
    </div>
    
  </body>
</html>