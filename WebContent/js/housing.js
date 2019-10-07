function selectParams(){
	var district=$("select[name='district']").val();
	var rentType=$("select[name='rentType']").val();
	var houseType=$("select[name='houseType']").val();
	var decorationType=$("select[name='decorationType']").val();
	var status=$("select[name='status']").val();
	var params="";
	if (district!=0) {
		params+="&district="+district;
	}
	if (rentType&&rentType!=0) {
		params+="&rentType="+rentType;
	}
	if (houseType!=0) {
		params+="&houseType="+houseType;
	}
	if (decorationType!=0) {
		params+="&decorationType="+decorationType;
	}
	if (status>=0) {
		params+="&status="+status;
	}
	return params;
}
function priceParams(){
	var monthlyRent=$("input[name='monthlyRent']");
	var monthlyRent2=$("input[name='monthlyRent2']");
	var totalPrice=$("input[name='totalPrice']");
	var totalPrice2=$("input[name='totalPrice2']");
	var params="";
	if (monthlyRent.length>0) {
		if (monthlyRent.val()!="") {
			params+="&monthlyRent="+monthlyRent.val();
		}
	}
	if (monthlyRent2.length>0) {
		if (monthlyRent2.val()!="") {
			params+="&monthlyRent2="+monthlyRent2.val();
		}
	}
	if (totalPrice.length>0) {
		if (totalPrice.val()!="") {
			params+="&totalPrice="+totalPrice.val();
		}
	}
	if (totalPrice2.length>0) {
		if (totalPrice2.val()!="") {
			params+="&totalPrice2="+totalPrice2.val();
		}
	}
	return params;
}
function numParams(){
	var floor=$("input[name='floor']").val();
	var building=$("input[name='building']").val();
	var acreage=$("input[name='acreage']").val();
	var acreage2=$("input[name='acreage2']").val();
	var buildTime=$("input[name='buildTime']").val();
	var searchKey=$("input[name='searchKey']").val();
	var type=$("input[name='type']").val();
	var delFlag=$("input[name='delFlag']").val();
	var params="";
	if (floor&&floor!="") {
		params+="&floor="+floor;
	}
	if (building&&building!="") {
		params+="&building="+building;
	}
	if (acreage&&acreage!="") {
		params+="&acreage="+acreage;
	}
	if (acreage2&&acreage2!="") {
		params+="&acreage2="+acreage2;
	}
	if (buildTime&&buildTime!="") {
		params+="&buildTime="+buildTime;
	}
	if (searchKey&&searchKey!="") {
		params+="&searchKey="+searchKey;
	}
	if (type!="") {
		params+="&type="+type;
	}
	if (delFlag=="del") {
		params+="&delFlag="+0;
	}
	return params;
}
/**
 * 获取信息查询参数
 * @returns
 */
function getParams(){
var params=selectParams();
params+=priceParams();
params+=numParams();
return params;	
}
function closeSencondDiv(){
	var secondShow=$("#secondShow");
	var contentShow=$("#contentShow");
	secondShow.html("");
	contentShow.show();
}
$(function(){
	initNumInput(); 
	$("#queryData").click(function(){
		$("#tenementListGridInfo").jqGrid("setGridParam",
				{url:"/housingInfo/queryhousing.do?"+getParams()}).trigger("reloadGrid");
	});
	var secondShow=$("#secondShow");
	var contentShow=$("#contentShow");
	var type=$("input[name='type']").val();
	var tenementGridInfo=$("#tenementListGridInfo");
	if ($("#addTenement").length>0) {
		$("#addTenement").click(function(){
			$.get("/housingInfo/editTenement.do",{"type":type,"operation":"add"},function(data){
				contentShow.hide();
				secondShow.html(data);
			});
		});
	}
		function getId(){
			//获取选中行(单行)的ID
			var id = tenementGridInfo.jqGrid('getGridParam','selrow');
			//根据id获取行数据,返回的是列表
			var rowDatas = tenementGridInfo.jqGrid('getRowData', id);
			//取到选中行某一字段的值，其中name为colModel中定义的字段名
			var id= rowDatas["id"];
			if (id) {
				return id;
			}else{
				pop_up_box.showMsg("没有选择数据!");
				return false;
			}
		}
		if ($("#editTenement").length>0) {
			$("#editTenement").click(function(){
				var id=getId();
				if (!id) {
 					return;
				}
				$.get("/housingInfo/editTenement.do",{"type":type,"operation":"edit","id":id},function(data){
					contentShow.hide();
					secondShow.html(data);
				});
			});
		}
		if ($("#delTenement").length>0) {
			$("#delTenement").click(function(){
				var id=getId();
				if (!id) {
					return;
				}
				if (confirm("你是否要删除该记录,记录删除后可以到回收站去查看和永久删除!")) {
					$.get("/housingInfo/deleteHousingInfo.do",{"id":id},function(data){
						if (data.success) {
							$("#queryData").click();
							pop_up_box.showMsg("数据已经删除!");
						}
					});
				}
			});
		}
		if ($("#imgTenement").length>0) {
			$("#imgTenement").click(function(){
				var id=getId();
				if (!id) {
					return;
				}
				$.get("/housingInfo/showImg.do",{"id":id,"type":type},function(data){
					contentShow.hide();
					secondShow.html(data);
				});
			}); 
		}
		if ($("#delHousing").length>0) {
			$("#delHousing").click(function(){
				var id=getId();
				if (!id) {
					return;
				}
				if (confirm("你是否要永久删除该记录!")) {
					$.get("/housingInfo/deleteHousingInfoPermanent.do",{"id":id},function(data){
						if (data.success) {
//							$("#queryData").click();
							tenementGridInfo.jqGrid("setGridParam",
									{url:"/housingInfo/queryHousingRecycle.do?math="+Math.random()+getParams()}).trigger("reloadGrid");
							pop_up_box.showMsg("数据已经删除!");
						}
					});
				}
			});
		}
		
		$("#hireHouseing").click(function(){
			var id=getId();
			if (!id) {
				return;
			}
			$.get("/order/tenementOrder.do",{"id":getId()},function(data){
				if (data!="false") {
					secondShow.html(data);
					contentShow.hide();
				}else{
					pop_up_box.showMsg("该房屋已经出租!");
				}
			});
		});
		$("#saleHouseing").click(function(){
			var id=getId();
			if (!id) {
				return;
			}
			$.get("/order/tenementOrder.do",{"id":getId()},function(data){
				if (data!="false") {
					secondShow.html(data);
					contentShow.hide();
				}else{
					pop_up_box.showMsg("该房屋已经出售!");
				}
			});
		});
		
		$("#clientInfo").click(function(){
			var id=getId();
			if (!id) {
				return;
			}
			$.get("/user/getUserInfoByHousing.do",{"infoId":id},function(data){
				 $("#clientNo").html(data.number);
				 $("#clientName").html(data.username);
				 $("#clientPhone").html(data.phone);
				 $("#clientRemark").html(data.remark);
				 $("#businessName").html(data.business.loginName);
				 $("#infoShowDiv").show();
			});
		});
		
		$("#closeInfoShow").click(function(){
			$("#clientNo").html("");
			 $("#clientName").html("");
			 $("#clientPhone").html("");
			 $("#clientRemark").html("");
			 $("#businessName").html("");
			$("#infoShowDiv").hide();
		});
		
		$("#publish").click(function(){
			var id=getId();
			if (!id) {
				return;
			}
			//获取选中行(单行)的ID
			var id = tenementGridInfo.jqGrid('getGridParam','selrow');
			//根据id获取行数据,返回的是列表
			var rowDatas = tenementGridInfo.jqGrid('getRowData', id);
			//取到选中行某一字段的值，其中name为colModel中定义的字段名
			var district= rowDatas["district"];
			var community= rowDatas["community"];
			var houseType= rowDatas["houseType"];
			var floor= rowDatas["floor"];
			$("#publishInfo").html(district+"-"+community+"-"+houseType+"-"+floor);
			$("#publishDiv").show();
		});
		$("#publishBtn").click(function(){
			$.ajax({
				type:'POST',
				url:"http://dengqiang.nat123.net/publishInfo.do",
				data:{
					"publishInfo":$("#publishInfo").html(),
					"businessName":$("input[name='businessName']").val(),
					"businessPhone":$("input[name='businessPhone']").val(),
					"type":$("input[name='type']").val(),
					"businessRemark":$("textarea[name='businessRemark']").val(),
					"userInfo":$("#userInfo").html()
				},
				dataType:'jsonp',
				callback:'json_callback',
				success:function(data){
					if (data=="success") {
						pop_up_box.showMsg("数据发布成功",function(){
							$("#closePublish").click();
						}); 
					}else{
						pop_up_box.showMsg("数据发布失败!错误:"+data.msg);
					}
				},
				error:function(XMLHttpRequest, textStatus){
					if (XMLHttpRequest.statusText=="success") {
						pop_up_box.showMsg("数据发布成功",function(){
							$("#closePublish").click();
						}); 
					}else{
						pop_up_box.showMsg("数据发布失败!请检查填写的数据后重试.");
					}
				}
			}); 
		});
		$("#closePublish").click(function(){
			$("#publishInfo").html("");
			$("input[name='businessName']").val("");
			$("input[name='clientPhone']").val("");
			$("textarea[name='clientRemark']").val("");
			$("#publishDiv").hide();
		});
		
});
