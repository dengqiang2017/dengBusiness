<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="divPopUpBoxEdit" id="divPopUpBoxEdit" style="width: 500px;">
<form id="userForm">
<div class="divListTitle">增加客户信息</div>
<ul style="margin: 0 5px;padding: 0 5px;">
<li><span>客户名称:</span><input name="username" ></li>
<li><span>客户电话:</span><input name="phone" maxlength="11"></li>
<li><span>客户类型:</span><select name="clientType">
<option value="0">房主/房东</option>
<option value="1">租客</option>
<option value="2">购房者</option>
<option value="3">开发商</option>
</select>
<input type="hidden" name="id"> </li>
<li><span>客户需求:</span>
<textarea name="demand" rows="5" cols="30" maxlength="90"></textarea></li>
<!-- <li><span>推荐房源:</span><input type="text" name="recommendedHousing"></li> -->
<!-- <li><span>已看房源:</span><input type="text" name="alreadyLookedHouse"></li> -->
<li><span>客户意见:</span>
<textarea rows="5" cols="30" name="customerComments"></textarea>
</li>
<li><span>&emsp;&emsp;备注:</span>
<textarea name="remark" rows="5" cols="30" maxlength="90"></textarea>
</li>
<li style="text-align: center;margin-top: 10px;margin-bottom: 5px;">
<button id="saveUserInfo" class="orange" type="button">保存</button>
&emsp;
<button id="closeedit" type="reset" class="orange">取消</button></li>
</ul>
</form>
</div>
<div id='editZhezhao' class="divZhezhao"></div>