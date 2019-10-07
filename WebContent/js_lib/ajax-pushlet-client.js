
var PL={NV_P_FORMAT:'p_format=xml-strict',NV_P_MODE:'p_mode=pull',pushletURL:null,webRoot:null,sessionId:null,STATE_ERROR:-2,STATE_ABORT:-1,STATE_NULL:1,STATE_READY:2,STATE_JOINED:3,STATE_LISTENING:3,state:1,heartbeat:function(){PL._doRequest('hb');},join:function(){PL.sessionId=null;PL._doRequest('join',PL.NV_P_FORMAT+'&'+PL.NV_P_MODE);},joinListen:function(aSubject){PL._setStatus('join-listen '+aSubject);PL.sessionId=null;var query=PL.NV_P_FORMAT+'&'+PL.NV_P_MODE;if(aSubject){query=query+'&p_subject='+aSubject;}
PL._doRequest('join-listen',query);},leave:function(){PL._doRequest('leave');},listen:function(aSubject){var query=PL.NV_P_MODE;if(aSubject){query=query+'&p_subject='+aSubject;}
PL._doRequest('listen',query);},publish:function(aSubject,theQueryArgs){var query='p_subject='+aSubject;if(theQueryArgs){query=query+'&'+theQueryArgs;}
PL._doRequest('publish',query);},subscribe:function(aSubject,aLabel){var query='p_subject='+aSubject;if(aLabel){query=query+'&p_label='+aLabel;}
PL._doRequest('subscribe',query);},unsubscribe:function(aSubscriptionId){var query;if(aSubscriptionId){query='p_sid='+aSubscriptionId;}
PL._doRequest('unsubscribe',query);},setDebug:function(bool){PL.debugOn=bool;},_addEvent:function(elm,evType,callback,useCapture){var obj=PL._getObject(elm);if(obj.addEventListener){obj.addEventListener(evType,callback,useCapture);return true;}else if(obj.attachEvent){var r=obj.attachEvent('on'+evType,callback);return r;}else{obj['on'+evType]=callback;}},_doCallback:function(event,cbFunction){if(cbFunction){cbFunction(event);}else if(window.onEvent){onEvent(event);}},_doRequest:function(anEvent,aQuery){if(PL.state<0){PL._setStatus('died ('+PL.state+')');return;}
var waitForState=false;if(anEvent=='join'||anEvent=='join-listen'){waitForState=(PL.state<PL.STATE_READY);}else if(anEvent=='leave'){PL.state=PL.STATE_READY;}else if(anEvent=='refresh'){if(PL.state!=PL.STATE_LISTENING){return;}}else if(anEvent=='listen'){waitForState=(PL.state<PL.STATE_JOINED);}else if(anEvent=='subscribe'||anEvent=='unsubscribe'){waitForState=(PL.state<PL.STATE_LISTENING);}else{waitForState=(PL.state<PL.STATE_JOINED);}
if(waitForState==true){PL._setStatus(anEvent+' , waiting... state='+PL.state);setTimeout(function(){PL._doRequest(anEvent,aQuery);},100);return;}
var url=PL.pushletURL+'?p_event='+anEvent;if(aQuery){url=url+'&'+aQuery;}
if(PL.sessionId!=null){url=url+'&p_id='+PL.sessionId;if(anEvent=='p_leave'){PL.sessionId=null;}}
PL.debug('_doRequest',url);PL._getXML(url,PL._onResponse);},_getObject:function(obj){if(typeof obj=="string"){return document.getElementById(obj);}else{return obj;}},_getWebRoot:function(){if(PL.webRoot!=null){return PL.webRoot;}
var head=document.getElementsByTagName('head')[0];var nodes=head.childNodes;for(var i=0;i<nodes.length;++i){var src=nodes.item(i).src;if(src){var index=src.indexOf("ajax-pushlet-client.js");if(index>=0){index=src.indexOf("lib");PL.webRoot=src.substring(0,index);break;}}}
return PL.webRoot;},_getXML:function(url,callback){var xmlhttp=new XMLHttpRequest();if(!xmlhttp||xmlhttp==null){alert('No browser XMLHttpRequest (AJAX) support');return;}
var cb=callback;var async=false;if(cb){async=true;xmlhttp.onreadystatechange=function(){if(xmlhttp.readyState==4){if(xmlhttp.status==200){cb(xmlhttp.responseXML);xmlhttp=null;}else{var event=new PushletEvent();event.put('p_event','error')
event.put('p_reason','[pushlet] problem retrieving XML data:\n'+xmlhttp.statusText);PL._onEvent(event);}}};}
xmlhttp.open('GET',url,async);xmlhttp.send(null);if(!cb){if(xmlhttp.status!=200){var event=new PushletEvent();event.put('p_event','error')
event.put('p_reason','[pushlet] problem retrieving XML data:\n'+xmlhttp.statusText);PL._onEvent(event)
return null;}
return xmlhttp.responseXML;}},_init:function(){PL._showStatus();PL._setStatus('initializing...');if(window.ActiveXObject&&!window.XMLHttpRequest){window.XMLHttpRequest=function(){var msxmls=new Array('Msxml2.XMLHTTP.5.0','Msxml2.XMLHTTP.4.0','Msxml2.XMLHTTP.3.0','Msxml2.XMLHTTP','Microsoft.XMLHTTP');for(var i=0;i<msxmls.length;i++){try{return new ActiveXObject(msxmls[i]);}catch(e){}}
return null;};}
if(!window.ActiveXObject&&window.XMLHttpRequest){window.ActiveXObject=function(type){switch(type.toLowerCase()){case'microsoft.xmlhttp':case'msxml2.xmlhttp':case'msxml2.xmlhttp.3.0':case'msxml2.xmlhttp.4.0':case'msxml2.xmlhttp.5.0':return new XMLHttpRequest();}
return null;};}
PL.pushletURL=contextPath+'/pushlet.srv';PL._setStatus('initialized');PL.state=PL.STATE_READY;},_onEvent:function(event){PL.debug('_onEvent()',event.toString());var eventType=event.getEvent();if(eventType=='data'){PL._setStatus('data');PL._doCallback(event,window.onData);}else if(eventType=='refresh'){if(PL.state<PL.STATE_LISTENING){PL._setStatus('not refreshing state='+PL.STATE_LISTENING);}
var timeout=event.get('p_wait');setTimeout(function(){PL._doRequest('refresh');},timeout);return;}else if(eventType=='error'){PL.state=PL.STATE_ERROR;PL._setStatus('server error: '+event.get('p_reason'));PL._doCallback(event,window.onError);}else if(eventType=='join-ack'){PL.state=PL.STATE_JOINED;PL.sessionId=event.get('p_id');PL._setStatus('connected');PL._doCallback(event,window.onJoinAck);}else if(eventType=='join-listen-ack'){PL.state=PL.STATE_LISTENING;PL.sessionId=event.get('p_id');PL._setStatus('join-listen-ack');PL._doCallback(event,window.onJoinListenAck);}else if(eventType=='listen-ack'){PL.state=PL.STATE_LISTENING;PL._setStatus('listening');PL._doCallback(event,window.onListenAck);}else if(eventType=='hb'){PL._setStatus('heartbeat');PL._doCallback(event,window.onHeartbeat);}else if(eventType=='hb-ack'){PL._doCallback(event,window.onHeartbeatAck);}else if(eventType=='leave-ack'){PL._setStatus('disconnected');PL._doCallback(event,window.onLeaveAck);}else if(eventType=='refresh-ack'){PL._doCallback(event,window.onRefreshAck);}else if(eventType=='subscribe-ack'){PL._setStatus('subscribed to '+event.get('p_subject'));PL._doCallback(event,window.onSubscribeAck);}else if(eventType=='unsubscribe-ack'){PL._setStatus('unsubscribed');PL._doCallback(event,window.onUnsubscribeAck);}else if(eventType=='abort'){PL.state=PL.STATE_ERROR;PL._setStatus('abort');PL._doCallback(event,window.onAbort);}else if(eventType.match(/nack$/)){PL._setStatus('error response: '+event.get('p_reason'));PL._doCallback(event,window.onNack);}},_onResponse:function(xml){PL.debug('_onResponse',xml);var events=PL._rsp2Events(xml);if(events==null){PL._setStatus('null events')
return;}
delete xml;PL.debug('_onResponse eventCnt=',events.length);for(i=0;i<events.length;i++){PL._onEvent(events[i]);}},_rsp2Events:function(xml){if(!xml||!xml.documentElement){return null;}
var eventElements=xml.documentElement.getElementsByTagName('event');var events=new Array(eventElements.length);for(i=0;i<eventElements.length;i++){events[i]=new PushletEvent(eventElements[i]);}
return events;},statusMsg:'null',statusChanged:false,statusChar:'|',_showStatus:function(){if(PL.statusChanged==true){if(PL.statusChar=='|')PL.statusChar='/';else if(PL.statusChar=='/')PL.statusChar='--';else if(PL.statusChar=='--')PL.statusChar='\\';else PL.statusChar='|';PL.statusChanged=false;}
window.defaultStatus=PL.statusMsg;window.status=PL.statusMsg+'  '+PL.statusChar;timeout=window.setTimeout('PL._showStatus()',400);},_setStatus:function(status){PL.statusMsg="pushlet - "+status;PL.statusChanged=true;},timestamp:0,debugWindow:null,messages:new Array(),messagesIndex:0,debugOn:false,debug:function(label,value){if(PL.debugOn==false){return;}
var funcName="none";if(PL.debug.caller){funcName=PL.debug.caller.toString()
funcName=funcName.substring(9,funcName.indexOf(")")+1)}
var msg="-"+funcName+": "+label+"="+value
var now=new Date()
var elapsed=now-PL.timestamp
if(elapsed<10000){msg+=" ("+elapsed+" msec)"}
PL.timestamp=now;if((PL.debugWindow==null)||PL.debugWindow.closed){PL.debugWindow=window.open("","p_debugWin","toolbar=no,scrollbars=yes,resizable=yes,width=600,height=400");}
PL.messages[PL.messagesIndex++]=msg
PL.debugWindow.document.writeln('<html><head><title>Pushlet Debug Window</title></head><body bgcolor=#DDDDDD>');for(var i=0;i<PL.messagesIndex;i++){PL.debugWindow.document.writeln('<pre>'+i+': '+PL.messages[i]+'</pre>');}
PL.debugWindow.document.writeln('</body></html>');PL.debugWindow.document.close();PL.debugWindow.focus();}}
function PushletEvent(xml){this.arr=new Array();this.getSubject=function(){return this.get('p_subject');}
this.getEvent=function(){return this.get('p_event');}
this.put=function(name,value){return this.arr[name]=value;}
this.get=function(name){return this.arr[name];}
this.toString=function(){var res='';for(var i in this.arr){res=res+i+'='+this.arr[i]+'\n';}
return res;}
this.toTable=function(){var res='<table border="1" cellpadding="3">';var styleDiv='<div style="color:black; font-family:monospace; font-size:10pt; white-space:pre;">'
for(var i in this.arr){res=res+'<tr><td bgColor=white>'+styleDiv+i+'</div></td><td bgColor=white>'+styleDiv+this.arr[i]+'</div></td></tr>';}
res+='</table>'
return res;}
if(xml){for(var i=0;i<xml.attributes.length;i++){this.put(xml.attributes[i].name,xml.attributes[i].value);}}}
function p_debug(aBool,aLabel,aMsg){if(aBool==false){return;}
PL.setDebug(true);PL.debug(aLabel,aMsg);PL.setDebug(false);}
function p_embed(thePushletWebRoot){alert('Pushlet: p_embed() is no longer required for AJAX client')}
function p_join(){PL.join();}
function p_listen(aSubject,aMode){PL.listen(aSubject);}
function p_join_listen(aSubject){PL.joinListen(aSubject);}
function p_leave(){PL.leave();}
function p_heartbeat(){PL.heartbeat();}
function p_publish(aSubject,nvPairs){var args=p_publish.arguments;var query='';var amp='';for(var i=1;i<args.length;i++){if(i>1){amp='&';}
query=query+amp+args[i]+'='+args[++i];}
PL.publish(aSubject,query);}
function p_subscribe(aSubject,aLabel){PL.subscribe(aSubject,aLabel);}
function p_unsubscribe(aSid){PL.unsubscribe(aSid);}
PL._addEvent(window,'load',PL._init,false);