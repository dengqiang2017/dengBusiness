<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<link rel='stylesheet' href='/css/infoEdit.css?ver=001'/>
<link href="/css/settings.css?ver=001" rel="stylesheet" type="text/css" />
<script src="/js/ajaxfileupload.js" type="text/javascript"></script>
<script src='/js/infoEdit.js'></script>
<div class="mainTitle">
<span><a href="/login/index.do" style="text-decoration: underline;">首页</a>&emsp;>&emsp;
<a  style="text-decoration: underline;" onclick="closeSencondDiv();">${requestScope.upName}</a>&emsp;>&emsp;
<a>
<c:if test="${requestScope.operation=='add'}">
新增
</c:if>
<c:if test="${requestScope.operation=='edit'}">
修改
</c:if>
</a>
</span>
</div>
<div>
<div style="font-size: 14px;font-weight: bolder;color: tan;text-align: center;">房屋信息编辑</div>
<form action="" id="infoForm">
<ul style="margin-left: 10px;line-height: 30px;">
<li style="float: left;">
<ul style="line-height: 30px;">
<li><span>&emsp;&emsp;&emsp;区县:</span>
<select id="districtSelect">
<option value="0">区县不限</option>
<c:forEach items="${requestScope.districts}" var="districts">
<option value="${districts.id}">${districts.name}</option>
</c:forEach>
</select>
<c:if test="${empty requestScope.houseInfo.district.id}">
<input id="districtInput" name="district.name" class="selectIpt" value="区县不限">
</c:if>
<c:if test="${!empty requestScope.houseInfo.district.id}">
<input id="districtInput" name="district.name" class="selectIpt" value="${requestScope.houseInfo.district.id}">
</c:if>
</li>
<li><span>&emsp;&emsp;&emsp;板块:</span>
<select id="plateSelect">
<option value="0">板块不限</option>
<c:forEach items="${requestScope.plates}" var="plates">
<option value="${plates.id}">${plates.name}</option>
</c:forEach>
</select>
 <c:if test="${empty requestScope.houseInfo.plate.id}">
<input id="plateInput" name="plate.name" class="selectIpt" value="板块不限">
</c:if>
<c:if test="${!empty requestScope.houseInfo.plate.id}">
<input id="plateInput" name="plate.name" class="selectIpt" value="${requestScope.houseInfo.plate.id}">
</c:if>
</li>
<li><span>&emsp;&emsp;&ensp;<font style="color: red;">*</font>小区:</span>
<select id="communitySelect">
<option value="0">小区不限</option>
<c:forEach items="${requestScope.communitys}" var="communitys">
<option value="${communitys.id}">${communitys.name}</option>
</c:forEach>
</select>
 <c:if test="${empty requestScope.houseInfo.community.id}">
<input id="communityInput" name="community.name" class="selectIpt" value="小区不限">
</c:if>
<c:if test="${!empty requestScope.houseInfo.community.id}">
<input id="communityInput" name="community.name" class="selectIpt" value="${requestScope.houseInfo.community.id}">
</c:if>
</li>
<c:if test="${requestScope.type==1 }">
<li><span>&emsp;出租类型:</span>
<select id="rentTypeSelect">
<option  value="0">类型不限</option>
<c:forEach items="${requestScope.rentTypes}" var="rentTypes">
<option value="${rentTypes.id}">${rentTypes.name}</option>
</c:forEach>
</select>
 <c:if test="${empty requestScope.houseInfo.rentType.id}">
<input id="rentTypeInput" name="rentType.name" class="selectIpt" value="类型不限">
</c:if>
<c:if test="${!empty requestScope.houseInfo.rentType.id}">
<input id="rentTypeInput" name="rentType.name" class="selectIpt" value="${requestScope.houseInfo.rentType.id}">
</c:if>
</li>
</c:if>
<li><span>&emsp;&emsp;&ensp;<font style="color: red;">*</font>户型:</span>
<select id="houseTypeSelect">
<option  value="0">户型不限</option>
<c:forEach items="${requestScope.houseTypes}" var="houseTypes">
<option value="${houseTypes.id}">${houseTypes.name}</option>
</c:forEach>
</select>
 <c:if test="${empty requestScope.houseInfo.houseType.id}">
<input id="houseTypeInput" name="houseType.name" class="selectIpt" value="户型不限">
</c:if>
<c:if test="${!empty requestScope.houseInfo.houseType.id}">
<input id="houseTypeInput" name="houseType.name" class="selectIpt" value="${requestScope.houseInfo.houseType.id}">
</c:if>
</li>
<li><span>&emsp;&emsp;&ensp;<font style="color: red;">*</font>装修:</span>
<select id="decorationTypeSelect">
<option  value="0">装修不限</option>
<c:forEach items="${requestScope.decorationTypes}" var="decorationTypes">
<option value="${decorationTypes.id}">${decorationTypes.name}</option>
</c:forEach>
</select>
 <c:if test="${empty requestScope.houseInfo.decorationType.id}">
<input id="decorationTypeInput" name="decorationType.name" class="selectIpt" value="装修不限">
</c:if>
<c:if test="${!empty requestScope.houseInfo.decorationType.id}">
<input id="decorationTypeInput" name="decorationType.name" class="selectIpt" value="${requestScope.houseInfo.decorationType.id}">
</c:if>
</li>
<li><span>&ensp;<font style="color: red;">*</font>房源类型:</span>
<select id="housingTypeSelect">
<option  value="0">类型不限</option>
<c:forEach items="${requestScope.housingTypes}" var="housingTypes">
<option value="${housingTypes.id}">${housingTypes.name}</option>
</c:forEach>
</select>
 <c:if test="${empty requestScope.houseInfo.housingType.id}">
<input id="housingTypeInput" name="housingType.name" class="selectIpt" value="类型不限">
</c:if>
<c:if test="${!empty requestScope.houseInfo.housingType.id}">
<input id="housingTypeInput" name="housingType.name" class="selectIpt" value="${requestScope.houseInfo.housingType.id}">
</c:if>
</li>
<li><span>
<c:if test="${requestScope.type!=2}"><font style="color: red;">*</font>房主/房东:</c:if>
<c:if test="${requestScope.type==2}"><font style="color: red;">&emsp;&ensp;*</font>开发商:</c:if>
</span>
<a class="imgFileA" id="selectUser">选择客户</a>
<span id="userinfo">${requestScope.houseInfo.homeowner.username}-${requestScope.houseInfo.homeowner.phone}</span>
<input type="hidden" name="homeowner.id" id="homeownerId" value="${requestScope.houseInfo.homeowner.id}">
</li>
<li>
<span>上传房屋实景图片:</span> 
<span style="text-align: center;">
<input type="text" id="imgUrl" readonly="readonly" class="imgFileInp" onchange="imageUpload(this);">
<a id="fileSelect" class="imgFileA">请选择</a>
<input type="file" name="imgFile" id="imgFile" class="imgFile" onchange="imageUpload(this);">
<br>
<span id="msg"></span>
</span>
</li>
<li>
<ul id="imgUl" style="line-height: 22px; margin-left:17px; margin-top:10px;">
<c:forEach items="${requestScope.houseInfo.imgUrls}" var="img">
<li><a class="abtn" onclick="pop_up_box.showImg('${img.imgUrl}');">查看</a>&emsp;<a class="abtn" onclick="deleteImg('${img.imgUrl}',this);">删除</a>
<input type="hidden" name="imgUrls" value="${img.imgUrl}"></li>
</c:forEach>
</ul>
</li>
</ul>
</li>
<li style="float: left;margin-left:-20px;">
<ul style="line-height: 30px;">

<li><span>&ensp;<font style="color: red;">*</font>栋-单元:</span>
<input  name="building" style="width: 162px;" value="${requestScope.houseInfo.building}">&ensp;
<font style="color: rgb(197,197,197);" >xxx栋xxx单元xxx</font></li>

<li><span>&ensp;<font style="color: red;">*</font>楼层:</span>
<input  name="floor" style="width: 162px;" value="${requestScope.houseInfo.floor}"></li>

<li><span>&emsp;&emsp;&ensp;<font style="color: red;">*</font>面积:</span>
<input name="acreage" maxlength="10" style="width: 162px;" value="${requestScope.houseInfo.acreage}">&ensp;平米</li>
<c:if test="${requestScope.type!=1}">
<li><span>&emsp;&ensp;<font style="color: red;">*</font>总价格:</span>
<input name="totalPrice" data-number="num" maxlength="10" value="${requestScope.houseInfo.totalPrice}">&ensp;万元</li>
<li><span>&emsp;&emsp;&emsp;单价:</span>
<input name="unitPrice" data-number="num" maxlength="8" value="${requestScope.houseInfo.unitPrice}">&ensp;元</li>
</c:if>
<c:if test="${requestScope.type==1}">
<li><span>&emsp;&emsp;&ensp;<font style="color: red;">*</font>月租:</span>
<input name="monthlyRent" data-number="num" maxlength="8" value="${requestScope.houseInfo.monthlyRent}">&ensp;元</li>
</c:if>
<li><span>&emsp;&emsp;&emsp;朝向:</span>
<input name="orientation" style="width: 162px;" value="${requestScope.houseInfo.orientation}"></li>
<li><span>&emsp;修建时间:</span>
<input name="buildTime" class="Wdate" style="height: 20px;margin-left: -4px;width: 162px;" value="${requestScope.houseInfo.buildTime}" onclick="WdatePicker({'dateFmt':'yyyy'})">&ensp;年</li>
<li><span style="margin-top: 10px;">&emsp;&emsp;&emsp;备注:</span><textarea name="remark" rows="5" role="150">${requestScope.houseInfo.remark}</textarea> </li>
</ul>
</li>
</ul>
<div style="clear:both;"></div>
<div style="text-align: center;">
<input class="orange" value="保存" id="saveInfo" type="button"> 
<input class="orange" value="重置" type="reset"> 
<input class="orange" onclick="closeSencondDiv();" value="返回" type="button">
</div>
<input type="hidden" value="${requestScope.type}" name="type">
<input type="hidden" value="${requestScope.houseInfo.id}" name="id">
<div style="clear:both;"></div>
</form>
</div>
<%@include file="../dictionary/userSelect.jsp" %>
