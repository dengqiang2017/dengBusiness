$(function(){
	$("#saveUserInfo").click(function(){
		var username=$("#divPopUpBoxEdit").find("input[name='username']").val();
		var phone=$("#divPopUpBoxEdit").find("input[name='phone']").val();
		var id=$("#divPopUpBoxEdit").find("input[name='id']").val();
		if ($.trim(username)=="") {
			pop_up_box.showMsg("客户名称不能为空!");
			return;
		}
		if ($.trim(phone)=="") {
			pop_up_box.showMsg("客户电话不能为空!");
			return;
		}
		pop_up_box.postWait();
		$.post("/user/saveUserInfo.do",{
			"id":id,
			"username":username,
			"phone":phone,
			"demand":$("#divPopUpBoxEdit").find("textarea[name='demand']").val(),
			"customerComments":$("#divPopUpBoxEdit").find("textarea[name='customerComments']").val(),
			"remark":$("#divPopUpBoxEdit").find("textarea[name='remark']").val(),
			"clientType":$("#divPopUpBoxEdit").find("select[name='clientType']").val()
		},function(data){
			pop_up_box.loadWaitClose();
			if (data.success) {
				$("#homeownerSelect").append("<option value='"+data.msg+"'>"+username+"</option>");
				$("#homeownerSelect").val(data.msg);
				if ($("#homeownerInput").length>0) {
					$("#homeownerInput").val(data.msg);
				}
				hideUserEdit();
				if ($("#usernameFind").length>0) {
					$("#usernameFind").click();
				}
			}else{
				pop_up_box.showMsg("保存失败请检查数据!"+data.msg);
			}
		});
	});
	$("#closeedit").click(function(){
		hideUserEdit();
	});
	$("input[name='phone']").bind("input propertychange blur",function(){
		var reg = new RegExp("^[0-9]*$");
		if (!reg.test($(this).val().trim())) {
			$(this).val($(this).val().substring(0,$(this).val().length-1));
		}
	});
});
