 <%@ page language="java" contentType="text/html" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
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


	$('#save_btn').bind('click',function(event){

        var newSchoolInfo = {},year = [],$skxTrs;
        $skxTrs = $('.j-skx','#school_info');

        $("#school_info tr")
        .not('.j-skx,#year')
                            .find('td.j-input')
                            .each(function(){
                                var _this = $(this);
                                    id = _this.attr('id'),
                                    value = _this.text();
                                    if(id=="class"){id="class_name"}
                                newSchoolInfo[id] = value;
                            });


        $('#year td:gt(0)').each(function(){

            var _this = $(this),
                value = _this.text().trim().substring(0,4);
            year.push(value);

        });

        newSchoolInfo['year'] = year;

        $.each(year,function(i,v){
            var start = 4*i,
                end = start + 4,
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
            url : 'school_saveSchoolInfo',
            type : 'post',
            data  : {
                queryData:JSON.stringify($.toJSON(newSchoolInfo))
            }
        })
        .done(function(data){
            console.log(data);
            if(data.schoolInfoData == "success") {
                alert('添加成功');
            }
        })
        .fail(function(err){

        });



  });

	
});

</script>
  <div class="row-fluid" style="margin-top:20px;">
            <div class="offset2 span8">
                <table id="school_info" class="table table-bordered" style="background-color:white;">

                         <tr>
                            <td id="sno" class="c-width j-input" colspan="2" style="font-weight:bold;"></td>
                            <td id="sname" class="j-input c-width" colspan="8" style="font-size:20px;font-weight:bold;"></td>
                            <td  class="c-width" colspan="3" style="font-weight:bold;">NO.</td>
                        </tr>
                       
                         <tr>
                            <td class="c-width">地址</td>
                            <td id="city" class="j-input c-width" colspan="7"></td>
                            <td class="c-width" colspan="3">性质</td>
                            <td id="attr" class="c-width j-input" colspan="2"></td>
                        </tr>
                       
                       
                       
                        <tr>
                            <td class="c-width" colspan="8">规模</td>
                            <td class="c-width" colspan="3">学校类别</td>
                            <td id="class" class="c-width j-input" colspan="2"></td>
                        </tr>
                        <tr>
                            <td class="c-width" colspan="2" style="text-align:center;">占地</td>
                            <td class="c-width" colspan="2" style="text-align:center;">在校生</td>
                            <td class="c-width" colspan="2" style="text-align:center;">硕士点</td>
                            <td class="c-width" colspan="2" style="text-align:center;">博士点</td>
                            <td rowspan="2" style="width: 20px;vertical-align: middle;">段次</td>
                            <td id="wk" class="c-width j-input" rowspan="2" colspan="4" style="text-align:center;"></td>
                        </tr>
                        <tr>
                            <td id="area" class="c-width j-input" colspan="2" ></td>
                            <td id="stu_num" class="c-width j-input" colspan="2" ></td>
                            <td id="m_point" class="c-width j-input" colspan="2" ></td>
                            <td id="d_point" class="c-width j-input" colspan="2" ></td>
                        </tr>
                        <tr>
                            <td  class="c-width" colspan="2" >备注</td>
                            <td id="remark" class="c-width j-input" colspan="11"></td>
                        </tr>
                        <tr>
                             <td class="c-width" colspan="13" style="height:250px;font-size:20px;font-weight:bold;">学校简介(章程)</td>
                        </tr>
                        <tr>
                             <td class="c-width" colspan="13" style="font-size:20px;font-weight:bold;">浙江近三年各批次省控线及该校投档线、人数</td>
                        </tr>
                        <tr id="year">
                            <td class="c-width" rowspan="2" >年度</td>
                            <td class="c-width j-input" colspan="4" ></td>
                            <td class="c-width j-input" colspan="4" ></td>
                            <td class="c-width j-input" colspan="4" ></td>
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
                            <td class="c-width j-input wk" ></td>
                            <td class="c-width j-input wk_num" ></td>
                            <td class="c-width j-input lk" ></td>
                            <td class="c-width j-input lk_num" ></td>
                            <td class="c-width j-input wk" ></td>
                            <td class="c-width j-input wk_num" ></td>
                            <td class="c-width j-input lk" ></td>
                            <td class="c-width j-input lk_num" ></td>
                            <td class="c-width j-input wk" ></td>
                            <td class="c-width j-input wk_num" ></td>
                            <td class="c-width j-input lk" ></td>
                            <td class="c-width j-input lk_num" ></td>
                        </tr>
                        <tr class="j-skx">
                            <td class="c-width" >二段</td>
                            <td class="c-width j-input wk" ></td>
                            <td class="c-width j-input wk_num" ></td>
                            <td class="c-width j-input lk" ></td>
                            <td class="c-width j-input lk_num" ></td>
                            <td class="c-width j-input wk" ></td>
                            <td class="c-width j-input wk_num" ></td>
                            <td class="c-width j-input lk" ></td>
                            <td class="c-width j-input lk_num" ></td>
                            <td class="c-width j-input wk" ></td>
                            <td class="c-width j-input wk_num" ></td>
                            <td class="c-width j-input lk" ></td>
                            <td class="c-width j-input lk_num" ></td>
                        </tr>
                        <tr class="j-skx">
                            <td class="c-width" >三段</td>
                            <td class="c-width j-input wk" ></td>
                            <td class="c-width j-input wk_num" ></td>
                            <td class="c-width j-input lk" ></td>
                            <td class="c-width j-input lk_num" ></td>
                            <td class="c-width j-input wk" ></td>
                            <td class="c-width j-input wk_num" ></td>
                            <td class="c-width j-input lk" ></td>
                            <td class="c-width j-input lk_num" ></td>
                            <td class="c-width j-input wk" ></td>
                            <td class="c-width j-input wk_num" ></td>
                            <td class="c-width j-input lk" ></td>
                            <td class="c-width j-input lk_num" ></td>
                        </tr>
                </table>
            </div>
	        <div class="row-fluid" style="margin-bottom:40px;">
	            <button id="save_btn" type="button" class="offset2 span8 btn btn-primary" style="font-size:20px;font-weight:bold;height: 40px;">保存学校信息</button>
	        </div>
        </div>