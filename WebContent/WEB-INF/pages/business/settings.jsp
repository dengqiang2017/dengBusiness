<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<link href="/css/businessInfo.css" rel="stylesheet" type="text/css" />
<link href="/css/settings.css?ver=002" rel="stylesheet" type="text/css" />
<script src="/js/ajaxfileupload.js" type="text/javascript"></script>
<div class="mainTitle">
<span><a href="/login/index.do" style="text-decoration: underline;">首页</a>&emsp;>&emsp;系统设置</span>
</div>
<div style="line-height: 35px;text-align: center;">
<div><span>系统名称:&emsp;</span><input name="systemname" value="${requestScope.systemName}"></div>
<div><span>水印文字:&emsp;</span><input name="textWatermark" value="${requestScope.textWatermark}"></div>
<div style="max-height: 300px;">
<span style="margin-right: 230px;">系统logo:&emsp;</span><br>
<img alt="logo" id="logoImg" src="${requestScope.logo_url}" style="max-height:130px;max-width: 200px;">
</div>
<div style="text-align: center;">
<input type="text" id="imgUrl" readonly="readonly" class="imgFileInp" onchange="imageUpload(this);">
<a id="fileSelect" class="imgFileA">请选择</a>
<input type="file" name="imgFile" id="imgFile" class="imgFile" onchange="imageUpload(this);">
<span id="logoMsg"></span>
</div>
<div style="height: 70px; margin-top: 13px;">
<input value="保存" type="button" class="orange" id="saveSetting">&emsp;
<input value="取消" type="button" class="orange" onclick="location.reload();">
</div>
</div>
<script>
function imageUpload(t){
	ajaxUploadFile({
		"uploadUrl":"/upload/uploadImage.do?type=logo",
		"msgId":"logoMsg",
		"fileId":"imgFile",
		"msg":"logo图片",
		"fid":"imgUrl"
	},t,function(){
		pop_up_box.loadWaitClose();
		$("#logoImg").attr("src","/temp/"+$("#imgUrl").val());
	});
}
$(function(){
$("#saveSetting").click(function(){
	var imgUrl=$("#imgUrl");
	var url=$("#logoImg").attr("src");
	if(imgUrl.val()==""){
		url="";
	}
	$.post("/business/saveSettings.do",{
		"systemname":$("input[name='systemname']").val(),
		"logourl":url,
		"textWatermark":$("input[name='textWatermark']").val()
	},function(data){
		if(data.success){
			$("#logoUrl").attr("src",$("#logoImg").attr("src"));
			$("#systemName").html($("#systemname").val());
			pop_up_box.showMsg("数据保存成功!",function(){
				window.location.reload();
			});
		}
	});
}); 
});

</script>