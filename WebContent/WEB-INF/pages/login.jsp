<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
<link href="/css/layout.css" rel="stylesheet" type="text/css" />
<link href="/css/login.css" rel="stylesheet" type="text/css" />
<script src="/js_lib/jquery.js" type="text/javascript"></script>
<script src="/js_lib/jquery.cookie.js"></script>
<script src="/js_lib/jquery.md5.js"></script>
<script src="/js/login.js" type="text/javascript"></script>
</head>
<body>
<div class="align-center">
<div class="titlename">${requestScope.systemName}</div>
<c:if test="${!requestScope.business_license}">
</c:if>
<img src="/images/bjin2.png" style="float: left;margin-top: 20px;">
<ul class="ulLogin">
<li>
<span>用户名:</span>&ensp;<input style="font-size: 15px;" name="loginName">&ensp;<a href="/business/register.do" style="font-weight: normal;">注册新账号</a>
</li>
<li>
<span>密&emsp;码:</span>&ensp;<input style="font-size: 15px;" name="pwd" type="password">
</li>
<li  style="text-align: center;">
<!-- <a id="login"  class="button orange">登录</a> -->
<a id="loginInfo"  class="button blue" style="margin-top: 10px;">登录</a><br>
<span id="errorMsg" style="color: red;"></span>
</li>
</ul>
</div>
 
</body>
</html>