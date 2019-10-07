$(function(){
	var secondHandGridInfo=$("#tenementListGridInfo");
	secondHandGridInfo.jqGrid({
		url : "/housingInfo/queryHousingRecycle.do?type=0&math="+Math.random(),
		datatype : 'json',
		mtype : 'get',
		loadComplete : function(data,response){
			var totalPage =secondHandGridInfo.getGridParam("lastpage");
			$("#tenementListGridInfoPage").show();
			if(totalPage<2){
				$("#tenementListGridInfoPage").hide();
			}
		},
		loadError: function(req,status,error){
		},//"面积","单价(元)","总价(万元)","装修","朝向","房源类型","房源编号",
		colNames : ["序号","区县","板块","小区","户型","楼层","发布时间","状态","标注"],
		colModel : [
		            {name : "id",index : "id",editable : true,hidden : true,width:0},
		            {name : "district",index : "district",editable : true,width:100,formatter:function(data){return data.name;}},
		            {name : "plate",index : "plate",editable : true,width:100,formatter:function(data){return data.name;}} ,
		            {name : "community",index : "community",editable : true,width:100,formatter:function(data){return data.name;}} ,
		            {name : "houseType",index : "houseType",editable : true,width:100,formatter:function(data){return data.name;}} ,
		            {name : "floor",index : "floor",editable : true,width:100} ,
//		            {name : "acreage",index : "acreage",editable : true,width:100} ,
//		            {name : "unitPrice",index : "unitPrice",editable : true,width:100} ,
//		            {name : "totalPrice",index : "totalPrice",editable : true,width:100} ,
//		            {name : "decorationType",index : "decorationType",editable : true,width:60,formatter:function(data){return data.name;}} ,
//		            {name : "orientation",index : "orientation",editable : true,width:60}  ,
//		            {name : "refreshTime",index : "refreshTime",editable : true,width:100} ,
		            {name : "publishTime",index : "publishTime",editable : true,width:150,formatter:function(data){
		            	return  new Date(parseInt(data)).Format("yyyy-MM-dd hh:mm:ss");
	            	}} ,
//		            {name : "housingType",index : "housingType",editable : true,width:100} ,
		            {name : "status",index : "status",editable : true,width:100,formatter:function(val){
		            	if (val==0) {///0-未卖出,2-已预订,3-交易中,4-已卖出
							return "未卖出";
						}else if (val==2) {
							return "已预订";
						}else if (val==3) {
							return "交易中";
						}else{
							return "已卖出";
						}
		            }} ,
//		            {name : "number",index : "number",editable : true,width:100},
		            {name : "remark",index : "remark",editable : true,width:150}
		            ],
		            pager : '#tenementListGridInfoPage',
		            cmTemplate: { sortable: true},
		            rowNum : 10,
		            rowList : [ 10, 20, 30 ],
		            rownumbers : true,
		            rownumWidth :50,
		            label : "序号",
		            sortname : 'publishTime',
		            sortorder : 'asc',
		            viewrecords : true,altRows:false,
//		            gridview : true,
//		            autowidth :true,
		            height:"100%",
		            jsonReader : {
		            	root : "rows",
		            	records:"total",
		            	total:"totalPage",
		            	repeatitems : false,
		            	id : "0"
		            },
		            gridComplete:function(){ 
		            	$("#jqgh_tenementListGridInfo_rn").html("序号");
		            }
	});
});
