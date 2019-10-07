$(function(){
	$("input").removeAttr("disabled");
});
var key;
var ids=["1"];
var idi=1;
var lastValue = "";
var nodeList =[];
var fontCss = {};
var pop_up_box={
		/**
		 * 信息提示框
		 * @param msg 提示信息
		 * @param callback 点击确定后的操作-可选
		 */
		showMsg:function(msg,callback){
			function getMessageDiv(msg){
				var msgDiv="<div id='msgDiv'  style='HEIGHT:157px;WIDTH:345px; border:solid 2px #CCCCCC; position:fixed; display:none; left:38%;top:25%; background:#fff; z-index:99999999;'>"
					+"<div class='tanchukuangNxx'>"
					+"<span>系统提示：</span><a class='anniuGbxx' id='closeMsgX'></a>"
					+"<hr class='tckHrxx' />"
					+"<div class='tanchukuangNDxx' id='msg'>"+msg+"</div>"
					+"<div class='tanchukuangNDNxx'><a id='closeMsg'>确定</a></div>"
					+"</div>"
					+"</div><div id='zhezhao' style='position:fixed;z-index:9991;width:100%;height:100%;background-color:silver;display:none;opacity:.5;  filter:Alpha(Opacity=50); top:0px;'></div>";
				return msgDiv;
			}
			var msgDiv=getMessageDiv(msg);
			$("body").append(msgDiv);
			$("#msgDiv,#zhezhao").show();
			$("#closeMsg,#closeMsgX").bind("click",function(){
				$("input").removeAttr("disabled");
				$("#msgDiv,#zhezhao").remove();
				if (callback) {
					callback();
				}
			});
			$("input").attr("disabled","disabled"); 
		},
		/**
		 * 信息提示框
		 * @param msg 提示信息
		 * @param callback 点击确定后的操作-可选
		 */
		showmsg:function(msg,callback){
			pop_up_box.showMsg(msg, callback);
		},
		/**
		 * 数据提交等待
		 */
		postWait:function(){
			function getMessageDiv(){
				var msgDiv="<div id='msgDiv'  style='HEIGHT:110px;WIDTH:300px; border:solid 2px #CCCCCC; position:fixed;left:38%;top:25%; background:#fff; z-index:99999999;'>"
					+"<div class='tanchukuangNxx'>"
					+"<div style='margin-top: 10px;'><img src='/images/load_wait.gif'  style='margin-left: 100px;'/>"
					+"<label class='tanchukuangload'>数据提交中,请稍候...</label></div>"
					+"</div>"
					+"</div><div id='zhezhao' style='position:fixed;z-index:9991;width:100%;height:100%;background-color:silver;opacity:.5;  filter:Alpha(Opacity=50); top:0px;'></div>";
				return msgDiv;
			}
			$("input").attr("readonly","readonly");
			$("body").append(getMessageDiv());
		},
		/**
		 * 数据获取等待
		 */
		loadWait:function(){
			function getMessageDiv(){
				var msgDiv="<div id='msgDiv'  style='HEIGHT:110px;WIDTH:300px; border:solid 2px #CCCCCC; position:fixed;left:38%;top:25%; background:#fff; z-index:99999999;'>"
					+"<div class='tanchukuangNxx'>"
					+"<div style='margin-top: 10px;'><img src='/images/load_wait.gif'  style='margin-left: 100px;'/>"
					+"<label class='tanchukuangload'>数据获取中,请稍候...</label></div>"
					+"</div>"
					+"</div><div id='zhezhao' style='position:fixed;z-index:9991;width:100%;height:100%;background-color:silver;opacity:.5;  filter:Alpha(Opacity=50); top:0px;'></div>";
				return msgDiv;
			}
			$("input").attr("readonly","readonly");
			$("body").append(getMessageDiv());
		},
		/**
		 * 数据处理提示
		 */
		dataHandlingWait:function(){
			function getMessageDiv(){
				var msgDiv="<div id='msgDiv'  style='HEIGHT:110px;WIDTH:300px; border:solid 2px #CCCCCC; position:fixed;left:38%;top:25%; background:#fff; z-index:99999999;'>"
					+"<div class='tanchukuangNxx'>"
					+"<div style='margin-top: 10px;'><img src='/images/load_wait.gif'  style='margin-left: 100px;'/>"
					+"<label class='tanchukuangload'>数据处理中,请稍候...</label></div>"
					+"</div>"
					+"</div><div id='zhezhao' style='position:fixed;z-index:9991;width:100%;height:100%;background-color:silver;opacity:.5;  filter:Alpha(Opacity=50); top:0px;'></div>";
				return msgDiv;
			}
			$("input").attr("readonly","readonly");
			$("body").append(getMessageDiv());
		},
		/**
		 * 关闭数据处理提示
		 */
		loadWaitClose:function(){
			$("input").removeAttr("readonly");
			 $("#msgDiv,#zhezhao").remove();
		},
		/**
		 * 上传文件进度条
		 */
		showLoadImg:function(){
			function getMessageDiv(){
				var msgDiv="<div id='progress'  style='HEIGHT:110px;WIDTH:335px; border:solid 2px #CCCCCC; position:fixed;  left:38%;top:25%; background:#fff; z-index:99999999;'>"
					+"<div style='position:relative;float: left;' id='msg'>"
					+"<span style=' display: inline-block; float:left; '>上传文件：</span>"
					+"<img style='position:absolute; top:50px; left:10px; z-index:1;' width='300' height='15' src='/images/progress1.png' />"
					+"<div style='display:inline-block; position:absolute; top:50px; left:10px; z-index:2;width: 300px;'>"
					+"<img id='progressImg' style='position:absolute; z-index:2; width: 2%;' height='15' src='/images/progress2.png' /></div>"
					+"<div id='info' style='position:absolute; top:70px; left:10px; z-index:1; width: 335px;'>等待上传中...</div>"
					+"</div>"
					+"</div><div id='zhezhao' style='position:fixed;z-index:9991;width:100%;height:100%;background-color:silver;display:none;opacity:0.5;  filter:Alpha(Opacity=50); top:0px;'></div>";
				return msgDiv;
			}
			$("body").append(getMessageDiv());
			showProgress();
		},
		/**
		 * 显示图片
		 * @param imgurl 图片路径
		 */
		showImg:function(imgurl){
			function getMessageDiv(msg){
				return "<div id='imgDiv'  style='min-HEIGHT:157px;min-WIDTH:245px; *+height:620px;*+width:588px;  border:solid 2px #ec6941; position:fixed; display:none; left:38%;top:20%; background:#fff; z-index:99999999;'>"
					+"<div class='tanchukuangNxximg'>"
					+"<div class='tanchukuangNDxximg' id='msg'><img id='imgshow' src='"+imgurl+"'/></div>"
					+"<div class='tanchukuangNDNxximg' style='width:100%;'><a id='closeMsg'>确定</a></div>"
					+"</div></div>";
			}
			var msgDiv=getMessageDiv(imgurl);
			$("body").append(msgDiv);
			$("#imgDiv").show();
			pop_up_box.moveDialog("imgDiv","msg");
			$("#closeMsg,#imgshow").bind("click",function(){
				$("#imgDiv").remove();
			});
		},
		/**
		 * 行业树
		 * @param choice 可选,默认为多选,true为单选
		 */
		showTradeDiv:function(choice){
			function getTradeDiv(){
				var tradeDiv="<div id='tradeDiv'  style='max-HEIGHT:487px;min-height:240px; height:auto !important;  height:240px; WIDTH:345px; border:solid 2px #ec6941; position:fixed; display:none; left:723px;top:148px; background:#fff; z-index:99999999;'>"
					+"<div class='tanchukuangN' style=' width:100%; min-height:100px; height:auto !important;'>"
					+"<ul>"
					+"<li id='title' style='cursor: move;float:left; width:100%; background:url(/images/tckt.jpg) repeat-x ;'><span class='tanchukuangNts'>行业树</span><a class='anniuGb' id='closeMsg' style='float:right; margin-top:-5px;'></a></li>"
					+"<li><a class='tckButS' id='findBtn'></a><input class='tckStext' type='text' id='tradeName' placeholder='关键字'/></li>"
					+"<li>"
					+"  <div style='clear:both;'></div>"
					+"  <div class='tanchukuangNDx'>"
					+"  <ul id='tree' class='ztree' ></ul>"
					+"  </div>"
					+"</li>"
					+"</ul>"
					+"<div style='clear:both;'></div>"
					+"<div class='tanchukuangNDN' style='margin-bottom:10px;'><a id='selectBtn'>确定</a></div>"
					+"<div style='clear:both;'></div>"
					+"</div>"
					+"</div><div id='zhezhao' style='position:fixed;z-index:9991;width:100%;height:100%;background-color:silver;display:none;opacity:0.5;  filter:Alpha(Opacity=50); top:0px;'></div>";
				return tradeDiv;
			}
			function onloadZTreeT(choice){
				var ztreeNodes; 
			       $.post("/industryAction/list.do",function(data){
			           ztreeNodes =eval(data);
			           if (choice) {
			        	   $.fn.zTree.init($("#tree"), settingRadio, ztreeNodes);
						}else{
							$.fn.zTree.init($("#tree"), setting, ztreeNodes);
						}
			           
			        	   onAsyncSuccess();
			       });
			       //设置默认选中
			  	 function onAsyncSuccess(){
						var zTree = $.fn.zTree.getZTreeObj("tree");
					    if (ids) {
					 	   $.each(ids,function(i,n){
					 		   var node = zTree.getNodeByParam("id",n);
					 		   if (node) {
					 			   zTree.selectNode(node,true);
					 			   node.checked = true;
					 			   zTree.updateNode(node);
					 			   zTree.expandNode(node, true, false, false);//展开节点
					 		   }
					 	   });
						}
					}
			}
			function seacr(){
				function searchNode(e){
					var zTree = $.fn.zTree.getZTreeObj("tree");
					var value = $.trim(key.get(0).value);
					var keyType = "name";
					if (key.hasClass("empty")) {
						value = "";
					}
					if (lastValue === value) return;
					lastValue = value;
					if (value === "") return;
					updateNodes(false);
					///关键//获取匹配项
					nodeList = zTree.getNodesByParamFuzzy(keyType, value);
					updateNodes(true);
				}
				function updateNodes(highlight){
					var zTree = $.fn.zTree.getZTreeObj("tree");
					for( var i=0, l=nodeList.length; i<l; i++) {
						nodeList[i].highlight = highlight;
						zTree.selectNode(nodeList[i],true);
						zTree.updateNode(nodeList[i]);
						zTree.expandNode(nodeList[i], true, false, false);//展开节点
					}
					$("#tradeName").focus();
				}
				function focusKey(){
					if (key.hasClass("empty")) {
						key.removeClass("empty");
					}
				}
				function blurKey(){
					if (key.get(0).value === "") {
						key.addClass("empty");
					}
				}
				key = $("#tradeName");
				key.bind("focus",pop_up_box.focusKey)
				.bind("blur", pop_up_box.blurKey)
				.bind("propertychange", searchNode)
				.bind("input", searchNode); 
				$("#findBtn").bind("click",function(){
					searchNode();
				});
			}
			 if ($("#txtId").length>0) {
					var txt=$("#txtId");
					ids[0]=txt.val().split(",")[0];
			}
			var msgDiv=getTradeDiv();
			$("input:text").attr("disabled","disabled");
			$("body").append(msgDiv);
			$("#closeMsg").bind("click",function(){
				$("input").removeAttr("disabled");
				$("#tradeDiv,#zhezhao").remove();
			});
			$("#selectBtn").bind("click",function(){
				$("input").removeAttr("disabled");
				$("#tradeDiv,#zhezhao").remove();
			});
			pop_up_box.moveDialog("tradeDiv","title");
			onloadZTreeT(choice);
			seacr();
			 $("#tradeDiv,#zhezhao").show();	
		},
		moveDialog:function(tradeDiv,title){
			var posX;
			var posY;
			var fdiv = document.getElementById(tradeDiv);
			$("#"+title).mousedown(function(e){
				if(!e) e = window.event; //如果是IE
				posX = e.clientX - parseInt(fdiv.style.left);
				posY = e.clientY - parseInt(fdiv.style.top);
				document.onmousemove = mousemove;
			});
			document.onmouseup = function(){
				document.onmousemove = null;
			};
			function mousemove(ev){
				if(ev==null) ev = window.event;//如果是IE
				fdiv.style.left = (ev.clientX - posX) + "px";
				fdiv.style.top = (ev.clientY - posY) + "px";
			}
		}
};
//行业树多选参数设置
var setting={
	        data:{
	           simpleData:{
	                  enable: true, 
	                   idKey:"inid", 
	                   pIdKey:"pId",
	                   rootPid:"0"
	        }
	        },
	        async: {
	               enable: true
	               },
	             check:{
	             enable:true,
	            chkStyle:"checkbox",
	            chkboxType: { "Y": "s", "N": "s" }
	             },
	        view:{
	               showIcon:false,
	               dblClickExpand: true
	             },
	        callback: {
	        	onClick: function(e, treeId, treeNode){
	    			var zTree = $.fn.zTree.getZTreeObj("tree");
	    			zTree.checkNode(treeNode, !treeNode.checked, null, true);
	    			return false;
	    		},
				onCheck:function(e, treeId, treeNode){
					var zTree = $.fn.zTree.getZTreeObj("tree");
					var nodes = zTree.getCheckedNodes(true);
					v = "";
					s =" ";
					ids=["1"];
					idi=1;
					count = 0;
					for (var i=0;i<nodes.length; i++) {
							if(nodes[i].level!=0){
								if(!nodes[i].getParentNode().checked){
									v += nodes[i].name + ",";
									s += nodes[i].id + ",";
									ids[idi++]=nodes[i].id;
									count++;
								}
							}else{
								v += nodes[i].name + ",";
								s += nodes[i].id + ",";
								ids[idi++]=nodes[i].id;
								  count++;
							}
					}
						if (v.length > 0 ){
							v = v.substring(0, v.length-1);
							$("#industry").val(v);}
						
						if(s.length >0){
							s=s.substring(0, s.length-1);
							$("#txtId").val(s);
						}
				}
	                 }     
	      }; 
//行业树单选参数设置
var settingRadio={
        data:{
           simpleData:{
                  enable: true, 
                   idKey:"inid", 
                   pIdKey:"pId",
                   rootPid:"0"
        }
        },
        async: {  
               enable: true
               },
             check:{
             enable:true,
            chkStyle: "radio",
            radioType:"all"
             },
        view:{
               showIcon:false,
               dblClickExpand: true
             },
        callback: {  
        	onClick:function(e, treeId, treeNode) {
        		var zTree = $.fn.zTree.getZTreeObj("tree");
        		zTree.checkNode(treeNode, !treeNode.checked, null, true); 
        		return false;
        	},
			onCheck: function(e, treeId, treeNode) {
				 var zTree = $.fn.zTree.getZTreeObj("tree"); 
				 var nodes = zTree.getCheckedNodes(true);
				 $("#txtId").val(treeNode.id);//
			     $("#industry").val(treeNode.name);//存放选择的名称
			     $("#inidTree").val(treeNode.inid);//存放选择名称的id
//			     $("#messageorgCode").html("");//清空提示信息
			}
                 }     
      };
