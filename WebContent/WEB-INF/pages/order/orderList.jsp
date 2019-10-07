<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%><script src='/js/housing.js'></script>
 <c:if test="${requestScope.type==0}">
<script src='/js/secondOrderList.js?ver=001'></script>
</c:if>
<script src='/js/order.js?ver=001'></script>
 <c:if test="${requestScope.type==1}">
<script src='/js/tenementOrderList.js'></script>
</c:if>
 <c:if test="${requestScope.type==2}">
<script src='/js/newOrderList.js?ver=001'></script>
</c:if>
<div class="mainTitle">
<span><a href="/login/index.do" style="text-decoration: underline;">首页</a>&emsp;>&emsp;
${requestScope.allName}${requestScope.typeName}</span>
<input type="hidden" value="${requestScope.type}" id="type">
</div> 
 <div style="height: 30px; margin-left: 10px;">
 <c:if test="${requestScope.type!=1}">
 <a class="abtn" id="addorder">更新进度</a>&emsp;
 <a class="abtn" id="showOrderDetail">查看进度</a>&emsp;
 </c:if>
 <a class="abtn" id="showorder">查看订单</a>&emsp;<a class="abtn" id="delorder">删除</a>&emsp;<a class="abtn" id="imgorder">查看图片</a>
 </div>
 <div style="height: 100%;overflow: auto;">
<table id="orderListGridInfo" style="overflow: hidden;"></table>
<div id="orderListGridInfoPage"></div>
 </div>

 <div id="divPopUpBoxEdit" class="divPopUpBoxEdit">
<div class="divListTitle">更新进度</div>
 <ul>
 <li>
  <span>进度状态:</span>
 <select name="status">
 <option value="3">交易中</option>
 <option value="4">已完成</option>
 <option value="0">已取消</option>
 </select>
 </li>
 <li> 进度内容</li>
 <li> <span>进度内容:</span>
 <textarea rows="5" cols="30" name="orderDetails.scheduleContent"></textarea></li>
 <li>
<span>上传附件图片:</span>
<span style="text-align: center;">
<input type="text" id="imgUrl" readonly="readonly" class="imgFileInp" onchange="imageUpload(this);">
<a id="fileSelect" class="imgFileA">请选择</a>
<input type="file" name="imgFile" id="imgFile" class="imgFile" onchange="imageUpload(this);">
<span id="msg"></span>
</span>
</li>
 <li style="text-align: center;margin-top: 10px;">
 <input value="保存" id="saveUserInfo" class="orange" type="button">&emsp;
 <input id="closeedit" type="reset" class="orange" value="取消"></li>
 </ul>
 </div>
 <div id='editZhezhao' class="divZhezhao"></div>
 
 
 
 