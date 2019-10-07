$(function(){
	function getId(){
		//获取选中行(单行)的ID
		var orderGridInfo=$("#orderListGridInfo");
		var id = orderGridInfo.jqGrid('getGridParam','selrow');
		//根据id获取行数据,返回的是列表
		var rowDatas = orderGridInfo.jqGrid('getRowData', id);
		//取到选中行某一字段的值，其中name为colModel中定义的字段名
		var id= rowDatas["id"];
		if (id) {
			return id;
		}else{
			pop_up_box.showMsg("没有选择数据!");
			return false;
		}
	}
	var type=$("#type").val();
	$("#imgorder").click(function(){
		var id=getId();
		if (!id) {
			return;
		}
		$.get("/order/showImg.do",{"id":id,"type":type},function(data){
			contentShow.hide();
			secondShow.html(data);
		});
	});
	$("#delorder").click(function(){
		var id=getId();
		if (!id) {
			return;
		}
		//获取选中行(单行)的ID
		var id = orderGridInfo.jqGrid('getGridParam','selrow');
		//根据id获取行数据,返回的是列表
		var rowDatas = orderGridInfo.jqGrid('getRowData', id);
		function delRecord(){
			$.get("/order/deleteOrder.do",{"id":id},function(data){
				if (data.success) {
					$("#orderListGridInfo").trigger("reloadGrid");
					pop_up_box.showMsg("数据已经删除!");
				}
			});
		}
		 if (rowDatas["status"]==0) {
				if (confirm("你是否要永久删除该记录!")) {
					delRecord();
				}
		}else{
			if (confirm("你是否要删除该记录,记录删除后可以到回收站去查看和永久删除!")) {
					delRecord();
			}
		}
	});
	if ($("#addorder").length>0) {
		$("#addorder").click(function(){
			
		});
	}
	$("#showorder").click(function(){
		var id=getId();
		if (!id) {
			return;
		}
		$.get("/order/showOrderInfo.do",{"no":id},function(data){
			contentShow.hide();
			secondShow.html(data);
		});
	});
	
	$("#showOrderDetail").click(function(){
		var id=getId();
		if (!id) {
			return;
		}
		$.get("/order/getOrderDetail.do",{"id":id},function(data){
			showUserEdit();
		});
	});
});