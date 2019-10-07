<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- <link href="/css/settings.css?ver=001" rel="stylesheet" type="text/css" /> -->
<script src="/js/userinfo.js" type="text/javascript"></script>
<script src="/js/useredit.js" type="text/javascript"></script>
<div class="mainTitle">
<span><a href="/login/index.do" style="text-decoration: underline;">首页</a>&emsp;>&emsp;客户管理</span>
</div>
 <div style="height: 30px; margin-left: 10px;">
	<input placeholder="请输入客户姓名关键字" id="searchKey">&emsp;
		<select id="clientType">
	<option value="-1">所有</option>
	<option value="0">房主/房东</option>
	<option value="1">租客</option>
	<option value="2">购房者</option>
	</select>
	<input type="button" class="orange" id="usernameFind" value="查&ensp;询">&emsp;
 <a class="abtn" id="adduser">新增</a>&emsp;<a class="abtn" id="edituser">修改</a>&emsp;
<!--  <a class="abtn" id="deluser">删除</a>  -->
</div>
<div style="overflow: auto;width: inherit;">
<table id="userListGridInfo" style="overflow: hidden;"></table>
</div>
<div id="userListGridInfoPage"></div>

<%@include file="useredit.jsp" %>