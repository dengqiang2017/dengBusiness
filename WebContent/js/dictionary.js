var dictionary="";///tree选择的id值
$(function(){
	var setting = {
			callback: {
				onClick:onClick
	                 }
	};
	var zNodes =[
	 			{ name:" 所有类别", open:true, pId:0,iconOpen:"/images/1_open.png",iconClose:"/images/1_open.png",
	 				children: [
	 					{ name:"区县",id:1,pId:1,icon:"/images/2.png"},
	 					{ name:"板块",id:2,pId:1,icon:"/images/2.png"},
	 					{ name:"小区",id:3,pId:1,icon:"/images/2.png"},
	 					{ name:"户型",id:4,pId:1,icon:"/images/2.png"},
	 					{ name:"房屋类型",id:5,pId:1,icon:"/images/2.png"},
	 					{ name:"装修",id:6,pId:1,icon:"/images/2.png"},
	 					{ name:"类型",id:7,pId:1,icon:"/images/2.png"}
	 				]}];
	function onClick(e, treeId, treeNode){
		var zTree = $.fn.zTree.getZTreeObj("treeDictionary");
		var nodes = zTree.getCheckedNodes(true);
		 dictionary=treeNode.id;
		 if (!dictionary) {
			dictionary="";
		}else{
			dictionary=treeNode.id;
		}
		 $("#dictionaryGrid").jqGrid("setGridParam",
					{url:"/dictionary/queryDictionaryDetail.do?dictionary="+dictionary}).trigger("reloadGrid");
	}
	$.fn.zTree.init($("#treeDictionary"), setting, zNodes);
	var dictionaryGrid=$("#dictionaryGrid");
	dictionaryGrid.jqGrid({
		url : "/dictionary/queryDictionaryDetail.do?math="+Math.random(),
		datatype : 'json',
		mtype : 'get',
		loadComplete : function(data,response){
			var totalPage =dictionaryGrid.getGridParam("lastpage");
			$("#dictionaryGridPage").show();
			if(totalPage<2){
				$("#dictionaryGridPage").hide();
			}
		},
		loadError: function(req,status,error){
		},
		colNames : ["序号","名称","排序","备注"],
		colModel : [
		            {name : "id",index : "id",editable : true,hidden : true,width:0},
		            {name : "name",index : "name",editable : true,width:100},
		            {name : "sorting",index : "sorting",editable : true,width:100},
		            {name : "remark",index : "remark",editable : true,width:150}
		            ],
		            pager : '#dictionaryGridPage',
		            cmTemplate: { sortable: true},
		            rowNum : 10,
		            rowList : [ 10, 20, 30 ],
		            rownumbers : true,
		            rownumWidth :50,
		            label : "序号",
		            sortname : 'sorting',
		            sortorder : 'asc',
		            viewrecords : true,altRows:false,
//		            gridview : true,
//		            autowidth :true,
		            height:"100%",
		            sortname:"sorting",
		            sortorder: "desc",
		            jsonReader : {
		            	root : "rows",
		            	records:"total",
		            	total:"totalPage",
		            	repeatitems : false,
		            	id : "0"
		            },
		            ondblClickRow:function(rowid,iRow,iCol,e){
		            	$("#dictionaryEdit").click();
		            },
		            gridComplete:function(){
		            	$("#jqgh_dictionaryGrid_rn").html("序号");
		            }
	});
	function getId(){
		//获取选中行(单行)的ID
		var id = dictionaryGrid.jqGrid('getGridParam','selrow');
		//根据id获取行数据,返回的是列表
		var rowDatas = dictionaryGrid.jqGrid('getRowData', id);
		//取到选中行某一字段的值，其中name为colModel中定义的字段名
		return rowDatas["id"];
	}
	function loadDetail(){
		$("#queryData").click(function(){
			$("#dictionaryGrid").jqGrid("setGridParam",
					{url:"/dictionary/queryDictionaryDetail.do?dictionary="+dictionary}).trigger("reloadGrid");
		});
	}
	var secondShow=$("#secondShow");
	var contentShow=$("#contentShow");
	$("#dictionaryAdd").click(function(){
		$.get("/dictionary/editDictionary.do",function(data){
			contentShow.hide();
			secondShow.html(data);
		});
	});
	$("#dictionaryEdit").click(function(){
		var id=getId();
		if (!id) {
			pop_up_box.showMsg("没有选择数据!");
			return;
		}
		$.get("/dictionary/editDictionary.do",{"id":id},function(data){
			contentShow.hide();
			secondShow.html(data);
		});
	});
	$("#dictionaryDel").click(function(){
		var id=getId();
		if (!id) {
			pop_up_box.showMsg("没有选择数据!");
			return;
		}
		if (confirm("你是否要删除该记录!")) {
			$.get("/dictionary/deleteDictionaryDetail.do",{"id":id},function(data){
				if (data.success) {
					pop_up_box.showMsg("数据已经删除!");
					$("#dictionaryGrid").trigger("reloadGrid");
				}
			});
		}
	});
});