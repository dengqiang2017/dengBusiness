<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>${sessionScope.systemName}</title>
<meta name="Keywords" content="租房,出租,卖房,买房,新房" /> 
<meta name="author" content="有问题请到1376528532@qq.com留言" />
<meta name="Description"
	content="本系统由邓强(电话:18224052021,QQ:1376528532)制作完成，如果您要转载，请保留版权" />
<link href="/css/pageInit.css" rel="stylesheet" type="text/css" />
<link href="/css/layout.css" rel="stylesheet" type="text/css" />
<link href="/css/index.css" rel="stylesheet" type="text/css" />
<link href="/css/popUpBox.css" rel="stylesheet" type="text/css" />
<link href='/datepicker/skin/WdatePicker.css' rel='stylesheet' type="text/css"/>
<!-- <link rel='stylesheet' href='/css_lib/jquery-ui.css'/> -->
<link rel='stylesheet' href='/css_lib/jquery-ui-custom.css' type="text/css"/>
<link rel='stylesheet' href='/css_lib/ui.jqgrid.css' type="text/css"/>

<script type="text/javascript" src="/js_lib/jquery.js"></script>
<script type="text/javascript" src='/js_lib/jquery-ui.js'></script>  
<script type="text/javascript" src='/js_lib/grid.locale-cn.js'></script> 
<script type="text/javascript" src='/js_lib/jquery.jqGrid.js'></script>
<script type="text/javascript" src="/js/popUpBox.js"></script>
<script type="text/javascript" src="/js/index.js"></script>
<script type="text/javascript" src='/datepicker/WdatePicker.js'></script>
<style type="text/css">
#userForm ul li input{width: calc(100% - 75px);}
#userForm ul li select{width: calc(100% - 75px);}
#userForm ul li textarea{width: calc(100% - 75px);}
#userForm ul li span{margin-right: 5px;}
</style>
</head>
<body>
	<div id="container">
		<div id="header">
			<img id="logoUrl" src="${sessionScope.logoUrl}"/>
			 <span id="systemName">${sessionScope.systemName}</span>
			<div style="float: right; margin-right: 10px; margin-top: 2px;">
			<span style="color: rgb(182, 241, 249);">登录时间:${sessionScope.loginDate}</span>
				<span style="color: rgb(182, 241, 249);">用户:<a id="businessInfo" style="color: rgb(182, 241, 249);">${sessionScope.loginName}</a></span>&emsp; 
				<a href="#" style="color: rgb(182, 241, 249);">帮助</a>&emsp;
				<a href="#" style="color: rgb(182, 241, 249);">关于</a>&emsp;
				<c:if test="${sessionScope.businessInfo.level==1}">
				<a style="color: rgb(182, 241, 249);" id="setting">系统设置</a>&emsp;
				</c:if>
				<a style="color: rgb(182, 241, 249);" href="/login/exitLogin.do">重新登录</a>&emsp;
				<a style="color: rgb(182, 241, 249);" href="/login/exitLogin.do">退出</a>
			</div>
		</div> 
		<div id="mainContent">
			<div id="sidebar">
				<img src="/images/selectImg.png" style="width: 100%;" />
				<div class="divTitle" onclick="showTile(this);">
					业务管理 <img src="/images/up.png" class="imgUp" />
					<div id="find" class="items">
						<div id="find1" class="divMenu">出租房<img class="imgRight" src="/images/selectRight1.png"/></div>
						<div id="find2" class="divMenu">二手房<img class="imgRight" src="/images/selectRight1.png"/></div>
						<div id="find3" class="divMenu">新房<img class="imgRight" src="/images/selectRight1.png"/></div>
						<c:if test="${sessionScope.businessInfo.level==1}">
						<div id="find4" class="divMenu">公司所有出租房<img class="imgRight" src="/images/selectRight1.png"/></div>
						<div id="find5" class="divMenu">公司所有二手房<img class="imgRight" src="/images/selectRight1.png"/></div>
						<div id="find6" class="divMenu">公司所有新房<img class="imgRight" src="/images/selectRight1.png"/></div>
						</c:if>
					</div>
				</div>
				<div class="divTitle" onclick="showTile(this);">
					订单管理 <img src="/images/up.png" class="imgUp" />
					<div class="items">
						<div id="orderTenement" class="divMenu">出租房订单<img class="imgRight" src="/images/selectRight1.png"/></div>
						<div id="orderSecond" class="divMenu">二手房订单<img class="imgRight" src="/images/selectRight1.png"/></div>
						<div id="orderNew" class="divMenu">新房订单<img class="imgRight" src="/images/selectRight1.png"/></div>
						<div id="orderDel" class="divMenu">已删除订单<img class="imgRight" src="/images/selectRight1.png"/></div>
					 
						<c:if test="${sessionScope.businessInfo.level==1}">
						<div id="orderTenementAll" class="divMenu">公司所有出租房订单<img class="imgRight" src="/images/selectRight1.png"/></div>
						<div id="orderSecondAll" class="divMenu">公司所有二手房订单<img class="imgRight" src="/images/selectRight1.png"/></div>
						<div id="orderNewAll" class="divMenu">公司所有新房订单<img class="imgRight" src="/images/selectRight1.png"/></div>
						</c:if>
					</div>
				</div>
				<div class="divTitle" onclick="showTile(this);">
					数据统计 <img src="/images/up.png" class="imgUp" />
					<div class="items">
						<div id="find01" class="divMenu">出租房统计<img class="imgRight" src="/images/selectRight1.png"/></div>
						<div id="find02" class="divMenu">二手房统计<img class="imgRight" src="/images/selectRight1.png"/></div>
						<div id="find03" class="divMenu">新房统计<img class="imgRight" src="/images/selectRight1.png"/></div>
					</div>
				</div>
				<div class="divTitle" onclick="showTile(this);">
					系统维护 <img src="/images/up.png" class="imgUp" />
					<div class="items">
						<div id="dictionary" class="divMenu">字典管理<img class="imgRight" src="/images/selectRight1.png"/></div>
						<div id="clientMenu" class="divMenu">客户管理<img class="imgRight" src="/images/selectRight1.png"/></div>
						<div id="userMenu" class="divMenu">用户中心<img class="imgRight" src="/images/selectRight1.png"/></div>
						<div id="pwdMenu" class="divMenu">修改密码<img class="imgRight" src="/images/selectRight1.png"/></div>
					</div>
				</div>
				<div class="divTitle" onclick="showTile(this);">
					数据回收站 <img src="/images/up.png" class="imgUp" />
					<div class="items">
						<div id="find1Del" class="divMenu">出租房<img class="imgRight" src="/images/selectRight1.png"/></div>
						<div id="find2Del" class="divMenu">二手房<img class="imgRight" src="/images/selectRight1.png"/></div>
						<div id="find3Del" class="divMenu">新房<img class="imgRight" src="/images/selectRight1.png"/></div>
						<c:if test="${sessionScope.businessInfo.level==1}">
						<div id="find4Del" class="divMenu">公司所有出租房<img class="imgRight" src="/images/selectRight1.png"/></div>
						<div id="find5Del" class="divMenu">公司所有二手房<img class="imgRight" src="/images/selectRight1.png"/></div>
						<div id="find6Del" class="divMenu">公司所有新房<img class="imgRight" src="/images/selectRight1.png"/></div>
						</c:if>
					</div>
				</div>
			</div>
			<div id="content"> 
 				<div id="contentShow" ></div>
 				<div id="secondShow"></div>
			</div>
		</div>
		<div id="footer">
			@2015 dengqiang 版权所有 未经书面授权 不得复制和建立镜像 联系电话：18224052021
			QQ:1376528532  
		</div>
	</div>
	<div id='selectzhezhao' style='position:fixed;z-index:9991;width:100%;height:100%;background-color:silver;display:none;opacity:.5;  filter:Alpha(Opacity=50); top:0px;'></div>
</body>
</html>
