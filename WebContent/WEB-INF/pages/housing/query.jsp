<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<div style="line-height: 30px;margin-left: 10px;">
<select style="width: 100px;" name="district">
<option value="0">区县不限</option>
<c:forEach items="${requestScope.districts}" var="districts">
<option value="${districts.id}">${districts.name}</option>
</c:forEach>
</select>
<c:if test="${requestScope.type==1 }">
<select name="rentType">
<option  value="0">类型不限</option>
<c:forEach items="${requestScope.rentTypes}" var="rentTypes">
<option value="${rentTypes.id}">${rentTypes.name}</option>
</c:forEach>
</select>
</c:if>
<select name="houseType">
<option  value="0">户型不限</option>
<c:forEach items="${requestScope.houseTypes}" var="houseTypes">
<option value="${houseTypes.id}">${houseTypes.name}</option>
</c:forEach>
</select>
<select name="decorationType">
<option  value="0">装修不限</option>
<c:forEach items="${requestScope.decorationTypes}" var="decorationTypes">
<option value="${decorationTypes.id}">${decorationTypes.name}</option>
</c:forEach>
</select>
<select name="status">
<option  value="-1">房屋状态不限</option>
<c:if test="${requestScope.type==1}">
<option  value="0">未出租</option>
<option  value="1">已出租</option>
</c:if>
<c:if test="${requestScope.type==0||requestScope.type==2}">
<option  value="0">未卖出</option>
<option  value="2">已预订</option>
<option  value="3">交易中</option>
<option  value="4">已卖出</option>
</c:if>
</select>
<input placeholder="请输入关键字" name="searchKey">
<input type="button" class="orange" value="查询" id="queryData">
<div style=" ">
<c:if test="${requestScope.type==1}">
<span>栋-单元:<input type="text" name="building"></span>
<span>楼层:<input type="text" maxlength="3" data-num="num" name="floor"></span>
<span>
租金:<input type="text" maxlength="5" name="monthlyRent" data-num="num">~<input type="text" maxlength="5"  name="monthlyRent2" data-num="num">
</span>
</c:if>
<c:if test="${requestScope.type==0||requestScope.type==2}">
<span>
总价:<input type="text" maxlength="8" name="totalPrice" data-num="num">~<input type="text" maxlength="8"  name="totalPrice2" data-num="num">
</span>
</c:if>
<span>面积:<input type="text" maxlength="6" data-num="num" name="acreage">~<input name="acreage2" type="text" maxlength="6"  data-num="num"></span>
<span>年代:<input type="text" maxlength="4" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy'})" data-num="num" name="buildTime" style="height: 20px;">
</span>
<input type="hidden" value="${requestScope.type}" name="type">
<input type="hidden" value="${requestScope.all}" name="all">
<input type="hidden" value="${requestScope.del}" name="delFlag">
</div>
</div>