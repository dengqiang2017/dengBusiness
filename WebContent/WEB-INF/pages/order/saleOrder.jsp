<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<link rel='stylesheet' href='/css/infoEdit.css?ver=001'/>
<link href="/css/settings.css?ver=001" rel="stylesheet" type="text/css" />
<script src="/js/ajaxfileupload.js" type="text/javascript"></script>
<!-- <script src='/js/saleOrder.js'></script> -->
<script src='/js/useredit.js'></script>
<div class="mainTitle">
<span><a href="/login/index.do" style="text-decoration: underline;">首页</a>&emsp;>&emsp;
房屋出售</span>
</div>
<div style="text-align: center;font-size: 16px;font-weight: bold;color: red;">
<span>1.填写订单</span>&emsp;>&emsp;<span style="color: rgb(197,197,197);" id="orderTradeingTitle">2.交易中</span>
&emsp;>&emsp;<span style="color: rgb(197,197,197);" id="orderCompleteTitle">3.已完成</span>
</div>
<div style="margin-left: 30%;line-height: 30px;">
<form id="orderForm">
<ul>
<li><span>&ensp;房屋信息:</span><span>${requestScope.housingInfo.community.name}${requestScope.housingInfo.floor}</span></li>
<li><span>房主/房东:</span><a title="电话:${requestScope.housingInfo.homeowner.phone}">${requestScope.housingInfo.homeowner.username}</a></li>
<li><span>&ensp;初始总价:</span><font style="color: red;">${requestScope.housingInfo.monthlyRent}元</font></li>
<li><span>&ensp;实际总价:</span><input maxlength="10" style="color: red;" name="actualMonthlyRent" value="${requestScope.housingInfo.monthlyRent}">元</li>
<li><span>购房者名称:</span><select name="client.id">
<option value=""></option>
<c:forEach items="${requestScope.clients}" var="client">
<option value="${client.id}">${client.username}</option>
</c:forEach>
</select>&emsp;<a class="abtn">增加一个</a></li>
<li><span>&emsp;&emsp;&ensp;备注:</span><textarea name="remark" rows="5" cols="30"></textarea><input type="hidden" value="${requestScope.housingInfo.id}" name="housingInfo.id"> </li>
<li>
<span>上传合同图片:</span>
<span style="text-align: center;">
<input type="text" id="imgUrl" readonly="readonly" class="imgFileInp" onchange="imageUpload(this);">
<a id="fileSelect" class="imgFileA">请选择</a>
<input type="file" name="imgFile" id="imgFile" class="imgFile" onchange="imageUpload(this);">
<span id="msg"></span>
</span>
</li>
<li>
<ul id="imgUl" style="line-height: 22px; margin-left:17px; margin-top:10px;">
<c:forEach items="${requestScope.imgUrls}" var="img">
<li><a class="abtn" onclick="pop_up_box.showImg('${img.imgUrl}');">查看</a>&emsp;<a class="abtn" onclick="deleteImg('${img.imgUrl}',this);">删除</a>
<input type="hidden" name="imgUrls" value="${img.imgUrl}"></li>
</c:forEach>
</ul>
</li>
<li style="margin-left: 15%;height: 50px;margin-top: 20px;">
<input type="button" class="orange" value="提交订单" id="saveOrderInfo">&emsp;&emsp;
<input class="orange" type="reset" value="取消订单" onclick="closeSencondDiv();"></li>
</ul>
</form>
</div>
<div style="display: none;text-align: center;height: 30px;" id="orderCompleteDiv">
<span>订单创建完成,<a class="abtn" id="continueOrder">继续创建订单</a>&emsp;<a class="abtn" id="showOrder">查看订单</a></span>
</div>
<%@include file="../dictionary/useredit.jsp" %>