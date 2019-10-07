<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<script src="/js/dictionaryEdit.js" type="text/javascript"></script>
<div class="mainTitle">
<span><a href="/login/index.do" style="text-decoration: underline;">首页</a>&emsp;>&emsp;
<a  style="text-decoration: underline;" onclick="closeSencondDiv();">字典管理</a>&emsp;>&emsp;
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
<div style="text-align: center;">
<div style="font-size: 14px;font-weight: bolder;color: tan;text-align: center;">字典明细编辑</div>
	<form action="" id="dictionaryForm">
		<ul style="margin-left: 10px;line-height: 30px;">
			<li><span>&emsp;&emsp;名称:</span><input name="name"
				value="${requestScope.dictionary.name}"></li>
			<li><span>&emsp;&emsp;排序:</span><input name="sorting" type="number" placeholder="输入优先级数字,数字越大优先级越大"
				value="${requestScope.dictionary.sorting}"></li>
			<li><span>所属类别:</span><select id="dictionarySelect" name="dictionary.id">
					<c:forEach items="${requestScope.dictionarys}" var="dictionary">
						<option value="${dictionary.id}">${dictionary.name}</option>
					</c:forEach>
			</select> <input type="hidden" value="${requestScope.dictionary.dictionary.id}" name="dictionary.id"></li>
			<li><span>&emsp;&emsp;备注:</span><input name="remark"
				value="${requestScope.dictionary.remark}"></li>
				<li style="height: 40px;">
				<input type="button" class="orange" value="保存" id="saveInfo">&emsp;
				<input type="reset" class="orange" value="重置">&emsp;
				<input type="button" class="orange" onclick="closeSencondDiv();" value="返回">
				</li>
		</ul>
		<input type="hidden" value="${requestScope.dictionary.id}" name="id"> 
		<div style="clear:both;"></div>
	</form>
</div>