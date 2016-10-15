<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<link href="css/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
</script>
<script type="text/javascript">
	$(function(){
		$("#supplier").change(function(){
			var supplierUuid = $(this).val();
			$.post("order_ajaxGetGtmAndGm.action",{"supplierUuid":supplierUuid},function(data){
				$(".goodsType").empty();
				$(".goods").empty();
				//修改商品类别select
				var gtmList = data.gtmList;
				for(var i=0;i<gtmList.length;i++) {
					var gtm = gtmList[i];
					$op = $("<option value='"+gtm.uuid+"'>"+gtm.name+"</option>")
					$(".goodsType").append($op);
				}
				
				//修改商品select
				var gmList = data.gmList;
				for(var i=0;i<gmList.length;i++) {
					var gm = gmList[i];
					$op = $("<option value='"+gm.uuid+"'>"+gm.name+"</option>")
					$(".goods").append($op);
				}
				
				//修改数量
				$(".num").val(1);
				
				//修改单价
				var price = data.gm.inPriceView;
				$(".prices").val(price);
				
				//修改合计
				$(".total").html(price + " 元");
				
				//计算总价
				totalAll();
			})
		});
		
		//修改商品类别
		//$(".goodsType").change(function(){ //改成未来事件
		$(".goodsType").live("change",function(){
			var gtmUuid = $(this).val();
			
			/* //维护不方便格式
			var $gmSelect = $(this).parent().next().children("select");
			var $num = $(this).parent().next().next().children("input");
			var $prices = $(this).parent().next().next().next().children("input");
			var $totalPrice = $(this).parent().next().next().next().next();
			 */
			
			var $nowTr = $(this).parent().parent();
			var $gmSelect = $nowTr.children("td:eq(1)").children("select");
			var $num = $nowTr.children("td:eq(2)").children("input");
			var $prices = $nowTr.children("td:eq(3)").children("input");
			var $totalPrice = $nowTr.children("td:eq(4)");
			
			//使用过的商品的uuid都保存在class=goods的select中，二这个select中可能包含多个option选项，所以取出的数据是一个数组
			var goodsArr = $(".goods");
			var used = "";
			for(var i= 0;i<goodsArr.length;i++) {
				used = used + "'" + goodsArr[i].value + "',"
			}
			$.post("order_ajaxGetGm.action",{"gtmUuid":gtmUuid,"used":used},function(data){
				$gmSelect.empty();
				//修改商品select
				var gmList = data.gmList;
				for(var i=0;i<gmList.length;i++) {
					var gm = gmList[i];
					$op = $("<option value='"+gm.uuid+"'>"+gm.name+"</option>")
					$gmSelect.append($op);
				}
				
				//修改数量
				$num.val(1);
				
				//修改单价
				var price = data.gm.inPriceView;
				$prices.val(price);
				
				//修改合计
				$totalPrice.html(price + " 元");
				
				//计算总价
				totalAll();
			})
		});
		
		//修改商品
		//$(".goods").change(function(){ //改成未来事件
		$(".goods").live("change",function(){
			var gmUuid = $(this).val();
			
			var $nowTr = $(this).parent().parent();
			var $num = $nowTr.children("td:eq(2)").children("input");
			var $prices = $nowTr.children("td:eq(3)").children("input");
			var $totalPrice = $nowTr.children("td:eq(4)");
			
			$.post("order_ajaxGetPrice.action",{"gmUuid":gmUuid},function(data){
				var price = data.inPriceView;
				//修改数量
				$num.val(1);
				//修改单价
				$prices.val(price);
				//修改合计
				$totalPrice.html(price + " 元");
				
				//计算总价
				totalAll();
			})
		})
		
		
		//动态添加订单项
		var clickFlag = true; //控制点击添加按钮时是否发送ajax请求，用于防止网络延迟引发的bug
		$("#add").click(function(){
			//订单项完成后不可更改
			$(".supplier").attr("disabled",true);
			$(".goodsType").attr("disabled",true);
			$(".goods").attr("disabled",true);
			
			if(!clickFlag) {
				return;
			}
			clickFlag = false;
			
			//获取供应商的uuid；
			var supplierUuid = $("#supplier").val();
			
			//商品信息数据过滤(同一商品购买多个时，只需显示 一条商品信息，其他同名的商品信息需要过滤掉)
			//过滤思路：将当前页面使用过的商品uuid传递到后台，后台接收导数据后，将页面上使用过的商品过滤掉，然后再返回页面显示。
			//1，传递到后台的商品uuid不止一个，故后台需要对多个的商品信息进行过滤
			//2，多个数据如何设置成参数，后台接收多个数据使用集合或者数组，要求页面传递的参数与后台接收参数相同。
			//3，为了是参数传递复杂度降低，将所用的uuid设置成特殊格式，传递到后台，例如字符串“1,2,3,4,5”
			
			//使用过的商品的uuid都保存在class=goods的select中，二这个select中可能包含多个option选项，所以取出的数据是一个数组
			var goodsArr = $(".goods");
			var used = "";
			for(var i= 0;i<goodsArr.length;i++) {
				used = used + "'" + goodsArr[i].value + "',"
			}
			
			$.post("order_ajaxGetGtmAndGm2.action",{"supplierUuid":supplierUuid,"used":used},function(data){
				var gtmList = data.gtmList;
				var gmList = data.gmList;
				
				//加载商品类别信息
				var $tr = $('<tr bgcolor="#FFFFFF" align="center"></tr>');
				var $td1 = $('<td height="30"></td>');
				var $gtmSelect = $('<select style="width:200px" class="goodsType"></select>');
				for(var i=0;i<gtmList.length;i++) {
					var gtm = gtmList[i];
					var $op = $('<option value="'+gtm.uuid+'">'+gtm.name+'</option>');
					$gtmSelect.append($op);
				}
				$td1.append($gtmSelect);
				$tr.append($td1);
				
				//加载商品信息
				var $td2 = $('<td></td>');
				var $gmSelect = $('<select name="goodsUuids" style="width:200px" class="goods"></select>');
				for(var i=0;i<gmList.length;i++){
					var gm = gmList[i];
					var $op = $('<option value="'+gm.uuid+'">'+gm.name+'</option>');
					$gmSelect.append($op);
				}
				$td2.append($gmSelect);
				$tr.append($td2);
				
				//加载商品数量
				var $td3 = $('<td><input type="text" value="1" style="width:67px;border:1px solid black;text-align:right;padding:2px" class="num order_num" name="nums"></td>');
				$tr.append($td3);
				
				//加载商品单价
				var $td4 = $('<td><input type="text" value="100" style="width:93px;border:1px solid black;text-align:right;padding:2px" class="prices order_num" name="prices"> 元</td>');
				$tr.append($td4);
				
				var $td5 = $('<td align="right" class="total">100.00&nbsp;元</td>');
				$tr.append($td5);
				
				var $td6 = $('<td><a value="4" class="deleteBtn delete xiu"><img src="images/icon_04.gif"> 删除</a></td>');
				$tr.append($td6);
				
				//添加订单项
				$("#finalTr").before($tr);
				
				//当ajax请求的数据全部接受并处理完毕后，才可以继续通过添加按钮发送下一次ajax请求
				clickFlag = true;
				//判断添加按钮是否显示
				//当商品类别和商品都仅剩下一个时，将添加按钮隐藏
				if(gtmList.length == 1 && gmList.length == 1){
					//$("#add").hide();
					$("#add").css("display","none");
					//企业开发方案：美工设计两个样式，aa,bb aa显示  bb不显示
					//美工设计	aa里面有所有的样式，没有为display设置值
					//			bb里面只有一个样式，display设置为none
					//$("#add").toggleClass("bb");//切换样式
				}
				
				//计算总价
				totalAll();
			})
		})
		
		//删除订单项(行)
		//为删除按钮绑定未来事件
		$(".deleteBtn").live("click",function(){
			if($(".deleteBtn").length == 1) {
				return;
			}
			//删除当前行
			$nowTr = $(this).parent().parent();
			$nowTr.remove();
			
			//显示添加按钮
			$("#add").css("display","inline");
			
			//计算总价
			totalAll();
		});
		
		//修改数量
		$(".num").live("keyup",function(){
			//先把非数字的都替换掉，除了数字和. 
			$(this).val($(this).val().replace(/[^\d]/g,""));
			//计算小计
			totalPrice($(this));
			//计算总价
			totalAll();
		});
		
		//修改单价
		$(".prices").live("keyup",function(){
			//先把非数字的都替换掉，除了数字和. 
			$(this).val($(this).val().replace(/[^\d.]/g,""));
	        //必须保证第一个为数字而不是. 
	        $(this).val($(this).val().replace(/^\./g,"0."));
	        //保证只有出现一个.而没有多个. 
	        $(this).val($(this).val().replace(/\.{2,}/g,"."));
	        //保证.只出现一次，而不能出现两次以上
	        $(this).val($(this).val().replace(".","$#$").replace(/\./g,"").replace("$#$",".")); 
	        //计算合计
			totalPrice($(this));
			//计算总价
			totalAll();
		});
		
		//格式化数值数据（使得合计保留两位小数）
		function intToFloat(val) {
			return new Number(val).toFixed(2);
		}
		
		//根据商品数量和单价计算小计
		function totalPrice(obj) {
			var $nowTr = obj.parent().parent();
			var $num = $nowTr.children("td:eq(2)").children("input");
			var $prices = $nowTr.children("td:eq(3)").children("input");
			var $totalPrice = $nowTr.children("td:eq(4)");
			var total = $num.val() * $prices.val();
			//显示数据必须保留两位小数
			$totalPrice.html(intToFloat(total) + " 元");
		}
		
		//求总价
		function totalAll() {
			var numArr = $(".num");
			var pricesArr = $(".prices");
			
			var sum = 0;
			for(var i = 0;i<numArr.length;i++) {
				var total = numArr[i].value * pricesArr[i].value;
				sum += total;
			}
			
			$(".all").html(intToFloat(sum) + " 元");
		}
		
		//提交表单（保存数据）
		$("#submitOrder").click(function(){
			//由于disabled为true的表单项不参与表单提交，所以此处需要恢复表单项的distabled为false、
			$(".supplier").attr("disabled",false);
			$(".goods").attr("disabled",false);
			$("form:first").submit();
		})
	})
	
</script>
<div class="content-right">
	<div class="content-r-pic_w">
		<div style="margin:8px auto auto 12px;margin-top:6px">
			<span class="page_title">订单管理</span>
		</div>
	</div>
	<div class="content-text">
		<s:form action="order_buySave" method="post">
			<div class="square-o-top">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					style="font-size:14px; font-weight:bold; font-family:"黑体";">
					<tr>
						<td height="24">&nbsp;</td>
					</tr>
					<tr>
						<td width="68px" height="30">供应商：</td>
						<td width="648px">
							<s:select name="om.sm.uuid" id="supplier" list="supplierList" listKey="uuid" listValue="name" cssStyle="width:190px"/>
						</td>
						<td height="30">
							<a id="add"><img src="images/can_b_02.gif" border="0" /> </a>
						</td>
					</tr>
				</table>
			</div>
			<!--"square-o-top"end-->
			<div class="square-order">
				<table id="order" width="100%" border="1" cellpadding="0" cellspacing="0">
					<tr align="center"
						style="background:url(images/table_bg.gif) repeat-x;">
						<td width="25%" height="30">商品类别</td>
						<td width="25%">商品名称</td>
						<td width="10%">采购数量</td>
						<td width="15%">单价</td>
						<td width="15%">合计</td>
						<td width="10%">操作</td>
					</tr>
					<tr align="center" bgcolor="#FFFFFF">
						<td height="30">
							<s:select cssClass="goodsType" list="gtmList" listKey="uuid" listValue="name" cssStyle="width:200px"/>
						</td>
						<td>
							<s:select name="goodsUuids" cssClass="goods" list="gmList" listKey="uuid" listValue="name" cssStyle="width:200px"/>
						</td>
						<td><input name="nums" class="num order_num" style="width:67px;border:1px solid black;text-align:right;padding:2px" type="text" value="1"/></td>
						<td><input name="prices" class="prices order_num" style="width:93px;border:1px solid black;text-align:right;padding:2px" type="text" value="${gmList[0].inPrice}"/> 元</td>
						<td class="total" align="right">${gmList[0].inPrice}&nbsp;元</td>
						<td>
							<a class="deleteBtn delete xiu" value="4"><img src="images/icon_04.gif" /> 删除</a>
						</td>
					</tr>
					<tr id="finalTr" align="center"
						style="background:url(images/table_bg.gif) repeat-x;">
						<td height="30" colspan="4" align="right">总&nbsp;计:&nbsp;</td>
						<td class="all" width="16%" align="right">${gmList[0].inPrice}&nbsp;元</td>
						<td>&nbsp;</td>
					</tr>
				</table>
				<div class="order-botton">
				<div style="margin:1px auto auto 1px;">
					<table width="100%"  border="0" cellpadding="0" cellspacing="0">
					  <tr>
					    <td>
					    	<a href="javascript:void(0)" id="submitOrder"><img src="images/order_tuo.gif" border="0" /></a>
					    </td>
					    <td>&nbsp;</td>
					    <td><a href="#"><img src="images/order_tuo.gif" border="0" /></a></td>
					    <td>&nbsp;</td>
					    <td><a href="#"><img src="images/order_tuo.gif" border="0" /></a></td>
					  </tr>
					</table>
				</div>
			</div>
			</div>
		</s:form>
	</div>
	
	<div class="content-bbg"></div>
</div>














<script>
/* 
	function intToFloat(val){
		return new Number(val).toFixed(2);
	}
	//修改供应商
	$(document).ready(function() {
		$("#supplier").change(function(){
				//修改产品类别select
				$(".goodsType").empty();
				for(var i = 0;i<4;i++){
					var $option = $("<option value='1'>商品类别"+i+"</option>");	//创建option对象(jQuery格式)
					$(".goodsType").append($option);				//将option对象添加到select组件中
				}
				//修改商品select
				$(".goods").empty();
				for(var i = 0;i<5;i++){
					var $option = $("<option value='1'>商品名"+i+"</option>");	//创建option对象(jQuery格式)
					$(".goods").append($option);				//将option对象添加到select组件中
				}
				//修改商品数量
				$(".order_num").val("1");
				//修改商品单价
				$("[name='prices']").val(intToFloat(300));
				//修改商品合计
				$(".total").text(intToFloat(300)+" 元");
				//修改商品总计
				total();
		});
		//修改商品类别
		$(".goodsType").live("change",function(){
			var $goodsSelectObj = $($(this).parent().next().children()[0]);
			var $num = $($(this).parent().next().next().children()[0]);
			var $price = $($(this).parent().next().next().next().children()[0]);
			var $total = $(this).parent().next().next().next().next();
			//发送的请求要将当前已经使用的过滤掉，否则每次出现的集合会重复
			var jsonParam = {"gm.goodsType.uuid":$(this).val()};
			var goodsUuids = "";
			//取出所有select
			var goodsObjs = $(".goods");
			for(i = 0;i<goodsObjs.length;i++){
				goodsUuids = goodsUuids + $(goodsObjs[i]).val();
				if(i != goodsObjs.length -1){
					goodsUuids = goodsUuids + ",";
				}
			}
			jsonParam["goodsUuids"]= goodsUuids;
				
			$goodsSelectObj.empty();
			for(var i = 0;i<5;i++){
				var $option = $("<option value='222'>新商品名"+1+"</option>");	//创建option对象
				$goodsSelectObj.append($option);				//将option对象添加到select组件中
			}
			//修改商品数量
			$num.val("1");
			//修改商品单价
			$price.val(intToFloat(400));
			//修改商品合计
			$total.text(intToFloat(400)+" 元");
			//修改商品总计
			total();
		});
		//修改商品
		$(".goods").live("change",function(){
			var $num = $($(this).parent().next().children()[0]);
			var $price = $($(this).parent().next().next().children()[0]);
			var $total = $(this).parent().next().next().next();
			
			//修改商品数量
			$num.val("1");
			//修改商品单价
			$price.val(intToFloat(111));
			//修改商品合计
			$total.text(intToFloat(111)+" 元");
			//修改商品总计
			total();
		});
		//添加新商品
		$("#add").click(function(){
			//设置不能修改供应商
			$("#supplier").attr("disabled","disabled");
			//设置已存在的所有select全部不可点击
			$(".goodsType").attr("disabled","disabled");
			$(".goods").attr("disabled","disabled");
			
			//发送ajax提交，将供应商id与当前已经使用的类别对应商品2个id传递到后台，并将其过滤，过滤完毕的数据传递回页面
			var goodsTypeObjs = $(".goodsType");
			var goodsObjs = $(".goods");
			var jsonParam = {"gm.goodsType.supplier.uuid":$("#supplier").val()};
			var hasUuids = "";
			for(i = 0;i<goodsTypeObjs.length;i++){
				hasUuids = hasUuids + $(goodsTypeObjs[i]).val();
				hasUuids = hasUuids + ":";
				hasUuids = hasUuids + $(goodsObjs[i]).val();
				if(i != goodsTypeObjs.length -1){
					hasUuids = hasUuids + ",";
				}
			}
			jsonParam["hasUuids"]= hasUuids;
				
				//动态添加一个tr行
				//创建tr组件
				var $tr = $("<tr></tr>");
				
				//创建td组件，生成商品类别select
				var typeSelectStr = "<select class='goodsType' style='width:200px'>";
				for(i = 0;i<4;i++){
					typeSelectStr+="<option value='";
					typeSelectStr+=111;
					typeSelectStr+="'>";
					typeSelectStr+="类别名称"+i;
					typeSelectStr+="</option>";
				}
				typeSelectStr += "</select>";
				var $td1 = $("<td height='30'>"+typeSelectStr+"</td>");
				$tr.append($td1);
				
				//创建td组件，生成商品select
				var goodsSelectStr = "<select name='gmUuids' class='goods' style='width:200px'>";
				for(i = 0;i<4;i++){
					goodsSelectStr+="<option value='";
					goodsSelectStr+=123;
					goodsSelectStr+="'>";
					goodsSelectStr+="新商品名"+i;
					goodsSelectStr+="</option>";
				}
				goodsSelectStr += "</select>";
				var $td2 = $("<td>"+goodsSelectStr+"</td>");
				$tr.append($td2);
				
				//创建td组件，生成商品数量input，默认值1
				var $td3 = $("<td>&nbsp;<input name='nums' type='text' value='1' class='num order_num' style='width:67px;border:1px solid black;text-align:right;padding:2px' /></td>");
				$tr.append($td3);
				
				var $td4 = $("<td style='padding-left:4px'><input name='prices' type='text' value='"+222+"' class='prices order_num' style='width:93px;border:1px solid black;text-align:right;padding:2px' /> 元</td>");
				$tr.append($td4);
				
				var $td5 = $("<td class='total' align='right'>"+222+" 元</td>");
				$tr.append($td5);
				
				var $td6 = $("<td>&nbsp;&nbsp;<a class='deleteBtn delete xiu' value='"+112+"'><img src='images/icon_04.gif'/> 删除</a></td>");
				$tr.append($td6);
				
				//在最后一个tr对象前添加该tr组件
				$("#finalTr").before($tr);
				//注意：新添加的元素是否具有动态事件激活能力
				
				//获取后台数据是否还有下一个可用的商品，判断添加按钮是否显示
				if("Y" == "N"){
					//将添加按钮设置为不可显示
					$("#add").css("display","none");
				}
				total();
		});
		//修改商品数量，事件绑定为点击任意键盘数字按钮
		$(".num").live("keyup",function(event){
			//事件激活方式为任意键盘数字按钮，避免用户输入非法数字
			if(event.keyCode>=48 && event.keyCode<=57 || event.keyCode>=96 && event.keyCode<=105 || event.keyCode == 8){
				//获取当前输入数量值
				var num = $(this).val();
				//获取当前价格
				var price = $($(this).parent().next().children()[0]).val();
				$(this).parent().next().next().text(intToFloat(num*price)+" 元");
				total();
				return true;
			}
			return false;
		});
		//修改商品价格，事件绑定为点击任意键盘数字按钮
		$(".prices").live("keyup",function(event){
			//先把非数字的都替换掉，除了数字和. 
			$(this).val($(this).val().replace(/[^\d.]/g,""));
	        //必须保证第一个为数字而不是. 
	        $(this).val($(this).val().replace(/^\./g,"0."));
	        //保证只有出现一个.而没有多个. 
	        $(this).val($(this).val().replace(/\.{2,}/g,"."));
	        //保证.只出现一次，而不能出现两次以上
	        $(this).val($(this).val().replace(".","$#$").replace(/\./g,"").replace("$#$",".")); 

	        //获取当前输入价格
			var price = $(this).val();
			//获取当前数量
			var num = $($(this).parent().prev().children()[0]).val();
			//求合计
			$(this).parent().next().text(intToFloat(num*price)+" 元");
			//求总计
			total();
		});
		
		$(".deleteBtn").live("click",function(){
			if($(".deleteBtn").size()>1){
				//获取当前所在行，并将其删除
				$(this).parent().parent().remove();
				//将添加按钮设置为可显示状态
				$("#add").css("display","inline");
			}
			//将所有项设置为不可修改
			$(".goodsType").attr("disabled","disabled");
			$(".goods").attr("disabled","disabled");
			total();
		});
		
		//求总和的方法
		function total(){
			var nums = $(".num");
			var prices = $(".prices");
			var total = 0;
			for(var i = 0;i<nums.length;i++){
				var num = $(nums[i]).val();
				var price=  $(prices[i]).val();
				total = total + num * price ;
			}
			$(".all").text(intToFloat(total)+" 元");
		}
		
		//提交按钮（设置不可修改的
		$("#submitOrder").click(function(){
			$("#supplier").removeAttr("disabled");
			$(".goods").removeAttr("disabled");
			//提交页面中的第一个form对象
			$("form:first").submit();
		});
	}); */
</script>