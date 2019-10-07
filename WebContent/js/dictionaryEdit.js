$(function(){
	if ($("input[name='dictionary.id']").val()!="") {
		$("#dictionarySelect").val($("input[name='dictionary.id']").val());
	}
	$("#saveInfo").click(function(){
		pop_up_box.postWait();
		$.post("/dictionary/saveDictionaryDetail.do",$("#dictionaryForm").serialize(),function(data){
			pop_up_box.loadWaitClose();
			if (data.success) {
				pop_up_box.showMsg("数据保存成功!",function(){
					$("#secondShow").html("");
//					$("#dictionaryGrid").trigger("reloadGrid");
					$("#dictionaryGrid").jqGrid("setGridParam",
							{url:"/dictionary/queryDictionaryDetail.do?dictionary="+dictionary}).trigger("reloadGrid");
					$("#contentShow").show();
				});
			}else{
				pop_up_box.showMsg(data.msg);
			}
		});
	});
});