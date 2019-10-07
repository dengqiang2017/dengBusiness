<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	<script type="text/javascript" src="/js_lib/jquery.min.js"></script>
	<script type="text/javascript">
	function postData(){
		$.post("/user/insertUser.do",{
			userName:$("input[name='userName']").val(),
			account:$("input[name='account']").val(),
			userPwd:$("input[name='userPwd']").val()
		},function(data){
			alert(data);
		});
	}
	$(function(){
		$("#delBtn").click(function(){
			$.post("/user/delUser.do",{
				uId:$("input[name='uId']").val()
			},function(data){
				alert(data);
			});
		});
	$("#editBtn").click(function(){
		$.post("/user/editUser.do",{
			userName:$("input[name='userName']").val(),
			id:$("input[name='id']").val(),
			account:$("input[name='account']").val(),
			userPwd:$("input[name='userPwd']").val()
		},function(data){
			alert(data);
		});
	});
	$("#findBtn").click(function(){
		$.get("/user/getChannel.do",{name:$("#name").val()},function(data){
			console.debug($("#findDiv"));
			$("#findDiv").html("");
			$.each(data,function(i,n){
			$("#findDiv").append(i+":{"+n.cid+","+n.account
				+","+n.password+","+n.trueName+"}"); 
			});
		});
	});
	$("#findPageBtn").click(function(){
		$.get("/user/queryUser.do",{
			searchKey:$("#name").val()
			},function(data){
			console.debug(data);
			$("#findDiv").html("");
			$.each(data.rows,function(i,n){
			$("#findDiv").append(i+":{"+n.cid+","+n.account
				+","+n.password+","+n.trueName+"}"); 
			});
		});
	});
	});
	</script>
</head>
<body>
测试项目结果：<br/>
登录名：${uName}
密码：${userPwd}

<hr/>
<form action="/user/insertUser.do">
ID：<input type="text" name="id"/>
用户名：<input type="text" name="userName"/>
密码：<input type="text" name="userPwd"/>
账户：<input type="text" name="account"/>
<input type="button" onclick="postData();" value="添加"/>
<input type="button" id="editBtn" value="编辑"/>
</form>
<hr/>

<form action="/user/delUser.do">
删除ID：<input type="text" name="uId"/>
<input type="button" id="delBtn" value="提交"/>
</form>
<input type="text" id="name">
<input type="button" id="findBtn" value="查询数据">
<input type="button" id="findPageBtn" value="分页查询数据">
<br/>
<div id="findDiv" style="width: 500px;height: 100px;border: red 1px solid;"></div>
</body>
</html>