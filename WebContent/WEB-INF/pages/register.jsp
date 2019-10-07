<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>用户注册</title>
<link href="/css/layout.css" rel="stylesheet" type="text/css" />
<link href="/css/popUpBox.css" rel="stylesheet" type="text/css" />
<link href="/css/register.css" rel="stylesheet" type="text/css" />
<script src="/js_lib/jquery.js" type="text/javascript"></script>
<script src="/js_lib/jquery.md5.js" type="text/javascript"></script>
<script src="/js/register.js" type="text/javascript"></script>
<script src="/js/popUpBox.js" type="text/javascript"></script>
</head>
<body style="overflow: auto;">
	<div class="align-center" style="height: 570px; ">
		<div class="titlename">用户注册</div>
		<form id="registerForm" action="/business/saveBusiness.do" method="post">
			<ul class="ulRegister">
				<li><span style="color: red;">*</span>商家名称:&ensp;<input name="businessName">&ensp;<span id="businessNameMsg"
					class="msg"></span></li>
				<li><span style="color: red;">*</span>登&ensp;录&ensp;名:&ensp;<input name="loginName">&ensp;<span id="loginNameMsg"
					class="msg"></span></li>
				<li><span style="color: red;">*</span>登录密码:&ensp;<input name="password" type="password">&ensp;<span id="passwordMsg"
					class="msg"></span></li>
				<li><span style="color: red;">*</span>确认密码:&ensp;<input name="confirmPwd" type="password">&ensp;<span id="confirmPwdMsg"
					class="msg"></span></li>
				<li><span style="color: red;">*</span>电话号码:&ensp;<input name="phone" maxlength="11" placeholder="可以输入座机或者手机号">&ensp;<span id="phoneMsg"
					class="msg"></span></li>
				<li style="margin-left: 10px;">电子邮件:&ensp;<input name="email">&ensp;<span id="emailMsg"
					class="msg"></span></li>
				<li style="margin-left: 10px;">所在地区:&ensp;<input name="district">&ensp;<span id="districtMsg"
					class="msg"></span></li>
				<li style="margin-left: 10px;">详细地址:&ensp;<input name="address">&ensp;<span id="addressMsg"
					class="msg"></span></li>
<!-- 				<li style="margin-left: 10px;">所属门店:&ensp;<input name="store">&ensp;<span id="storeMsg" -->
<!-- 					class="msg"></span></li> -->
				<c:if test="${requestScope.businessinit}">
				<li style="margin-left: 10px;" id="level_li">用户级别:&ensp;<select name="level"  id="level" style="width: 230px;height: 27px;">
				<option value="0">普通用户</option>
				<option value="1">管理员用户</option>
				</select>&ensp;
				<span id="levelMsg" class="msg"><br><font style="color: red;">&emsp;选择管理员注册后其余注册用户将默认为普通用户</font></span></li>
				</c:if>
				<c:if test="${!requestScope.businessinit}">
				<input type="hidden" value="0" name="level">
				</c:if>
				<c:if test="${requestScope.business_license}">
				<li style="margin-left: 3px;" id="license_li">
				<div>根据机器码:[${requestScope.mac}]获取注册码</div>
				&ensp;注&ensp;册&ensp;码:
				<input name="license_mac" id="license1" maxlength="5" style="width: 63px;">
				<input name="license_mac" id="license2"maxlength="4" style="width: 53px;">
				<input name="license_mac" id="license3" maxlength="4" style="width: 53px;">
				<input name="license_mac" id="license4" maxlength="3" style="width: 43px;">
				&ensp;<span id="licenseMsg"	class="msg"></span></li>
				</c:if>
				<li>
				<span style="color: rgb(168,168,168);">带[<span style="color: red;">*</span>]表示为必填项,其余为选填项</span>
				</li>	
				<li style="text-align: center;margin-top: 20px;">已有账号去[<a style="color:white;" href="/login/loginPage.do">登录</a>]&emsp;<input type="button" id="submitInfo" value="注册" class="orange">&emsp;<input class="orange" type="reset" value="重置"></li>
			</ul>
		</form>
	</div>
</body>
</html>