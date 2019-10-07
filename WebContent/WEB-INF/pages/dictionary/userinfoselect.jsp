<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="/js/userinfoselect.js" type="text/javascript"></script>
<script src="/js/useredit.js" type="text/javascript"></script>
<div style="height: 30px; margin-left: 10px;">
	<input placeholder="请输入客户姓名关键字" id="searchKey">&emsp;
	<select id="clientType">
	<option value="-1">所有</option>
	<option value="0">房主/房东</option>
	<option value="1">租客</option>
	<option value="2">购房者</option>
	</select>&emsp;
	<input type="button" class="orange" id="usernameFind" value="查&ensp;询">&emsp;<br>
</div>
<div style="margin-top: 10px;height: 30px;margin-left: 10px;">
 <a class="abtn" id="adduser">新增</a>&emsp;<a class="abtn" id="edituser">修改</a>&emsp;
<!--  <a class="abtn" id="deluser">删除</a>  -->
</div>
<table id="userListGridInfo" style="overflow: hidden;"></table>
<div id="userListGridInfoPage"></div>
<%@include file="useredit.jsp" %>