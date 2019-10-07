$(function(){
	var info={
			businessNameFlag:false,
			phoneFlag:false,
			emailFlag:true
	};
	var businessName=$("input[name='businessName']");
	var phone=$("input[name='phone']");
	var email=$("input[name='email']");
	var district=$("input[name='district']");
	var address=$("input[name='address']");
	businessName.bind("blur",function(){
		if (businessName.val().trim()=="") {
			$("#businessNameMsg").html("<font color=red>商家名称不能为空</font>");
			info.businessNameFlag=false;
		}else{
			info.businessNameFlag=true;
		}
	});
	phone.bind("input propertychange blur",function(){
		var reg = new RegExp("^[0-9]*$");
		if (phone.val().trim()=="") {
			$("#phoneMsg").html("<font color=red>电话号码不能为空</font>");
			info.phoneFlag=false;
		}else if (!reg.test(phone.val().trim())) {
			phone.val(phone.val().substring(0,phone.val().length-1));
		}else if (phone.val().trim().length<7) {
			$("#phoneMsg").html("<font color=red>电话号码小于最低7位!</font>");
			info.phoneFlag=false;
		}else{
			$.post("/business/checkData.do",{
				"obj":phone.val().trim()
				,"key":102,"math":Math.random()
			},function(data){
				if (data) {
					$("#phoneMsg").html("<font color=red>电话号码已经存在!</font>");
					info.phoneFlag=false;
				}else{
					$("#phoneMsg").html("");
					info.phoneFlag=true;
				}
			});	
		}
	});
	email.bind("blur",function(){
		var space = /^\s*$/;
		var RegExp = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
			if (email.val().trim() == "" || space.test(email.val())) {
				email.val("");
				info.emailFlag=true;
			} else if (!RegExp.test(email.val())) {
				$("#emailMsg").html("<font color=red>您输入的邮箱格式不正确</font>");
				info.emailFlag=false;
			}else {
				$("#emailMsg").html("");
				info.emailFlag=true;
				  $.post("/business/checkData.do",{
						"obj":email.val().trim()
						,"key":103,"math":Math.random()
					},function(data){
						if (data) {
							$("#emailMsg").html("<font color=red>邮箱已经存在!</font>");
							info.emailFlag=false;
						}else{
							$("#emailMsg").html("");
							emailFlag=true;
						}
					});	
			}
	});
	if ($("#level_li").length<=0) {
		$(".align-center").css("height","530px");		
	}var licenseFlag=true;
	var licenseFlag2=true;
	var licenseFlag3=true;
	var licenseFlag4=true;
	if ($("#license_li").length<=0) {
		$(".align-center").css("height","620px");		
	}else{
		$("#license1").bind("input propertychange blur",function(){
			if ($("#license1").val().trim().length==5) {
				$("#license2").focus();
				licenseFlag=true;
			}else if($("#license1").val().trim().length>0){
				licenseFlag=false;
			}else{
				licenseFlag=true;
			}
		});
		$("#license2").bind("input propertychange blur",function(){
			if ($("#license2").val().trim().length==4) {
				$("#license3").focus();
				licenseFlag2=true;
			}else if($("#license2").val().trim().length>0){
				licenseFlag2=false;
			}else{
				licenseFlag2=true;
			}
		});
		$("#license3").bind("input propertychange blur",function(){
			if ($("#license3").val().trim().length==4) {
				$("#license4").focus();
				licenseFlag3=true;
			}else if($("#license3").val().trim().length>0){
				licenseFlag3=false;
			}else{
				licenseFlag3=true;
			}
		});
		$("#license4").bind("input propertychange blur",function(){
			if ($("#license4").val().trim().length==3) {
				licenseFlag4=true;
			}else if($("#license4").val().trim().length>0){
				licenseFlag4=false;
			}else{
				licenseFlag4=true;
			}
		});
	}
	if ($("#level_li").length<=0&&$("#license_li").length<=0) {
		$(".align-center").css("height","525px");		
	}
	function checkData(){
		if (!info.businessNameFlag) {
			pop_up_box.showMsg("请检查商家名称!");
			return false;
		}
		if (!info.phoneFlag) {
			pop_up_box.showMsg("请检查电话号码!");
			return false;
		}
		if (!info.emailFlag) {
			pop_up_box.showMsg("请检查邮箱!");
			return false;
		}
		if (licenseFlag&&licenseFlag2&&licenseFlag3&&licenseFlag4) {
			
		}else{
			pop_up_box.showMsg("请输入完整注册码或者不输入");
		}
		return true;
	}
	$("#submitInfo").click(function(){
		var infoForm=$("#infoForm");
		$.post("/business/saveBusiness.do",infoForm.serialize(),function(data){
			pop_up_box.showMsg("保存成功将跳转到首页!",function(){
				window.location.reload();
			})
		});
	});
});