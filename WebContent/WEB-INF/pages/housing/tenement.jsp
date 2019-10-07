<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<script src='/js/housing.js'></script>
<c:if test="${requestScope.type==0}">
 <script src='/js/${requestScope.del}/${requestScope.del}secondHand.js?ver=001'></script>
</c:if>
<c:if test="${requestScope.type==1}">
 <script src='/js/${requestScope.del}/${requestScope.del}tenement.js?ver=001'></script>
</c:if>
<c:if test="${requestScope.type==2}">
 <script src='/js/${requestScope.del}/${requestScope.del}newHouse.js?ver=001'></script>
</c:if>
<div class="mainTitle">
<span><a href="/login/index.do" style="text-decoration: underline;">首页</a>&emsp;>&emsp;
<c:if test="${requestScope.del!=''}">
 回收站 ->
</c:if>
${requestScope.allName}${requestScope.typeName}</span>
</div>
<div>
<%@include file="query.jsp" %>
 <div style="height: 30px; margin-left: 10px;">
 <c:if test="${requestScope.del!='del'}">
 <a class="abtn" id="addTenement">新增</a>&emsp;<a class="abtn" id="editTenement">修改</a>&emsp;
 <a class="abtn" id="delTenement">删除</a>&emsp;
 <a class="abtn" id="imgTenement">查看图片</a>
 &emsp;<a class="abtn" id="clientInfo">备注</a><!-- &emsp;<a class="abtn" id="publish">发布信息</a> -->
 <c:if test="${requestScope.type==1}">
 &emsp;<a class="abtn" id="hireHouseing">出租</a>
 </c:if>
 <c:if test="${requestScope.type!=1}">
 &emsp;<a class="abtn" id="saleHouseing">出售</a>
 </c:if>
 </c:if>
 <c:if test="${requestScope.del=='del'}">
 <a class="abtn" id="delHousing">删除</a>
 </c:if>
 </div>
 <div style="height: 100%;overflow: auto;">
<table id="tenementListGridInfo" style="overflow: hidden;"></table>
<div id="tenementListGridInfoPage"></div>
 </div>
</div>
<div class="divPopUpBoxEdit" id="infoShowDiv" style="font-size: 18px;">
<div class="divListTitle">备注</div>
<ul>
<li><span>&emsp;编号:</span><span id="clientNo"></span></li>
<li><span>&emsp;姓名:</span><span id="clientName"></span></li>
<li><span>&emsp;电话:</span><span id="clientPhone"></span></li>
<li><span>&emsp;备注:</span><span id="clientRemark"></span></li>
<li><span>经纪人:</span><span id="businessName"></span></li>
<li style="text-align: center;"><a class="abtn" id="closeInfoShow">关闭</a></li>
</ul>
</div>
<div class="divPopUpBoxEdit" id="publishDiv" style="font-size: 18px;">
<div class="divListTitle">信息发布</div>
<ul>
<li><span style="color: red;">本系统郑重保证只向公共服务平台提交以下数据,并且数据提交完全属于自愿!</span></li>
<li><span>将要发布的信息:&ensp;</span><span id="publishInfo"></span></li>
<li><span>信息来源:&ensp;</span><span id="userInfo">${sessionScope.loginName}</span><input type="hidden" name="type" value="${requestScope.type}"></li>
<li><span>联&ensp;系&ensp;人:&ensp;</span><input name="businessName" type="text" maxlength="10"></li>
<li><span>联系电话:&ensp;</span><input name="businessPhone" data-num="number" maxlength="11"></li>
<li><span>&emsp;&emsp;备注:&ensp;</span><textarea name="businessRemark" maxlength="50"></textarea></li>
<li style="text-align: center;"><button class="orange" id="publishBtn">发布</button>&emsp;<button class="orange" id="closePublish">关闭</button></li>
</ul>
</div>
