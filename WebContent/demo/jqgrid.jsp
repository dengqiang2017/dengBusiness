<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel='stylesheet' href='/css_lib/jquery-ui.css?ver=002'/>
<link rel='stylesheet' href='/jqgrid/css/ui.jqgrid.css?ver=002'/>
<!-- <link rel='stylesheet' href='/jqgrid/plugins/ui.multiselect.css?ver=002'/> -->
<script type="text/javascript" src="/js_lib/jquery.js?ver=002"></script>
<script src='/js_lib/jquery-ui.js?ver=002'></script>  
<!-- <script src='/js_lib/jquery.layout.js?ver=002'></script>  -->
<script src='/jqgrid/js/i18n/grid.locale-cn.js?ver=002'></script> 
<script type="text/javascript">
	$.jgrid.no_legacy_api = true;
	$.jgrid.useJSON = true;
</script>

<script src='/jqgrid/js/jquery.jqGrid.min.js?ver=002'></script> 
<!-- <script src='/jqgrid/plugins/ui.multiselect.js?ver=002'></script> -->
<!-- <script src='/jqgrid/plugins/jquery.contextmenu.js?ver=002'></script>  -->

</head>
<body>
<!-- 'north' & 'south' are children of body --> 
<table id="list2"></table>
<div id="pager2"></div>
<script type="text/javascript">
$(function(){
jQuery("#list2").jqGrid({
   	url:'jqgrid.json',
	datatype: "json",
   	colNames:['Inv No','Date', 'Client', 'Amount','Tax','Total','Notes'],
   	colModel:[
   		{name:'id',index:'id', width:55},
   		{name:'invdate',index:'invdate', width:90},
   		{name:'name',index:'name asc, invdate', width:100},
   		{name:'amount',index:'amount', width:80, align:"right"},
   		{name:'tax',index:'tax', width:80, align:"right"},		
   		{name:'total',index:'total', width:80,align:"right"},		
   		{name:'note',index:'note', width:150, sortable:false}		
   	],
   	rowNum:10,
   	rowList:[10,20,30],
   	pager: '#pager2',
   	sortname: 'id',
    viewrecords: true,
    sortorder: "desc",
    caption:"JSON Example"
});
jQuery("#list2").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false});
});
</script>
</body>
</html>