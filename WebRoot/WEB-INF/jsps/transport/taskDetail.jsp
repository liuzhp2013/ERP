<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<link href="css/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script type="text/javascript">
	$(function() {
		$("#task").click(function() {
			$("form:first").submit();
		});
	});
</script>
<div class="content-right">
	<div class="content-r-pic_w">
		<div style="margin:8px auto auto 12px;margin-top:6px">
			<span class="page_title">任务指派</span>
		</div>
	</div>
	<div class="content-text">
			<div class="square-o-top">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					style="font-size:14px; font-weight:bold; font-family:"黑体";">
					<tr>
						<td height="30">企业名称:</td>
						<td class="order_show_msg">${om.sm.name}</td>
						<td height="30">订单类别:</td>
						<td class="order_show_msg">${om.orderTypeView}</td>
						<td>提货方式:</td>
						<td class="order_show_msg">${om.sm.needsView}</td>
						<td>订 单 号:</td>
						<td class="order_show_msg" colspan="2">${om.orderNum}</td>
					</tr>
					<tr>
						<td>联&nbsp;系&nbsp;人:</td>
						<td class="order_show_msg">${om.sm.contact}</td>
						<td>联系方式:</td>
						<td class="order_show_msg">${om.sm.tele}</td>
						<td>商品总量:</td>
						<td class="order_show_msg">${om.totalNum}</td>
						<td>地&nbsp;&nbsp;&nbsp;&nbsp;址:</td>
						<td class="order_show_msg">${om.sm.address}</td>
					</tr>
				</table>
			</div>
			<!--"square-o-top"end-->
			<div class="square-order">
				<center style="text-decoration:underline;font-size:16px; font-weight:bold; font-family:"黑体";">&nbsp;&nbsp;&nbsp;&nbsp;单&nbsp;&nbsp;据&nbsp;&nbsp;明&nbsp;&nbsp;细&nbsp;&nbsp;&nbsp;&nbsp;</center>
				<br/>
				<table width="100%" border="1" cellpadding="0" cellspacing="0">
					<tr align="center"
						style="background:url(images/table_bg.gif) repeat-x;">
						<td width="20%" height="30">商品类别</td>
						<td width="50%">商品名称</td>
						<td width="30%">数量</td>
					</tr>
					<s:iterator value="om.odms">
						<tr align="center" bgcolor="#FFFFFF">
							<td height="30">${gm.gtm.name}</td>
							<td>${gm.name}</td>
							<td>${num}</td>
						</tr>
					</s:iterator>
				</table>
				<br/>
				<s:form action="transport_assignTask" method="post">
					<s:hidden name="om.uuid"/>
					<table width="100%">
						<tr>
							<td width="50%" align="right">
								<s:select name="om.completer.uuid" list="empList" listKey="uuid" listValue="name" cssStyle="width:100px;font-size:20px"></s:select>
							</td>
							<td width="50%" align="left">
								<a id="task" href="javascript:void(0)" style="color:#0f0;font-size:20px;padding-top:2px;font-weight:bold;text-decoration:none;width:82px;height:28px;display:block;background:url(images/btn_bg.jpg)">
									&nbsp;派&nbsp;&nbsp;单
								</a>
							</td>
						</tr>
					</table>
				</s:form>
			</div>
	</div>
	<div class="content-bbg"></div>
</div>
