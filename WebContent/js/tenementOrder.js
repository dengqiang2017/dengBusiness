$(function(){
	$("input[name='actualMonthlyRent']").bind("input propertychange blur",function(){
		var reg = new RegExp("^[0-9]*$");
		if (!reg.test($(this).val().trim())) {
			$(this).val($(this).val().substring(0,$(this).val().length-1));
		}
	});
	$("#useradd").click(function(){
		showUserEdit();
	});
	$("#showOrder").click(function(){
		$("#orderTenement").click();
	});
	function checkData(){
		if ($.trim($("input[name='tenancy']").val())=="") {
			pop_up_box.showMsg("请输入租期!");
			return false;
		}
		if ($.trim($("input[name='actualMonthlyRent']").val())=="") {
			pop_up_box.showMsg("请输入租金!");
			return false;
		}
		if ($.trim($("input[name='client.id']").val())=="") {
			pop_up_box.showMsg("请选择一个客户或者重新输入一个!");
			return false;
		}
		return true;
	}
	$("#saveOrderInfo").click(function(){
		if (checkData()) {
			pop_up_box.postWait();
			$.post("/order/saveOrder.do",$("#orderForm").serialize(),function(data){
				pop_up_box.loadWaitClose();
				if (data.success) {
					$("#orderCompleteTitle").css("color","");
					$("#orderForm").hide();
					$("#orderCompleteDiv").show();
				}else{
					pop_up_box.showMsg("保存失败,请检查数据!"+data.msg);
				}
			});
		}
	});
	$("#continueOrder").click(function(){
		$("#tenementListGridInfo").trigger("reloadGrid");
		closeSencondDiv();
	});
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