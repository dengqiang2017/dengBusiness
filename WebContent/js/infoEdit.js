$(function(){
	$("#content").css("min-height",$(document).height()-167);
	var district=$("#secondShow").find("select[name='district']");
	var plate=$("#secondShow").find("select[name='plate']");
	var community=$("#secondShow").find("select[name='community']");
	var rentType=$("#secondShow").find("select[name='rentType']");
	var houseType=$("#secondShow").find("select[name='houseType']");
	var decorationType=$("#secondShow").find("select[name='decorationType']");
	//1.先获取选中的值,
	//2.对比是否是0
	//3.不是就在数据加载完成后,设置默认值选中
	function setSelectVal(iptId,selectId){
		var select=$("#"+selectId);
		var ipt=$("#"+iptId);
		var val=ipt.val();
		if (!isNaN(val)) {//判断是不是数字
			select.val(val);
			if (val.length>0) {
				ipt.val(select.find("option:selected").text());
			}
		}
		select.bind("change",function(){
			ipt.val(select.find("option:selected").text());
		});
//		select.bind("focus",function(){
//			ipt.focus();
//		});
	}
	function selectInit(){
		setSelectVal("districtInput", "districtSelect");
		setSelectVal("plateInput", "plateSelect");
		setSelectVal("communityInput", "communitySelect");
		setSelectVal("rentTypeInput", "rentTypeSelect");
		setSelectVal("houseTypeInput", "houseTypeSelect");
		setSelectVal("decorationTypeInput", "decorationTypeSelect");
		setSelectVal("housingTypeInput", "housingTypeSelect");
	}
	selectInit(); 
	$("input[data-number]").bind("input propertychange blur",function(){
		var reg = new RegExp("^[0-9]*$");
		if (!reg.test($(this).val().trim())) {
			$(this).val($(this).val().substring(0,$(this).val().length-1));
		}
	});
	$("input[name='acreage']").bind("input propertychange blur",function(){
		var reg = new RegExp(/\d+(\.\d{0,2})?/);
//		var reg = new RegExp(/^\d+(\.\d{2})?$/);
		this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0];
		if (!reg.test($(this).val().trim())) {
			$(this).val($(this).val().substring(0,$(this).val().length-1));
		}
	});
	$("#saveInfo").bind("click",function(){
		if (!checkData()) {
			return;
		}
		pop_up_box.postWait();
		$.post("/housingInfo/saveHousing.do",$("#infoForm").serialize(),function(data){
			pop_up_box.loadWaitClose();
			if (data.success) {
				pop_up_box.showMsg("数据保存成功!",function(){
					$("#secondShow").html("");
					$("#queryData").click();
					$("#contentShow").show();
					$("#tenementListGridInfo").trigger("reloadGrid");
				});
			}else{
				pop_up_box.showMsg(data.msg);
			}
		});
	});
function checkData(){
	var districtInput=$("#districtInput");
	if (districtInput.val().indexOf("不限")>0) {
		districtInput.val("");
	}
	var plateInput=$("#plateInput");
	if (plateInput.val().indexOf("不限")>0) {
		plateInput.val("");
	}
	var communityInput=$("#communityInput");
	if ($.trim(communityInput.val())==""||communityInput.val().indexOf("不限")>0) {
		communityInput.val("");
		pop_up_box.showMsg("请选择小区或者重新输入一个!");
		return false;
	}
	var rentTypeInput=$("#rentTypeInput");
	if (rentTypeInput.length>0&&rentTypeInput.val().indexOf("不限")>0) {
		rentTypeInput.val("");
	}
	var houseTypeInput=$("#houseTypeInput");
	if ($.trim(houseTypeInput.val())==""||houseTypeInput.val().indexOf("不限")>0) {
		houseTypeInput.val("");
		pop_up_box.showMsg("请选择户型或者重新输入一个!");
		return false;
	}
	var decorationTypeInput=$("#decorationTypeInput");
	if ($.trim(decorationTypeInput.val())==""||decorationTypeInput.val().indexOf("不限")>0) {
		decorationTypeInput.val("");
		pop_up_box.showMsg("请选择装修类型或者重新输入一个!");
		return false;
	}
	var housingTypeInput=$("#housingTypeInput");
	if ($.trim(housingTypeInput.val())==""||housingTypeInput.val().indexOf("不限")>0) {
		housingTypeInput.val("");
		pop_up_box.showMsg("请选择房源类型或者重新输入一个!");
		return false;
	}
	var homeownerSelect=$("#homeownerId");
	if (homeownerSelect.val()==0) {
		pop_up_box.showMsg("请选择房主/房东或者重新输入一个!");
		return false;
	}
	var floor=$("#secondShow").find("input[name='floor']").val();
	if ($.trim(floor)=="") {
		pop_up_box.showMsg("请输入所在小区的单元楼层!");
		return false;
	}
	var acreage=$("#secondShow").find("input[name='acreage']");
	if (acreage.length>0) {
		if ($.trim(acreage.val())=="") {
			pop_up_box.showMsg("请输入面积!");
			return false;
		}
	}
	var monthlyRent=$("#secondShow").find("input[name='monthlyRent']");
	if (monthlyRent.length>0) {
		if ($.trim(monthlyRent.val())=="") {
			pop_up_box.showMsg("请输入月租!");
			return false;
		}
	}
	var totalPrice=$("#secondShow").find("input[name='totalPrice']");
	if (totalPrice.length>0) {
		if ($.trim(totalPrice.val())=="") {
			pop_up_box.showMsg("请输入总价!");
			return false;
		}
	}
	return true;
}
////////选择用户信息---begin///////
$("#selectUser").click(function(){
	$("#selectUserDiv,#selectzhezhao").show();
	if ($("#userinfoList").val()=="") {
		$.get("/user/userinfoselect.do",function(data){
			$("#userinfoList").html(data);
		});
	}
});
});
