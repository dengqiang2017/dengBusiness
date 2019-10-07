var isIE = (document.all) ? true : false;

var $ = function (id) {
	return "string" == typeof id ? document.getElementById(id) : id;
};

var Class = {
	create: function() {
		return function() { this.initialize.apply(this, arguments); }
	}
}

var Extend = function(destination, source) {
	for (var property in source) {
		destination[property] = source[property];
	}
}

var Bind = function(object, fun) {
	var args = Array.prototype.slice.call(arguments).slice(2);
	return function() {
		return fun.apply(object, args);
	}
}

var BindAsEventListener = function(object, fun) {
	return function(event) {
		return fun.call(object, Event(event));
	}
}

function Event(e){
	var oEvent = isIE ? window.event : e;
	if (isIE) {
		oEvent.pageX = oEvent.clientX + document.documentElement.scrollLeft;
		oEvent.pageY = oEvent.clientY + document.documentElement.scrollTop;
		oEvent.preventDefault = function () { this.returnValue = false; };
		oEvent.detail = oEvent.wheelDelta / (-40);
		oEvent.stopPropagation = function(){ this.cancelBubble = true; }; 
	}
	return oEvent;
}

var CurrentStyle = function(element){
	return element.currentStyle || document.defaultView.getComputedStyle(element, null);
}

function addEventHandler(oTarget, sEventType, fnHandler) {
	if (oTarget.addEventListener) {
		oTarget.addEventListener(sEventType, fnHandler, false);
	} else if (oTarget.attachEvent) {
		oTarget.attachEvent("on" + sEventType, fnHandler);
	} else {
		oTarget["on" + sEventType] = fnHandler;
	}
};

function removeEventHandler(oTarget, sEventType, fnHandler) {
    if (oTarget.removeEventListener) {
        oTarget.removeEventListener(sEventType, fnHandler, false);
    } else if (oTarget.detachEvent) {
        oTarget.detachEvent("on" + sEventType, fnHandler);
    } else { 
        oTarget["on" + sEventType] = null;
    }
};


//婊戝姩鏉＄▼搴�
var Slider = Class.create();
Slider.prototype = {
  //瀹瑰櫒瀵硅薄锛屾粦鍧�
  initialize: function(container, bar, options) {
	this.Bar = $(bar);
	this.Container = $(container);
	this._timer = null;//鑷姩婊戠Щ鐨勫畾鏃跺櫒
	this._ondrag = false;//瑙ｅ喅ie鐨刢lick闂
	//鏄惁鏈€灏忓€笺€佹渶澶у€笺€佷腑闂村€�
	this._IsMin = this._IsMax = this._IsMid = false;
	//瀹炰緥鍖栦竴涓嫋鏀惧璞★紝骞堕檺瀹氳寖鍥�
	this._drag = new Drag(this.Bar, { Limit: true, mxContainer: this.Container,
		onStart: Bind(this, this.DragStart), onStop: Bind(this, this.DragStop), onMove: Bind(this, this.Move)
	});
	
	this.SetOptions(options);
	
	this.WheelSpeed = Math.max(0, this.options.WheelSpeed);
	this.KeySpeed = Math.max(0, this.options.KeySpeed);
	
	this.MinValue = this.options.MinValue;
	this.MaxValue = this.options.MaxValue;
	
	this.RunTime = Math.max(1, this.options.RunTime);
	this.RunStep = Math.max(1, this.options.RunStep);
	
	this.Ease = !!this.options.Ease;
	this.EaseStep = Math.max(1, this.options.EaseStep);
	
	this.onMin = this.options.onMin;
	this.onMax = this.options.onMax;
	this.onMid = this.options.onMid;
	
	this.onDragStart = this.options.onDragStart;
	this.onDragStop = this.options.onDragStop;
	
	this.onMove = this.options.onMove;
	
	this._horizontal = !!this.options.Horizontal;//涓€鑸笉鍏佽淇敼
	
	//閿佸畾鎷栨斁鏂瑰悜
	this._drag[this._horizontal ? "LockY" : "LockX"] = true;
	
	//鐐瑰嚮鎺у埗
	addEventHandler(this.Container, "click", BindAsEventListener(this, function(e){ this._ondrag || this.ClickCtrl(e);}));
	//鍙栨秷鍐掓场锛岄槻姝㈣窡Container鐨刢lick鍐茬獊
	addEventHandler(this.Bar, "click", BindAsEventListener(this, function(e){ e.stopPropagation(); }));
	
	//璁剧疆榧犳爣婊氳疆鎺у埗
	this.WheelBind(this.Container);
	//璁剧疆鏂瑰悜閿帶鍒�
	this.KeyBind(this.Container);
	//淇鑾峰彇鐒︾偣
	var oFocus = isIE ? (this.KeyBind(this.Bar), this.Bar) : this.Container;
	addEventHandler(this.Bar, "mousedown", function(){ oFocus.focus(); });
	//ie榧犳爣鎹曡幏鍜宖f鐨勫彇娑堥粯璁ゅ姩浣滈兘涓嶈兘鑾峰緱鐒︾偣锛屾墍浠ヨ鎵嬪姩鑾峰彇
	//濡傛灉ie鎶奻ocus璁剧疆鍒癈ontainer锛岄偅涔堝湪鍑虹幇婊氬姩鏉℃椂ie鐨刦ocus鍙兘浼氬鑷磋嚜鍔ㄦ粴灞�
  },
  //璁剧疆榛樿灞炴€�
  SetOptions: function(options) {
	this.options = {//榛樿鍊�
		MinValue:	0,//鏈€灏忓€�
		MaxValue:	100,//鏈€澶у€�
		WheelSpeed: 5,//榧犳爣婊氳疆閫熷害,瓒婂ぇ瓒婂揩(0鍒欏彇娑堥紶鏍囨粴杞帶鍒�)
		KeySpeed: 	50,//鏂瑰悜閿粴鍔ㄩ€熷害,瓒婂ぇ瓒婃參(0鍒欏彇娑堟柟鍚戦敭鎺у埗)
		Horizontal:	true,//鏄惁姘村钩婊戝姩
		RunTime:	20,//鑷姩婊戠Щ鐨勫欢鏃舵椂闂�,瓒婂ぇ瓒婃參
		RunStep:	2,//鑷姩婊戠Щ姣忔婊戝姩鐨勭櫨鍒嗘瘮
		Ease:		false,//鏄惁缂撳姩
		EaseStep:	5,//缂撳姩绛夌骇,瓒婂ぇ瓒婃參
		onMin:		function(){},//鏈€灏忓€兼椂鎵ц
		onMax:		function(){},//鏈€澶у€兼椂鎵ц
		onMid:		function(){},//涓棿鍊兼椂鎵ц
		onDragStart:function(){},//鎷栧姩寮€濮嬫椂鎵ц
		onDragStop:	function(){},//鎷栧姩缁撴潫鏃舵墽琛�
		onMove:		function(){}//婊戝姩鏃舵墽琛�
	};
	Extend(this.options, options || {});
  },
  //寮€濮嬫嫋鏀炬粦鍔�
  DragStart: function() {
  	this.Stop();
	this.onDragStart();
	this._ondrag = true;
  },
  //缁撴潫鎷栨斁婊戝姩
  DragStop: function() {
  	this.onDragStop();
	setTimeout(Bind(this, function(){ this._ondrag = false; }), 10);
  },
  //婊戝姩涓�
  Move: function() {
  	this.onMove();
	
	var percent = this.GetPercent();
	//鏈€灏忓€煎垽鏂�
	if(percent > 0){
		this._IsMin = false;
	}else{
		if(!this._IsMin){ this.onMin(); this._IsMin = true; }
	}
	//鏈€澶у€煎垽鏂�
	if(percent < 1){
		this._IsMax = false;
	}else{
		if(!this._IsMax){ this.onMax(); this._IsMax = true; }
	}
	//涓棿鍊煎垽鏂�
	if(percent > 0 && percent < 1){
		if(!this._IsMid){ this.onMid(); this._IsMid = true; }
	}else{
		this._IsMid = false;
	}
  },
  //榧犳爣鐐瑰嚮鎺у埗
  ClickCtrl: function(e) {
	var o = this.Container, iLeft = o.offsetLeft, iTop = o.offsetTop;
	while (o.offsetParent) { o = o.offsetParent; iLeft += o.offsetLeft; iTop += o.offsetTop; }
	//鑰冭檻鏈夋粴鍔ㄦ潯锛岃鐢╬ageX鍜宲ageY
	this.EasePos(e.pageX - iLeft - this.Bar.offsetWidth / 2, e.pageY - iTop - this.Bar.offsetHeight / 2);
  },
  //榧犳爣婊氳疆鎺у埗
  WheelCtrl: function(e) {
	var i = this.WheelSpeed * e.detail;
	this.SetPos(this.Bar.offsetLeft + i, this.Bar.offsetTop + i);
	//闃叉瑙﹀彂鍏朵粬婊氬姩鏉�
	e.preventDefault();
  },
  //缁戝畾榧犳爣婊氳疆
  WheelBind: function(o) {
  	//榧犳爣婊氳疆鎺у埗
	addEventHandler(o, isIE ? "mousewheel" : "DOMMouseScroll", BindAsEventListener(this, this.WheelCtrl));
  },
  //鏂瑰悜閿帶鍒�
  KeyCtrl: function(e) {
	if(this.KeySpeed){
		var iLeft = this.Bar.offsetLeft, iWidth = (this.Container.clientWidth - this.Bar.offsetWidth) / this.KeySpeed
			, iTop = this.Bar.offsetTop, iHeight = (this.Container.clientHeight - this.Bar.offsetHeight) / this.KeySpeed;
		//鏍规嵁鎸夐敭璁剧疆鍊�
		switch (e.keyCode) {
			case 37 ://宸�
				iLeft -= iWidth; break;
			case 38 ://涓�
				iTop -= iHeight; break;
			case 39 ://鍙�
				iLeft += iWidth; break;
			case 40 ://涓�
				iTop += iHeight; break;
			default :
				return;//涓嶆槸鏂瑰悜鎸夐敭杩斿洖
		}
		this.SetPos(iLeft, iTop);
		//闃叉瑙﹀彂鍏朵粬婊氬姩鏉�
		e.preventDefault();
	}
  },
  //缁戝畾鏂瑰悜閿�
  KeyBind: function(o) {
	addEventHandler(o, "keydown", BindAsEventListener(this, this.KeyCtrl));
	//璁剧疆tabIndex浣胯缃璞¤兘鏀寔focus
	o.tabIndex = -1;
	//鍙栨秷focus鏃跺嚭鐜扮殑铏氱嚎妗�
	isIE || (o.style.outline = "none");
  },
  //鑾峰彇褰撳墠鍊�
  GetValue: function() {
	//鏍规嵁鏈€澶ф渶灏忓€煎拰婊戝姩鐧惧垎姣斿彇鍊�
	return this.MinValue + this.GetPercent() * (this.MaxValue - this.MinValue);
  },
  //璁剧疆鍊间綅缃�
  SetValue: function(value) {
	//鏍规嵁鏈€澶ф渶灏忓€煎拰鍙傛暟鍊艰缃粦鍧椾綅缃�
	this.SetPercent((value- this.MinValue)/(this.MaxValue - this.MinValue));
  },
  //鑾峰彇鐧惧垎姣�
  GetPercent: function() {
	//鏍规嵁婊戝姩鏉℃粦鍧楀彇鐧惧垎姣�
	return this._horizontal ? this.Bar.offsetLeft / (this.Container.clientWidth - this.Bar.offsetWidth)
		: this.Bar.offsetTop / (this.Container.clientHeight - this.Bar.offsetHeight)
  },
  //璁剧疆鐧惧垎姣斾綅缃�
  SetPercent: function(value) {
	//鏍规嵁鐧惧垎姣旇缃粦鍧椾綅缃�
	this.EasePos((this.Container.clientWidth - this.Bar.offsetWidth) * value, (this.Container.clientHeight - this.Bar.offsetHeight) * value);
  },
  //鑷姩婊戠Щ(鏄惁閫掑)
  Run: function(bIncrease) {
	this.Stop();
	//淇涓€涓媌Increase
	bIncrease = !!bIncrease;
	//鏍规嵁鏄惁閫掑鏉ヨ缃€�
	var percent = this.GetPercent() + (bIncrease ? 1 : -1) * this.RunStep / 100;
	this.SetPos((this.Container.clientWidth - this.Bar.offsetWidth) * percent, (this.Container.clientHeight - this.Bar.offsetHeight) * percent);
	//濡傛灉娌℃湁鍒版瀬闄愬€煎氨缁х画婊戠Щ
	if(!(bIncrease ? this._IsMax : this._IsMin)){
		this._timer = setTimeout(Bind(this, this.Run, bIncrease), this.RunTime);
	}
  },
  //鍋滄婊戠Щ
  Stop: function() {
	clearTimeout(this._timer);
  },
  //缂撳姩婊戠Щ
  EasePos: function(iLeftT, iTopT) {
	this.Stop();
	//蹇呴』鏄暣鏁帮紝鍚﹀垯鍙兘姝诲惊鐜�
	iLeftT = Math.round(iLeftT); iTopT = Math.round(iTopT);
	//濡傛灉娌℃湁璁剧疆缂撳姩
	if(!this.Ease){ this.SetPos(iLeftT, iTopT); return; }
	//鑾峰彇缂撳姩鍙傛暟
	var iLeftN = this.Bar.offsetLeft, iLeftS = this.GetStep(iLeftT, iLeftN)
	, iTopN = this.Bar.offsetTop, iTopS = this.GetStep(iTopT, iTopN);
	//濡傛灉鍙傛暟鏈夊€�
	if(this._horizontal ? iLeftS : iTopS){
		//璁剧疆浣嶇疆
		this.SetPos(iLeftN + iLeftS, iTopN + iTopS);
		//濡傛灉娌℃湁鍒版瀬闄愬€煎垯缁х画缂撳姩
		if(this._IsMid){ this._timer = setTimeout(Bind(this, this.EasePos, iLeftT, iTopT), this.RunTime); }
	}
  },
  //鑾峰彇姝ラ暱
  GetStep: function(iTarget, iNow) {
    var iStep = (iTarget - iNow) / this.EaseStep;
    if (iStep == 0) return 0;
    if (Math.abs(iStep) < 1) return (iStep > 0 ? 1 : -1);
    return iStep;
  },
  //璁剧疆婊戝潡浣嶇疆
  SetPos: function(iLeft, iTop) {
	this.Stop();
	this._drag.SetPos(iLeft, iTop);
  }
};


//鎷栨斁绋嬪簭
var Drag = Class.create();
Drag.prototype = {
  //鎷栨斁瀵硅薄
  initialize: function(drag, options) {
	this.Drag = $(drag);//鎷栨斁瀵硅薄
	this._x = this._y = 0;//璁板綍榧犳爣鐩稿鎷栨斁瀵硅薄鐨勪綅缃�
	this._marginLeft = this._marginTop = 0;//璁板綍margin
	//浜嬩欢瀵硅薄(鐢ㄤ簬缁戝畾绉婚櫎浜嬩欢)
	this._fM = BindAsEventListener(this, this.Move);
	this._fS = Bind(this, this.Stop);
	
	this.SetOptions(options);
	
	this.Limit = !!this.options.Limit;
	this.mxLeft = parseInt(this.options.mxLeft);
	this.mxRight = parseInt(this.options.mxRight);
	this.mxTop = parseInt(this.options.mxTop);
	this.mxBottom = parseInt(this.options.mxBottom);
	
	this.LockX = !!this.options.LockX;
	this.LockY = !!this.options.LockY;
	this.Lock = !!this.options.Lock;
	
	this.onStart = this.options.onStart;
	this.onMove = this.options.onMove;
	this.onStop = this.options.onStop;
	
	this._Handle = $(this.options.Handle) || this.Drag;
	this._mxContainer = $(this.options.mxContainer) || null;
	
	this.Drag.style.position = "absolute";
	//閫忔槑
	if(isIE && !!this.options.Transparent){
		//濉厖鎷栨斁瀵硅薄
		with(this._Handle.appendChild(document.createElement("div")).style){
			width = height = "100%"; backgroundColor = "#fff"; filter = "alpha(opacity:0)"; fontSize = 0;
		}
	}
	//淇鑼冨洿
	this.Repair();
	addEventHandler(this._Handle, "mousedown", BindAsEventListener(this, this.Start));
  },
  //璁剧疆榛樿灞炴€�
  SetOptions: function(options) {
	this.options = {//榛樿鍊�
		Handle:			"",//璁剧疆瑙﹀彂瀵硅薄锛堜笉璁剧疆鍒欎娇鐢ㄦ嫋鏀惧璞★級
		Limit:			false,//鏄惁璁剧疆鑼冨洿闄愬埗(涓簍rue鏃朵笅闈㈠弬鏁版湁鐢�,鍙互鏄礋鏁�)
		mxLeft:			0,//宸﹁竟闄愬埗
		mxRight:		9999,//鍙宠竟闄愬埗
		mxTop:			0,//涓婅竟闄愬埗
		mxBottom:		9999,//涓嬭竟闄愬埗
		mxContainer:	"",//鎸囧畾闄愬埗鍦ㄥ鍣ㄥ唴
		LockX:			false,//鏄惁閿佸畾姘村钩鏂瑰悜鎷栨斁
		LockY:			false,//鏄惁閿佸畾鍨傜洿鏂瑰悜鎷栨斁
		Lock:			false,//鏄惁閿佸畾
		Transparent:	false,//鏄惁閫忔槑
		onStart:		function(){},//寮€濮嬬Щ鍔ㄦ椂鎵ц
		onMove:			function(){},//绉诲姩鏃舵墽琛�
		onStop:			function(){}//缁撴潫绉诲姩鏃舵墽琛�
	};
	Extend(this.options, options || {});
  },
  //鍑嗗鎷栧姩
  Start: function(oEvent) {
	if(this.Lock){ return; }
	this.Repair();
	//璁板綍榧犳爣鐩稿鎷栨斁瀵硅薄鐨勪綅缃�
	this._x = oEvent.clientX - this.Drag.offsetLeft;
	this._y = oEvent.clientY - this.Drag.offsetTop;
	//璁板綍margin
	this._marginLeft = parseInt(CurrentStyle(this.Drag).marginLeft) || 0;
	this._marginTop = parseInt(CurrentStyle(this.Drag).marginTop) || 0;
	//mousemove鏃剁Щ鍔� mouseup鏃跺仠姝�
	addEventHandler(document, "mousemove", this._fM);
	addEventHandler(document, "mouseup", this._fS);
	if(isIE){
		//鐒︾偣涓㈠け
		addEventHandler(this._Handle, "losecapture", this._fS);
		//璁剧疆榧犳爣鎹曡幏
		this._Handle.setCapture();
	}else{
		//鐒︾偣涓㈠け
		addEventHandler(window, "blur", this._fS);
		//闃绘榛樿鍔ㄤ綔
		oEvent.preventDefault();
	};
	//闄勫姞绋嬪簭
	this.onStart();
  },
  //淇鑼冨洿
  Repair: function() {
	if(this.Limit){
		//淇閿欒鑼冨洿鍙傛暟
		this.mxRight = Math.max(this.mxRight, this.mxLeft + this.Drag.offsetWidth);
		this.mxBottom = Math.max(this.mxBottom, this.mxTop + this.Drag.offsetHeight);
		//濡傛灉鏈夊鍣ㄥ繀椤昏缃畃osition涓簉elative鏉ョ浉瀵瑰畾浣嶏紝骞跺湪鑾峰彇offset涔嬪墠璁剧疆
		!this._mxContainer || CurrentStyle(this._mxContainer).position == "relative" || CurrentStyle(this._mxContainer).position == "absolute" || (this._mxContainer.style.position = "relative");
	}
  },
  //鎷栧姩
  Move: function(oEvent) {
	//鍒ゆ柇鏄惁閿佸畾
	if(this.Lock){ this.Stop(); return; };
	//娓呴櫎閫夋嫨
	window.getSelection ? window.getSelection().removeAllRanges() : document.selection.empty();
	//璁剧疆绉诲姩鍙傛暟
	this.SetPos(oEvent.clientX - this._x, oEvent.clientY - this._y);
  },
  //璁剧疆浣嶇疆
  SetPos: function(iLeft, iTop) {
	//璁剧疆鑼冨洿闄愬埗
	if(this.Limit){
		//璁剧疆鑼冨洿鍙傛暟
		var mxLeft = this.mxLeft, mxRight = this.mxRight, mxTop = this.mxTop, mxBottom = this.mxBottom;
		//濡傛灉璁剧疆浜嗗鍣紝鍐嶄慨姝ｈ寖鍥村弬鏁�
		if(!!this._mxContainer){
			mxLeft = Math.max(mxLeft, 0);
			mxTop = Math.max(mxTop, 0);
			mxRight = Math.min(mxRight, this._mxContainer.clientWidth);
			mxBottom = Math.min(mxBottom, this._mxContainer.clientHeight);
		};
		//淇绉诲姩鍙傛暟
		iLeft = Math.max(Math.min(iLeft, mxRight - this.Drag.offsetWidth), mxLeft);
		iTop = Math.max(Math.min(iTop, mxBottom - this.Drag.offsetHeight), mxTop);
	}
	//璁剧疆浣嶇疆锛屽苟淇margin
	if(!this.LockX){ this.Drag.style.left = iLeft - this._marginLeft + "px"; }
	if(!this.LockY){ this.Drag.style.top = iTop - this._marginTop + "px"; }
	//闄勫姞绋嬪簭
	this.onMove();
  },
  //鍋滄鎷栧姩
  Stop: function() {
	//绉婚櫎浜嬩欢
	removeEventHandler(document, "mousemove", this._fM);
	removeEventHandler(document, "mouseup", this._fS);
	if(isIE){
		removeEventHandler(this._Handle, "losecapture", this._fS);
		this._Handle.releaseCapture();
	}else{
		removeEventHandler(window, "blur", this._fS);
	};
	//闄勫姞绋嬪簭
	this.onStop();
  }
};