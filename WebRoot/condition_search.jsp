<%@ page language="java" contentType="text/html" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="row-fluid" style="background-color:white">
					<div class="span12 clearfix">
							<div id="tabs">
								<ul>
									<li><a href="#tab-1">按字母编码或学校名查询</a></li>
								</ul>
								<div id="tab-1">
									
										<input id="alph_code" type="text" class="search-query" placeholder="请输入要查询的学校编码或学校名" title="请输入要查询的学校编码或学校名" style="margin-bottom:0px;"/>
										<button class="btn" id="query"><i class="icon-search" style="margin-top:2px;margin-right:4px;"></i>查询</button>
								</div>
							</div>
							<div id="accordion">
								<h3>高级查询<button class="btn" id="reset_btn" style="float:right;"><i class="icon-wrench" style="margin-top:2px;margin-right:4px;"></i>重置查询条件</button></h3>
								<div id="condition" class="panel">
									<h4 class="subTitle">所在地区：</h4>
									<div id="address">
									<span>省或直辖市：</span>
									<select class="province"  style="width:350px;" tabindex="1">
									</select>	
									<span>市区：</span>
									<select class="city" style="width:350px;" tabindex="1">
										<option value="不限">不限</option>
									</select>	
									</div>
									<h4 class="subTitle">学校性质：</h4>
									<ul id="attr" class="search_condition">
										<li><span mark="attr_all" ></span>全部</li>
										<li><span></span>211</li>
										<li><span></span>985</li>
										<li><span></span>公办</li>
										<li><span></span>民办</li>
										<li><span></span>独立学院</li>
										<li><span></span>中外合作</li>
									</ul>
									<h4 class="subTitle">招生类型：</h4>
									<ul id="category" class="search_condition">
										<li><span mark="attr_all"></span>全部</li>
										<li><span></span>综合类</li>
										<li><span></span>政法类</li>
										<li><span></span>师范类</li>
										<li><span></span>财经类</li>
										<li><span></span>语言类</li>
										<li><span></span>医药类</li>
									</ul>
									<h4 class="subTitle">学校查询科目：</h4>
									<ul id="subject" class="search_condition">
										<li><span mark="attr_all"></span>全部</li>
										<li><span></span>分数</li>
										<li><span></span>排名</li>
									</ul>
									<h4 class="subTitle">学校招生段次：</h4>
									<ul id="pici" class="search_condition">
										<li><span mark="attr_all"></span>全部</li>
										<li><span></span>一</li>
										<li><span></span>二</li>
										<li><span></span>三</li>
									</ul>
									<h4 class="subTitle">学校备注：</h4>
									<div id="remark">
										<label for="keyWords">关键字：<input type="text" id="keyWords" name="keyWords"/></label>
									</div>
									<h4 class="subTitle">分数线差值：</h4>
									<div id="bl">
										<span>比例:</span>
										&nbsp;1&nbsp;:
										<input type="text" id="pre" name="pre"/>
										-
										<input type="text" id="post" name="post"/>
										<input type="hidden" id="isvlidate" />
										<span id="bl_result" style="display: inline-block;margin-left: 15px;color: rgb(252,3,13);font-weight: bold;"></span>
										<div class="btn-group" data-toggle="buttons-checkbox" style="margin-left: 40px;">
											<button id="begin_validate" type="button" class="btn btn-primary">开启验证</button>
											<ul id="extra" class="search_condition">
												<li><span mark="attr_all" ></span>全部</li>
												<li><span></span>仅显示大于等于</li>
												<li><span></span>仅显示小于</li>
											</ul>
										</div>
										<ul class="js-bl radio first_radio">
											<li><span></span>按年数查询</li>
											<li><span></span>按年份查询</li>
										</ul>
										<ul class="js-bl radio">
											<li><span></span>1年</li>
											<li><span></span>2年</li>
											<li><span></span>3年</li>
										</ul>
										<ul id="year" class="js-bl search_condition" style="display:none;">
											<li class="no1"><span mark="attr_all"></span>全部</li>
											<li><span></span>2010年</li>
											<li><span></span>2011年</li>
											<li><span></span>2012年</li>
										</ul>
										<div id="alert">
											<span></span>
										</div>
									</div>
									<div class="beginSearch"><span>开始查询</span></div>
									
								</div>
								<table id="test">
										<thead>
											<tr>
												<th>学校名称</th>
												<th>操作</th>
												<th>学校地址</th>
												<th>学校性质</th>
												<th>学校类别</th>
												<th>备注</th>
												<th>录取率</th>
											</tr>
										</thead>
								</table>
							</div>
					</div>
				</div>
<script type="text/javascript" src="js/condition_search.js"></script>