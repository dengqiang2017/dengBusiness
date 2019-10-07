$(function(){
	var userGridInfo=$("#userListGridInfo");
	userGridInfo.jqGrid({
		url : "/user/queryUser.do?math="+Math.random(),
		datatype : 'json',
		mtype : 'get',
		loadComplete : function(data,response){
			var totalPage =userGridInfo.getGridParam("lastpage");
			$("#userListGridInfoPage").show();
			if(totalPage<2){
				$("#userListGridInfoPage").hide();
			}
		},
		loadError: function(req,status,error){
		},
		colNames : [null,"客户编号","客户名称","客户电话","客户类型"],
		colModel : [
		            {name : "id",index : "id",editable : true,hidden : true,width:0},
		            {name : "number",index : "number",editable : true,width:130},
		            {name : "username",index : "username",editable : true,width:100} ,
		            {name : "phone",index : "phone",editable : true,width:100} ,
		            {name : "clientType",index : "clientType",editable : true,width:100,formatter:function(data){
		            	if (data==0) {
							return "房主/房东";
						}else if (data==1) {
							return "租客";
						}else{
							return "购房者";
						}
		            	return data.name;
		            	}}
		            ],
		            pager : '#userListGridInfoPage',
		            cmTemplate: { sortable: true},
		            rowNum : 10,
		            rowList : [ 10, 20, 30 ],
		            rownumbers : true,
		            rownumWidth :50,
		            label : "序号",
		            viewrecords : true,
		            altRows:false,
		            height:"100%", 
		            jsonReader : {
		            	root : "rows",
		            	records:"total",
		            	total:"totalPage",
		            	repeatitems : false,
		            	id : "0"
		            },
		            ondblClickRow:function(rowid,iRow,iCol,e){
		            	$("#selectUserinfo").click();
		            },
		            gridComplete:function(){
		            	$("#jqgh_userListGridInfo_rn").html("序号");
		            }
	});
	$("#usernameFind").click(function(){
		var params="";
		if ($.trim($("#searchKey").val())!="") {
			params="searchKey="+$.trim($("#searchKey").val());
		}
		if (params!="") {
			params+="&";
		}
		if ($("#clientType").val()>=0) {
			params+="clientType="+$("#clientType").val();
		}
		userGridInfo.jqGrid("setGridParam",
				{url:"/user/queryUser.do?"+params}).trigger("reloadGrid");
	});
	$("#adduser").click(function(){
		$("#closeedit").click();
		$("#divPopUpBoxEdit").find("input[name='id']").val("");
		showUserEdit();
	});
	function getId(){
		//获取选中行(单行)的ID
		var id = userGridInfo.jqGrid('getGridParam','selrow');
		//根据id获取行数据,返回的是列表
		var rowDatas = userGridInfo.jqGrid('getRowData', id);
		//取到选中行某一字段的值，其中name为colModel中定义的字段名
		return rowDatas["id"];
	}
	$("#edituser").click(function(){
		var id=getId();
		if (!id) {
			pop_up_box.showMsg("没有选择数据!");
			return;
		}
		//获取选中行(单行)的ID
		var id = userGridInfo.jqGrid('getGridParam','selrow');
		//根据id获取行数据,返回的是列表
		var rowDatas = userGridInfo.jqGrid('getRowData', id);
		$("#divPopUpBoxEdit").find("input[name='id']").val(rowDatas["id"]);
		$("#divPopUpBoxEdit").find("input[name='username']").val(rowDatas["username"]);
		$("#divPopUpBoxEdit").find("input[name='phone']").val(rowDatas["phone"]);
		$("#divPopUpBoxEdit").find("textarea[name='remark']").val(rowDatas["remark"]);
		var type=0;
		if ("租客"==rowDatas["clientType"]) {
			type=1;
		}else if ("购房者"==rowDatas["clientType"]) {
			type=2;
		}else{
			type=0;
		}
		$("#divPopUpBoxEdit").find("select[name='clientType']").val(type);
		showUserEdit();
	});
	$("#deluser").click(function(){
		var id=getId();
		if (!id) {
			pop_up_box.showMsg("没有选择数据!");
			return;
		}
		if (confirm("你是否要永久删除该记录!")) {
			$.get("/user/delUser.do",{"id":id},function(data){
				if (data.success) {
					$("#usernameFind").click();
					pop_up_box.showMsg("数据已经删除!");
				}
			});
		}
	});
////////选择用户信息---begin///////
	$("#selectUserinfo").click(function(){
		var userGridInfo=$("#userListGridInfo");
		function getId(){
			//获取选中行(单行)的ID
			var id = userGridInfo.jqGrid('getGridParam','selrow');
			//根据id获取行数据,返回的是列表
			var rowDatas = userGridInfo.jqGrid('getRowData', id);
			//取到选中行某一字段的值，其中name为colModel中定义的字段名
			return rowDatas["id"];
		}
		var id=getId();
		if (!id) {
			pop_up_box.showMsg("没有选择数据!");
			return;
		}
		//获取选中行(单行)的ID
		var id = userGridInfo.jqGrid('getGridParam','selrow');
		var rowDatas = userGridInfo.jqGrid('getRowData', id);
		$("#homeownerId").val(rowDatas["id"]);
		$("#userinfo").html(rowDatas["username"]+"-"+rowDatas["phone"]);
		$("#selectUserDiv,#selectzhezhao").hide();
	});
	$("#closeUserSelect").click(function(){
		$("#selectUserDiv,#selectzhezhao").hide();
	});
	////////选择用户信息---end///////
});