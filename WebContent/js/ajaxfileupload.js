var fileSuccess=false;
jQuery.extend({
    createUploadIframe: function(id, uri)
	{
			//create frame
            var frameId = 'jUploadFrame' + id;
            
            if(window.ActiveXObject) {
            	   if(!$.browser||$.browser.version=="9.0" || $.browser.version=="10.0"){
            	    	var io = document.createElement('iframe');
            	    	io.id = frameId;
            	    	io.name = frameId;
            	    }else if(jQuery.browser.version=="6.0" || jQuery.browser.version=="7.0" || jQuery.browser.version=="8.0"){
            	    	var io = document.createElement('<iframe id="' + frameId + '" name="' + frameId + '" />');
            	    	if(typeof uri== 'boolean'){
            	    	    io.src = 'javascript:false';
            	    	}
            	    	else if(typeof uri== 'string'){
            	    	    io.src = uri;
            	    	}
            	    }
            	}
            else {
                var io = document.createElement('iframe');
                io.id = frameId;
                io.name = frameId;
            }
            io.style.position = 'absolute';
            io.style.top = '-1000px';
            io.style.left = '-1000px';

            document.body.appendChild(io);

            return io;   
    },
    createUploadForm: function(id, fileElementId,data)
	{
		//create form	
		var formId = 'jUploadForm' + id;
		var fileId = 'jUploadFile' + id;
		var form = $('<form  action="" method="POST" name="' + formId + '" id="' + formId + '" enctype="multipart/form-data"></form>');	
		var oldElement = $('#' + fileElementId);
		var newElement = $(oldElement).clone();
		$(oldElement).attr('id', fileId);
		$(oldElement).before(newElement);
		$(oldElement).appendTo(form);
		//set attributes
		if (data) {
			for ( var i in data) {
				$('<input type="hidden" name="' + i + '" value="' + data[i] + '" />').appendTo(form);
			}
		}
		
		$(form).css('position', 'absolute');
		$(form).css('top', '-1200px');
		$(form).css('left', '-1200px');
		$(form).appendTo('body');		
		return form;
    },

    ajaxFileUpload: function(s) {
        // TODO introduce global settings, allowing the client to modify them for all requests, not only timeout		
    	pop_up_box.showLoadImg();
        s = jQuery.extend({}, jQuery.ajaxSettings, s);
        var id = new Date().getTime()        
		var form = jQuery.createUploadForm(id, s.fileElementId,s.data);
		var io = jQuery.createUploadIframe(id, s.secureuri);
		var frameId = 'jUploadFrame' + id;
		var formId = 'jUploadForm' + id;		
        // Watch for a new set of requests
        if ( s.global && ! jQuery.active++ )
		{
			jQuery.event.trigger( "ajaxStart" );
		}            
        var requestDone = false;
        // Create the request object
        var xml = {};   
        if( s.global )
        {
         jQuery.event.trigger("ajaxSend", [xml, s]);
        }            
        
        var uploadCallback = function(isTimeout)
		{			
			var io = document.getElementById(frameId);
            try 
			{				
				if(io.contentWindow)
				{
					 xml.responseText = io.contentWindow.document.body?io.contentWindow.document.body.innerHTML:null;
                	 xml.responseXML = io.contentWindow.document.XMLDocument?io.contentWindow.document.XMLDocument:io.contentWindow.document;
					 
				}else if(io.contentDocument)
				{
					 xml.responseText = io.contentDocument.document.body?io.contentDocument.document.body.innerHTML:null;
                	xml.responseXML = io.contentDocument.document.XMLDocument?io.contentDocument.document.XMLDocument:io.contentDocument.document;
				}						
            }catch(e)
			{
				jQuery.handleError(s, xml, null, e);
			}
            if ( xml || isTimeout == "timeout") 
			{				
                requestDone = true;
                var status;
                try {
                	 closeLoadImg();
                    status = isTimeout != "timeout" ? "success" : "error";
                    // Make sure that the request was successful or notmodified
                    if ( status != "error" )
					{
                        // process the data (runs the xml through httpData regardless of callback)
                        var data = jQuery.uploadHttpData( xml, s.dataType );    
                        // If a local callback was specified, fire it and pass it the data
                        fileSuccess=false;
                        if ( s.success ){
                        	fileSuccess=true;
                        	loadWaitClose();
                        	s.success( data, status );
                        }
                        // Fire the global callback
                        if( s.global )
                            jQuery.event.trigger( "ajaxSuccess", [xml, s] );
                    } else
                        jQuery.handleError(s, xml, status);
                } catch(e) 
				{
                    status = "error";
                    jQuery.handleError(s, xml, status, e);
                }

                // The request was completed
                if( s.global )
                    jQuery.event.trigger( "ajaxComplete", [xml, s] );

                // Handle the global AJAX counter
                if ( s.global && ! --jQuery.active )
                    jQuery.event.trigger( "ajaxStop" );

                // Process result
                if ( s.complete )
                    s.complete(xml, status);

                jQuery(io).unbind()

                setTimeout(function()
									{	try 
										{
											$(io).remove();
											$(form).remove();	
											
										} catch(e) 
										{
											jQuery.handleError(s, xml, null, e);
										}									

									}, 100)

                xml = null

            }
        }
        // Timeout checker
        if ( s.timeout > 0 ) 
		{
            setTimeout(function(){
                // Check to see if the request is still happening
                if( !requestDone ) uploadCallback( "timeout" );
            }, s.timeout);
        }
        try 
		{
           // var io = $('#' + frameId);
			var form = $('#' + formId);
			$(form).attr('action', s.url);
			$(form).attr('method', 'POST');
			$(form).attr('target', frameId);
            if(form.encoding)
			{
                form.encoding = 'multipart/form-data';				
            }
            else
			{				
                form.enctype = 'multipart/form-data';
            }			
            $(form).submit();

        } catch(e) 
		{			
            jQuery.handleError(s, xml, null, e);
        }
        if(window.attachEvent){
            document.getElementById(frameId).attachEvent('onload', uploadCallback);
        }
        else{
            document.getElementById(frameId).addEventListener('load', uploadCallback, false);
        } 		
        return {abort: function () {}};	

    },

    uploadHttpData: function( r, type ) {
        var data = !type;
        data = type == "xml" || data ? r.responseXML : r.responseText;
        // If the type is "script", eval it in global context
        if ( type == "script" )
            jQuery.globalEval( data );
        // Get the JavaScript object, if JSON is used.
        if ( type == "json" ){
        	////////////以下为新增代码///////////////  
            data = r.responseText;  
        	var start = data.indexOf(">");  
            if(start != -1) {  
	        	var end = data.indexOf("<", start + 1);  
	        	if(end != -1) {  
	        	  data = data.substring(start + 1, end);  
	        	}  
            }  
            ///////////以上为新增代码///////////////  
            eval( "data = " + data );
        // evaluate scripts within html
        }
        if ( type == "html" )
            jQuery("<div>").html(data).evalScripts();
			//alert($('param', data).each(function(){alert($(this).attr('value'));}));
        return data;
    },
    handleError: function( s, xhr, status, e ) {
    	// If a local callback was specified, fire it
    			if ( s.error ) {
    				fileSuccess=true;
    				closeProgress();
    				s.error.call( s.context || s, xhr, status, e );
    			}

    			// Fire the global callback
    			if ( s.global ) {
    				(s.context ? jQuery(s.context) : jQuery.event).trigger( "ajaxError", [xhr, s, e] );
    			}
    }
});
/**
 * ajaxFileUpload上传文件
 * @param params 参数json以下是具体子项
 * @param fid 返回上传文件id值的文本框id
 * @param msgId 返回信息的id
 * @param fileId 上传file组件的id或者name
 * @param msg 提示信息
 * @param uploadUrl 上传地址url
 * @param callback(boolean) 回调函数用于数据状态的返回
 */
function ajaxUploadFile(params,target,callback){
	var isIE = /msie/i.test(navigator.userAgent) && !window.opera;   
	   if(!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(target.value))
       {
			 $("#"+params.msgId).html("<font color=red>图片类型必须是.GIF,.JPEG,.JPG,.PNG）结尾的一种</font>");
         return false;
       }else{
    	   $("#"+params.msgId).html("");
       }
	var fileSize = 0;
//	var target=document.getElementsByName(params.fileId);
	  if (isIE && !target.files) {
	   var filePath = target.value;  
	   try{
		    var fileSystem = new ActiveXObject("Scripting.FileSystemObject");         
		    var file = fileSystem.GetFile (filePath);      
		    fileSize = file.Size; 
	   }catch(e){
		   
	   }
	  } else {
		  try {
			  document.getElementById(params.fileId).value=target.value;
		} catch (e) {
		}
	   fileSize = target.files[0].size;  
	   }    
	  var size = fileSize / 1024/1024;
	  if(size>uploadFileSize){
		  pop_up_box.showMsg("文件不能大于10M");
	  $("input[name='"+fileId+"']").val("");
		 return false;
	   } 
	//设置上传提示---begin----
	pop_up_box.showLoadImg();
//设置上传提示---end----
	$.ajaxFileUpload({
		   url:params.uploadUrl,
		   secureuri:false,  
	     fileElementId:params.fileId,                        //文件选择框的id属性  
	     dataType: 'JSON',                                     //服务器返回的格式，可以是json  
	     beforeSend:function(XMLHttpRequest){
	    	 //上传开始,不会执行    	  
	     },
	     ajaxSend:function(XMLHttpRequest){
	    	 ///上传开始,不会执行    
	     },
	     success: function (data, status) {
	    	 data=$.parseJSON(data); 
	    	 closeLoadImg();
	  	   $("#"+params.fid).val(data.msg);
	  	   $("#"+params.msgId).html("<font color=green>"+params.msg+"上传成功</font>");
	  	   if (callback) {
	  		   callback(data.msg);
	  	   }
			   return true;
		   },
		   error:function(data, status, e){
			   closeLoadImg();
			 if (!data.success) {
				 data=$.parseJSON(data.responseText); 
			}
			 if (data.success) {
			  	   $("#"+params.fid).val(data.msg);
			  	   $("#"+params.msgId).html("<font color=green>"+params.msg+"上传成功</font>");
			}else{
				$("#"+params.msgId).append("<font color=red>"+params.msg+"上传失败</font>");    
			}
			 if (callback) {
				 callback(data.msg);
			 }
             return false;
		   },
		   complete:function(XMLHttpRequest, textStatus){
			  ///上传完成
			   closeLoadImg();
		   }
	});
}
function closeLoadImg(){}
var startTime;
var progressBar;
var interval;
function showProgress(){
	var myDate = new Date();
	startTime = myDate.getTime();
	$("#progress,#zhezhao").show();
//	progressBar=window.setTimeout("getProgressBar()", 500);
	getProgressBar();
}
function closeProgress(){
	setTimeout(function(){
		$("#progress,#zhezhao").remove();
		window.clearTimeout(interval);
		window.clearTimeout(progressBar);
		if (!fileSuccess) {
			pop_up_box.dataHandlingWait();
		}
	}, 500);
}
function getProgressBar() {
	var timestamp = (new Date()).valueOf();
	var bytesReadToShow = 0;
	var contentLengthToShow = 0;
	var bytesReadGtMB = 0;
	var contentLengthGtMB = 0;
	$.getJSON("/upload/getBar.do", {"t":timestamp}, function (json) {
		var bytesRead = (json.pBytesRead / 1024).toString();
		if (bytesRead > 1024) {
			bytesReadToShow = (bytesRead / 1024).toString();
			bytesReadGtMB = 1;
		}else{
			bytesReadToShow = bytesRead.toString();
		}
		var contentLength = (json.pContentLength / 1024).toString();
		if (contentLength > 1024) {
			contentLengthToShow = (contentLength / 1024).toString();
			contentLengthGtMB = 1;
		}else{
			contentLengthToShow= contentLength.toString();
		}
		$("#json").append(json);
		bytesReadToShow = bytesReadToShow.substring(0, bytesReadToShow.lastIndexOf(".") + 3);
		contentLengthToShow = contentLengthToShow.substring(0, contentLengthToShow.lastIndexOf(".") + 3);
		if (bytesRead == contentLength) {
			$("#close").show();
			$("#progressImg").css("width", "300px");
			if (contentLengthGtMB == 0) {
				$("div#info").html("\u4e0a\u4f20\u5b8c\u6210\uff01\u603b\u5171\u5927\u5c0f" + contentLengthToShow + "KB.\u5b8c\u6210100%");
				
			} else {
				$("div#info").html("\u4e0a\u4f20\u5b8c\u6210\uff01\u603b\u5171\u5927\u5c0f" + contentLengthToShow + "MB.\u5b8c\u6210100%");
			}
			closeProgress();
//			$("#subButton").attr("disabled", false);
		} else {
			var pastTimeBySec = (new Date().getTime() - startTime) / 1000;
			var sp = (bytesRead / pastTimeBySec).toString();
			var speed = sp.substring(0, sp.lastIndexOf(".") + 3);
			var percent = Math.floor((bytesRead / contentLength) * 100) + "%";
			$("#progressImg").css("width", percent);$("#json").append(percent+",");
			if (bytesReadGtMB == 0 && contentLengthGtMB == 0) {
				$("div#info").html("\u4e0a\u4f20\u901f\u5ea6:" + speed + "KB/s,\u5df2\u7ecf\u8bfb\u53d6" + bytesReadToShow + "KB <br>\u603b\u5171\u5927\u5c0f:" + contentLengthToShow + "KB.\u5b8c\u6210" + percent);
			} else {
				if (bytesReadGtMB == 0 && contentLengthGtMB == 1) {
					$("div#info").html("\u4e0a\u4f20\u901f\u5ea6:" + speed + "KB/s,\u5df2\u7ecf\u8bfb\u53d6" + bytesReadToShow + "KB <br>\u603b\u5171\u5927\u5c0f:" + contentLengthToShow + "MB.\u5b8c\u6210" + percent);
				} else {
					if (bytesReadGtMB == 1 && contentLengthGtMB == 1) {
						$("div#info").html("\u4e0a\u4f20\u901f\u5ea6:" + speed + "KB/s,\u5df2\u7ecf\u8bfb\u53d6" + bytesReadToShow + "MB <br>\u603b\u5171\u5927\u5c0f:" + contentLengthToShow + "MB.\u5b8c\u6210" + percent);
					}
				}
			}
		}
	});
	interval = window.setTimeout("getProgressBar()", 500);
}  

/**
 * 上传大小控制
 *   10M
 */
var uploadFileSize = 10;
/**
 * 删除上传的图片
 * @param imgurl
 * @param t
 */
function deleteImg(imgurl,t){
	$.get("/upload/removeTemp.do",{"imgUrl":imgurl},function(data){
		if (data.success) {
			$(t).parent().remove();
			pop_up_box.showMsg("删除成功");
		}
	});
}
/**
 * 上传图片
 * @param t
 */
function imageUpload(t){
	ajaxUploadFile({
		"uploadUrl":"/upload/uploadImage.do",
		"msgId":"msg",
		"fileId":"imgFile",
		"msg":"图片",
		"fid":""
	},t,function(imgUrl){
		pop_up_box.loadWaitClose();
		var li=$("<li>");
		var showa=$("<a class='abtn'>");
		var del=$("<a class='abtn'>");
		var imgval=$("<input type='hidden' name='imgUrls'>");
		var imgli='<li><a class="abtn" onclick="pop_up_box.showImg('+imgUrl+')">查看</a>&emsp;<a class="abtn" onclick="deleteImg('+imgUrl+',this)">删除</a>'
			+'<input type="hidden" name="imgUrls.imgUrl" value='+imgUrl+'></li>';
		li.append(showa);
		li.append("&emsp;");
		li.append(del);
		li.append(imgval);
		$("#imgUl").append(li);
		imgval.val(imgUrl);
		showa.html("查看");
		del.html("删除");
		showa.bind("click",function(){
			pop_up_box.showImg(imgUrl);
		});
		del.bind("click",function(){
			deleteImg(imgUrl,this);
		});
	});
}