if (window != top) {
	top.location.href = contextPath + "/login.html";
}
$(function() {
	$(document).keydown(function(e) {
		if (e.keyCode == 13) {
			$("#loginInfo").click();
		}
	});
	var loginname = $("input[name='loginName']");
	var password = $("input[name='pwd']");
//	var validCode = $("#validCode");
//	var validCodeInput = $("#validCodeInput");
	var errorMsg = $("#errorMsg");
	loginname.val($.cookie("loginName"));
//	validCode.bind("click", function() {
//		validCode.attr("src", 'login/identifyingCode.do?it='
//				+ Math.random());
//	});
	if (loginname.val()!="") {
		password.focus();
	}else{
		loginname.focus();
	}
	function login_check(val, msg) {
		if ($.trim(val) != "") {
			errorMsg.hide();
			return true;
		} else {
			errorMsg.text(msg);
			errorMsg.show();
			return false;
		}
	}
	function loginname_check() {
		// loginname.focus();
		return login_check(loginname.val(), "用户名不能为空!");
	}
	loginname.blur(function() {
		loginname_check();
	});
	function password_check() {
		if ($.trim(password.val()) == "") {
			errorMsg.text("密码不能为空!");
			errorMsg.show();
			// password.focus();
		}
		if ($.trim(password.val()) == "" && $.trim(loginname.val()) == "") {
			errorMsg.text("用户名和密码不能为空!");
			errorMsg.show();
			return false;
		}
		if ($.trim(password.val()) != "") {
			errorMsg.hide();
			return true;
		}
		return false;
	}
	password.blur(function() {
		password_check();
	});

//	$("#hidePrompt").hide();
//	$("#hidePrompt").fadeIn(3000);
//	$("#hidePrompt").fadeOut(5000);
	$("#loginInfo").bind("click", function() {
		if ($.trim(loginname.val()) == "") {
			loginname.focus();
		} else if ($.trim(password.val()) == "") {
			password.focus();
		}
//		else if ($.trim(validCodeInput.val()) == "") {
//			validCodeInput.focus();
//		} 
		else if (loginname_check() && password_check()) {
			$.post("/login/loginAjax.do", {
				loginName : loginname.val(),
				pwd : $.md5(password.val())
//				identifying_code : validCodeInput.val()
			}, function(data) {
				if (data.success) {
                    $.cookie("loginName",loginname.val(),{ path: '/', expires: 7 });
					window.location.href = "/login/index.do";
				} else {
					errorMsg.text(data.msg);
					errorMsg.show();
					if (data.error_code == 102) {
						validCode.click();
						validCodeInput.focus();
						validCodeInput.val("");
					} else {
						password.focus();
						password.val("");
					}
				}
			});
		}
	});
});
