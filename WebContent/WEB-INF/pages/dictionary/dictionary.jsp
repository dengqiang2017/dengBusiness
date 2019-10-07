<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel='stylesheet' href='/css_lib/zTreeStyle.css' />
<link rel='stylesheet' href='/css/dictionary.css' />
<script src='/js_lib/jquery.ztree.core-3.5.js'></script>
<script type="text/javascript" src="/js_lib/jquery.ztree.excheck-3.5.min.js"></script>
<script src='/js/dictionary.js'></script>
<div class="mainTitle">
	<span><a href="/login/index.do"
		style="text-decoration: underline;">首页</a>&emsp;>&emsp;字典管理</span>
</div>
<div style="float: left; width: 300px;border: solid rgb(166,201,226) 1px;">
	<div  class="divListTitle">字典类别</div>
	<ul id="treeDictionary" class="ztree"></ul>
</div>
<div style="float: left; width: 422px;border: solid rgb(166,201,226) 1px;">
	<div class="divListTitle">字典明细类别</div>
	<div style="height: 30px;line-height: -moz-block-height;margin-left: 2px;">
		<a class="abtn" id="dictionaryAdd">添加</a>&emsp;<a class="abtn"
			id="dictionaryEdit">修改</a> 
<!-- 			&emsp;<a class="abtn" id="dictionaryDel">删除</a> -->
	</div>
	<table id="dictionaryGrid" style="overflow: hidden;"></table>
	<div id="dictionaryGridPage"></div>
</div>
<div style="clear: both;"></div>
