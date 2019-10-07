<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<style type="text/css">
*{margin:0;padding:0;list-style-type:none;}
a,img{border:0;}
/* body{color:#333;font: 12px/1.5 tahoma, arial, \5b8b\4f53, sans-serif;} */
a{color:#333;text-decoration:none;}
a:hover{color:#3366cc;text-decoration:underline;}
.scrolltitle{height:24px;font-size:14px;width:980px;border-bottom:solid 1px #ddd;margin:20px auto 15px auto;}
.scrolllist{width:980px;height:204px;margin:0 auto;}
.imgshow{width:980px;margin:0 auto;}
.imgshow .imgzoom{margin:0 auto;width:620px;border:solid 1px #000;}
/* container */
.container{width:620px;height:164px;margin:20px auto;overflow:hidden;position:relative;}
.container ul{width:10000px;position:absolute;left:0px;top:0px;padding:0;margin:0;}
.container ul li{width:158px;padding:0 10px;float:left;text-align:center;}
.container ul li p{height:40px;line-height:20px;overflow:hidden;}
.container ul li img{border:1px solid #ddd;padding:3px;}
.container ul li div.current img{border:solid 1px #ff6600;padding:3px;}
/* hScrollPane */
.hScrollPane_dragbar,.hScrollPane_draghandle,.hScrollPane_leftarrow,.hScrollPane_rightarrow{background:url(/images/dragbar.gif);}
.hScrollPane_dragbar{position:absolute;left:0px;bottom:0px;height:16px;margin:0 auto;background-position:left -32px;}
.hScrollPane_draghandle{height:14px;width:30px;border:1px solid #d5d3d3;overflow:hidden;position:absolute;top:0px;left:0px;cursor:default;background-position:center -48px;background-repeat:no-repeat;background-color:#e5e5e5;-moz-border-radius:2px;-khtml-border-radius:2px;-webkit-border-radius:2px;border-radius:2px;}
.hScrollPane_leftarrow,.hScrollPane_rightarrow{display:inline-block;height:16px;width:17px;overflow:hidden;position:absolute;bottom:0;}
.hScrollPane_leftarrow{left:0;}
.hScrollPane_leftarrow:hover{background-position:left -64px;}
.hScrollPane_rightarrow{right:0;background-position:left -16px;}
.hScrollPane_rightarrow:hover{background-position:left -80px;}
.draghandlealter{background-position:center -96px;background-color:#efefef;}
</style>
<div class="mainTitle">
<span><a href="/login/index.do" style="text-decoration: underline;">首页</a>&emsp;>&emsp;
<a  style="text-decoration: underline;" onclick="closeSencondDiv();">${requestScope.upName}</a>&emsp;>&emsp;
查看图片
</span>
</div>
<div style="text-align: center;height: 50px; color: tan;font-weight: bold;">
${requestScope.housing.community.name} - ${requestScope.housing.floor}
<!-- <a style="float: right;margin-right: 30px;" onclick="closeSencondDiv();">返回</a> -->
</div>
<div class="imgshow">
	
	<div class="imgzoom">
		<img src="${requestScope.imageUrl}" alt="" style="width: 620px;"/>
		<div class="loading"></div>
	</div>
	<c:if test="${requestScope.list.size()==0}">
	<script type="text/javascript">
	pop_up_box.showMsg("没有图片!",function(){
	closeSencondDiv();
	});
	</script>
	</c:if>
	<div class="container thumblist">
		<ul>
		<c:forEach items="${requestScope.list}" var="img" >
			<li>
				<div class="current"><a href="${img.imgUrl}"><img src="${img.imgUrl}" alt="" style="height: 150px;max-width: 170px;" /></a></div>
			</li>
		</c:forEach>
		</ul>
	</div>
	<div style="clear:both;"></div>
</div>
	<div style="clear:both;"></div>
<script type="text/javascript" src="/js_scroll/jquery.mousewheel.js"></script>
<script type="text/javascript" src="/js_scroll/hScrollPane.js"></script>
<script type="text/javascript">
$(".container").hScrollPane({
	mover:"ul",
	moverW:function(){return $(".container li").length*178;}(),
	showArrow:true,
	handleCssAlter:"draghandlealter",
	mousewheel:{moveLength:207}
});

$(function(){

	var img=new Image();
	var imgshowobj=$(".imgshow");
	var imgzoom=imgshowobj.find(".imgzoom");

	imgshowobj.find(".thumblist").find("div a").live("click",function(){
		
		imgzoom.find(".loading").show();
		img.onload=function(){
			imgzoom.find("img").attr("src",img.src);
			imgzoom.find(".loading").hide();
		}
		img.src=$(this).attr("href");
		$(".thumblist li div a").parent().removeClass("current");
		$(this).parent().addClass("current");
		return false;
	});
	
});	
</script>