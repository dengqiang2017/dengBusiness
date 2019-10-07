<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<script src="/js_lib/jquery.md5.js"></script>
<div class="mainTitle">
<span><a href="/login/index.do" style="text-decoration: underline;">首页</a>&emsp;>&emsp;修改密码</span>
</div>
<div style="text-align: center;line-height: 40px;">
<form action="" id="pwdForm">
<div><span>&emsp;旧密码:</span><input type="password" name="oldPwd"><span id="old_psd_info" style="color: red;"></span></div>
<div><span>&emsp;新密码:</span><input type="password" name="pwd"><span id="new_psd_info" style="color: red;"></span></div>
<div><span>确认密码:</span><input type="password" name="confimPwd"><span id="confirm_psd_info" style="color: red;"></span></div>
<div  style="height: 40px;">
<input type="button" class="orange" value="保存" id="saveInfo">&emsp;
<input type="button" class="orange" onclick="window.location.reload();" value="返回">
<div style="clear:both;"></div>
</div>
</form>
</div>
<script type="text/javascript">
$(function(){
var newPassWord = $("input[name='pwd']");
var confirmPwd = $("input[name='confimPwd']");
var oldPassWord = $("input[name='oldPwd']");
var new_psd_info = $("#new_psd_info");
var confirm_psd_info = $("#confirm_psd_info");
var old_psd_info = $("#old_psd_info");
/////////////////////////
var oldPwdFlag=false;
var newPwdFlag=false;
var confirmPwdFlag=false;
////////////////
oldPassWord.bind("input propertychange blur", function() {
	if ($.trim(oldPassWord.val()) == "") {
		old_psd_info.html("请输入旧密码!");
		oldPwdFlag=false;
	}else{
		old_psd_info.html("<img src='"+"/images/selected.png'>");
		oldPwdFlag=true;
	}
});
newPassWord.bind("input propertychange blur", function() {
	if ($.trim(newPassWord.val()) == "") {
		new_psd_info.html("请输入新密码!"); 
		newPwdFlag=false;
	}else if ($.trim(newPassWord.val()).length<6) {
		newPwdFlag=false;
		new_psd_info.html("密码不能小于6位!");
	}else{
		new_psd_info.html("<img src='"+"/images/selected.png'>");
		newPwdFlag=true;
	}
});
confirmPwd.bind("input propertychange blur", function() {
	if ($.trim(confirmPwd.val()) == "") {
		confirm_psd_info.html("请输入确认密码!");
		confirmPwdFlag=false;
	}else{
		confirmPwdFlag=true;
	}
});
function check_new_confirm(){
	if ($.trim(confirmPwd.val()) != $.trim(newPassWord.val())) {
		confirm_psd_info.html("新密码与确认密码不一致!");
		confirmPwdFlag=false;
	}else{
		confirm_psd_info.html("<img src='"+"/images/selected.png'>");
		confirmPwdFlag=true;
	}
	return false;
}
confirmPwd.bind("change", function() {
	check_new_confirm();
});
oldPassWord.change(function(){
	$.post("/business/checkPassword.do",{"oldPwd":$.md5(oldPassWord.val())},function(data){
		if(data){
			old_psd_info.html("<img src='"+"/images/selected.png'>");
			oldPwdFlag=true;
		}else{
			old_psd_info.html("旧密码错误!");
			oldPwdFlag=false;
		}
	});
});
// ///////////////////////////////
$("#saveInfo").bind("click", function() {
	check_new_confirm();
	if(!oldPwdFlag){
		pop_up_box.showMsg("请检查旧密码:"+old_psd_info.html());
		return;
	}
	if(!newPwdFlag){
		pop_up_box.showMsg("请检查新密码:"+new_psd_info.html());
		return;
	}
	if(!confirmPwdFlag){
		pop_up_box.showMsg("请检查确认密码:"+confirm_psd_info.html());
		return;
	} 
	$.post( "/business/savePwd.do", {
		newPwd : $.md5(newPassWord.val()),
		confirmPwd : $.md5(confirmPwd.val()),
		oldPwd : $.md5(oldPassWord.val())
	}, function(data) {
		if (data.success) {
			alert("密码修改成功!请重新登录.");
			window.location.href =  "/login/exitLogin.do";
		} else {
			old_psd_info.html("旧密码不正确!");
			pop_up_box.showMsg(data.msg);		 
		}
	});
});
});
</script>