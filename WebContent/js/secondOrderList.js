$(function(){
	var orderGridInfo=$("#orderListGridInfo");
	orderGridInfo.jqGrid({
		url : "/order/queryOrder.do?type=1&status=0&math="+Math.random(),
		datatype : 'json',
		mtype : 'get',
		loadComplete : function(data,response){
			var totalPage =orderGridInfo.getGridParam("lastpage");
			$("#orderListGridInfoPage").show();
			if(totalPage<2){
				$("#orderListGridInfoPage").hide();
			}
		},
		loadError: function(req,status,error){
		},
		colNames : [null,"订单编号","小区","楼层","总价","面积","创建时间","状态","标注"],
		colModel : [
		            {name : "id",index : "id",editable : true,hidden : true,width:0},
		            {name : "orderNumber",index : "orderNumber",editable : true,width:140},
//		            {name : "housingInfo.district",index : "housingInfo.district",editable : true,width:100,formatter:function(data){return data.name;}},
//		            {name : "plate",index : "plate",editable : true,width:100,formatter:function(data){return data.name;}} ,
		            {name : "housingInfo.community",index : "housingInfo.community",editable : true,width:100,formatter:function(data){return data.name;}} ,
//		            {name : "houseType",index : "houseType",editable : true,width:100,formatter:function(data){return data.name;}} ,
		            {name : "housingInfo.floor",index : "housingInfo.floor",editable : true,width:100} ,
//		            {name : "acreage",index : "acreage",editable : true,width:100} ,
		            {name : "totalPrice",index : "totalPrice",editable : true,width:80} ,
		            {name : "acreage",index : "acreage",editable : true,width:80} ,
//		            {name : "housingInfo.rentType",index : "housingInfo.rentType",editable : true,width:80,formatter:function(data){return data.name;}} ,
//		            {name : "housingInfo.homeowner",index : "housingInfo.homeowner",editable : true,width:100,formatter:function(data){return data.username;}} ,
//		            {name : "client",index : "client",editable : true,width:100,formatter:function(data){return data.username;}} ,
//		            {name : "orientation",index : "orientation",editable : true,width:100} ,
		            
//		            {name : "refreshTime",index : "refreshTime",editable : true,width:340,formatter:function(data){
//		            	return  new Date(parseInt(data)).Format("yyyy-MM-dd hh:mm:ss");
//		            	}} ,
		            {name : "publishTime",index : "publishTime",editable : true,width:150,formatter:function(data){
		            	return  new Date(parseInt(data)).Format("yyyy-MM-dd hh:mm:ss");
	            	}} ,
//		            {name : "housingInfo.housingType",index : "housingInfo.housingType",editable : true,width:80,formatter:function(data){return data.name;}} ,
		            {name : "status",index : "status",editable : true,width:100,formatter:function(val){
		            	if(val==0){
		            		return "已取消";
		            	}else if (val==2) {
							return "已预订";
						}else if (val==3) {
							return "交易中";
						}else{
							return "已完成";
						}
		            }} ,
//		             ,{name : "number",index : "number",editable : true,width:200}
		           {name : "remark",index : "remark",editable : true,width:150} 
		            ],
		            pager : '#orderListGridInfoPage',
		            cmTemplate: { sortable: true},
		            rowNum : 10,
		            rowList : [ 10, 20, 30 ],
		            rownumbers : true,
		            rownumWidth :50,
		            label : "序号",
		            viewrecords : true,
		            altRows:false,
//		            gridview : true,
//		            autowidth :true,
		            height:"100%",
		            sortname:'publishTime',
		            sortorder: "asc",
		            jsonReader : {
		            	root : "rows",
		            	records:"total",
		            	total:"totalPage",
		            	repeatitems : false,
		            	id : "0"
		            },
		            gridComplete:function(){
		            	$("#jqgh_orderListGridInfo_rn").html("序号");
		            }
	});
});
