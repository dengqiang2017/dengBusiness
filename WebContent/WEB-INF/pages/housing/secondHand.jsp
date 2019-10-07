<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<link href="/css/businessInfo.css" rel="stylesheet" type="text/css" />
<link rel='stylesheet' href='/css_lib/jquery-ui.css'/>                
<link rel='stylesheet' href='/css_lib/ui.jqgrid.css'/>
<script src='/js_lib/jquery-ui.js'></script>  
<script src='/js_lib/grid.locale-cn.js'></script> 
<script src='/js_lib/jquery.jqGrid.js'></script> 
<script src='/js/housing.js'></script> 
<script src='/js/secondHand.js'></script> 
<div>
<span style="font-size: 16px;"><a href="/login/index.do">首页</a>->二手房</span>
</div>
<div style="height: 300px;">
<%@include file="query.jsp" %>
<div  id="secondHandList" style="border:1px solid #000;overflow-x: auto;width: auto;">
<table id="secondHandListGridInfo"></table>
<div id="secondHandListGridInfoPage"></div>
</div>
</div>