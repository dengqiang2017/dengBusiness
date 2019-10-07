//判断对象是否是json格式 
function isJson(obj) {
	var json = typeof (obj) == "object"
			&& Object.prototype.toString.call(obj).toLowerCase() == "[object object]"
			&& !obj.length;
	return json;
}
function initNumInput(){
	$("input[data-number]").bind("input propertychange blur",function(){
		var reg = new RegExp("^[0-9]*$");
		if (!reg.test($(this).val().trim())) {
			$(this).val($(this).val().substring(0,$(this).val().length-1));
		}
	});
	$("input[data-num]").bind("input propertychange blur",function(){
		datanum(this);
	});
	$("input").bind("input propertychange blur",function(){
		$(this).val($.trim($(this).val()));
	});
}
/**
 *  只能输入数字,字母,下划线
 * @param t
 */
function datanum(t){
	var reg = new RegExp("^[0-9a-zA-Z_]{1,}$");
	var val=$(t).val();
	if (val&&!reg.test($.trim(val))) {
		$(t).val($.trim(val.substring(0,val.length-1)));
		datanum(t);
	}
}
// 判断是否已经登录
var loginname="";
var login_url= "/login/loginPage.do";
$.get( "/business/checkLogin.do?math="+Math.random(), function(data) {
	if (!data.success) {
		window.location.href =login_url;
	}else{
		loginname=data.loginName;
			$("#loginname_index").html("欢迎回来: "+data.loginName);
$.ajaxSetup({
	contentType : "application/x-www-form-urlencoded;charset=utf-8",
	complete : function(XMLHttpRequest, textStatus) {
		var responseText = XMLHttpRequest.responseText;
		if (responseText&&responseText.indexOf("<title>登录</title>") > 0) {
			window.location.href =  login_url;
		}
		if (responseText) {
			try {
				var sessionstatus = eval('(' + responseText + ')');
				if (sessionstatus && sessionstatus.sessionstatus == "timeout") {
					alert("会话超时,重新登录!");
					// 这里怎么处理在你，这里跳转的登录页面
					window.location.href =  login_url;
				}
			} catch (e) {
			}
		}
	}
});

$(function() {
	initNumInput();
	$("#content").css("min-height",$(document).height()-167);
	var index = $("#contentShow");
	var nowPage="information";
	function getHtml(url,param){
		if (!param) {
			param={};
		}
		pop_up_box.loadWait();
		$.get(url,param,function(data){
			pop_up_box.loadWaitClose();
			index.show();
			index.html("");
			$("#secondShow").html("");
			index.html(data);
		});
	}
	function loadHousingHtml(type,all,del){
		if (!all) {
			all="";
		}
		if (!del) {
			del="";
		}
		getHtml("/housingInfo/tenement.do",{"type":type,"all":all,"del":del});
	}
	function setCssSelect(find){
		$(".divMenu").css("background-color","white");
		$("#"+find).css("background-color","#a8c0db");
	}
	function setDelCss(find,type,all){
		setCssSelect(find);
		loadHousingHtml(type,all,"del");
	}
	function setCss(find,type,all){
		setCssSelect(find);
		loadHousingHtml(type,all);
	}
	///////////////////////
	$("#find1").click(function(){//0二手卖房,1--二手租房,2--新房
		setCss("find1",1);
	});
	$("#find2").click(function(){
		setCss("find2",0);
	});
	$("#find3").click(function(){
		setCss("find3",2);
	});
	////////////
	$("#find").parent().click();
	$("#find1").click();
	/////////系统维护///////
	$("#businessInfo,#userMenu").bind("click",function(){
		setCssSelect("userMenu");
		getHtml("/business.do");
	});
	$("#setting").click(function(){
		getHtml("/business/settings.do"); 
	});
	$("#clientMenu").click(function(){
		setCssSelect("clientMenu");
		getHtml("/user.do");
	});
	//////////密码/////////////////
	$("#pwdMenu").click(function(){
		setCssSelect("pwdMenu");
		getHtml("/business/pwd.do");
	});
	//////////////所有记录/////////////////////
	$("#find4").click(function(){//0二手卖房,1--二手租房,2--新房
		setCss("find4",1,"all");
	});
	$("#find5").click(function(){
		setCss("find5",0,"all");
	});
	$("#find6").click(function(){
		setCss("find6",2,"all");
	});
	$("#dictionary").click(function(){
		setCssSelect("dictionary");
		getHtml("/dictionary.do",{});
	});

	///////////////回收站/////////
	$("#find1Del").click(function(){//0二手卖房,1--二手租房,2--新房
		setDelCss("find1Del",1);
	});
	$("#find2Del").click(function(){
		setDelCss("find2Del",0);
	});
	$("#find3Del").click(function(){
		setDelCss("find3Del",2);
	});
	$("#find4Del").click(function(){//0二手卖房,1--二手租房,2--新房
		setDelCss("find4Del",1,"all");
	});
	$("#find5Del").click(function(){
		setDelCss("find5Del",0,"all");
	});
	$("#find6Del").click(function(){
		setDelCss("find6Del",2,"all");
	});
	//////////////订单相关//////////////
	function orderHtml(id,type,all){
		setCssSelect(id);
		getHtml("/order/orderList.do",{"type":type,"all":all});
	}
	$("#orderTenement").click(function(){
		orderHtml("orderTenement",1);
	});
	$("#orderSecond").click(function(){
		orderHtml("orderSecond",0);
	});
	$("#orderNew").click(function(){
		orderHtml("orderNew",2);
	});
	$("#orderTenementAll").click(function(){
		orderHtml("orderTenementAll",1,"all");
	});
	$("#orderSecondAll").click(function(){
		orderHtml("orderSecondAll",0,"all");
	});
	$("#orderNewAll").click(function(){
		orderHtml("orderNewAll",2,"all");
	});
	
});  
//搜索公共函数
function search(nowPage){
	var searchKey=encodeURIComponent($("#searchfield").val());
	if (searchKey!=null&&searchKey!="") {
		searchKey="?searchKey="+searchKey+"&math="+Math.random();
	}
	if (nowPage=="information") {
		$("#informationGridInfo").jqGrid("setGridParam",{page:1});
		$("#informationGridInfo").jqGrid("setGridParam",
				{url:"/businessUser/queryAllByLimit.do"+searchKey}).trigger("reloadGrid");
	}else if (nowPage=="merchantsList"&&$("#merchantPage").css("display")=="block"){
		$("#merchantContentGridInfo").jqGrid("setGridParam",{page:1});
		$("#merchantContentGridInfo").jqGrid("setGridParam",
				{url:"/businessUser/queryThreeInfo.do"+searchKey}).trigger("reloadGrid");
	}else if(nowPage="auditSms"){
		$("#sms_info").jqGrid("setGridParam",{page:1});
		if (searchKey=="") {
			$("#sms_info").jqGrid("setGridParam",
					{url:"/sms/AllNotAuditSmsRechar.do?e_auditingState=0"}).trigger("reloadGrid");
		}else{
			$("#sms_info").jqGrid("setGridParam",
					{url:"/sms/AllNotAuditSmsRechar.do"+searchKey+"&e_auditingState=0"}).trigger("reloadGrid");
		}
	}
}
function keyDownSearch(nowPage){
	$("#searchfield").bind("keydown",function(e){
		if (e.keyCode==13) {
			  search(nowPage);
		}
	});
	$("#search-btn").bind("click",function(){
		search(nowPage);
	});
}
 }}); 

function showTile(find){
	 $(".imgUp").attr("src","/images/up.png");
	 $(".items").hide();
	 if($(find).children("div").is(":hidden")){
		$(find).children("div").show();
		$(find).children(".imgUp").attr("src","/images/down.png");
	 }
}
function showUserEdit(){
	$("#divPopUpBoxEdit").show();
	$("#editZhezhao").show();
}
function hideUserEdit(){
	$("#divPopUpBoxEdit").hide();
	$("#editZhezhao").hide();
}