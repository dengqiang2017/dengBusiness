<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<link href="/css/businessInfo.css" rel="stylesheet" type="text/css" />
<script src="/js/businessInfo.js" type="text/javascript"></script>
<div class="mainTitle">
<span><a href="/login/index.do" style="text-decoration: underline;">首页</a>&emsp;>&emsp;我的资料</span>
</div>
<div>
<form id="infoForm">
<ul class="business_ul">
				<li><span style="color: red;">*</span>商家名称:&ensp;<input name="businessName" value="${sessionScope.businessInfo.businessName}">&ensp;<span id="businessNameMsg"
					class="msg"></span></li>
				<li style="margin-left: 10px;">登&ensp;录&ensp;名:&ensp;${sessionScope.businessInfo.loginName}&ensp;<span id="loginNameMsg"
					class="msg"></span></li>
				<li><span style="color: red;">*</span>电话号码:&ensp;<input type="tel" name="phone" maxlength="11" value="${sessionScope.businessInfo.phone}">&ensp;<span id="phoneMsg"
					class="msg"><font style="color: rgb(197,197,197);">可以输入座机或者手机号</font></span></li>
				<li style="margin-left: 10px;">电子邮件:&ensp;<input name="email" value="${sessionScope.businessInfo.email}">&ensp;<span id="emailMsg"
					class="msg"></span></li>
				<li style="margin-left: 10px;">所在地区:&ensp;<input name="district" value="${sessionScope.businessInfo.district}">&ensp;<span id="districtMsg"
					class="msg"></span></li>
				<li style="margin-left: 10px;">详细地址:&ensp;<input name="address" value="${sessionScope.businessInfo.address}">&ensp;<span id="addressMsg"
					class="msg"></span></li>
<!-- 				<li style="margin-left: 10px;">所属门店:&ensp;<input name="store">&ensp;<span id="storeMsg" -->
<!-- 					class="msg"></span></li> -->
				<c:if test="${requestScope.businessinit||requestScope.levelSelect}">
				<li style="margin-left: 10px;" id="level_li">用户级别:&ensp;<select name="level"  id="level" style="width: 230px;height: 27px;">
				<c:if test="${sessionScope.businessInfo.level==0}">
				<option value="0" selected="selected">普通用户</option>
				</c:if>
				<c:if test="${sessionScope.businessInfo.level!=0}">
				<option value="0">普通用户</option>
				</c:if>
				<c:if test="${sessionScope.businessInfo.level==1}">
				<option value="1" selected="selected">管理员用户</option>
				</c:if>
				<c:if test="${sessionScope.businessInfo.level!=1}">
				<option value="1">管理员用户</option>
				</c:if>
				</select>&ensp;<span id="levelMsg" class="msg"><br><font style="color: rgb(197,197,197);">&emsp;选择管理员注册后其余注册用户将默认为普通用户</font></span></li>
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
				<input type="hidden" name="id" value="${sessionScope.businessInfo.id}">
				<span style="color: rgb(168,168,168);">带[<span style="color: red;">*</span>]表示为必填项,其余为选填项</span>
				</li>
				<li style="text-align: start;margin-top: 20px;margin-left: 100px;">
				<input type="button" id="submitInfo" value="保存" class="orange">&emsp;
				<input type="button" onclick="location.reload();" class="orange" value="取消"></li>
			</ul>
</form>
<br>
<br>
</div>