<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<link href="css/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="js/Calendar.js"></script>
<script type="text/javascript">
	$(function() {
		//全选/取消全选
		// 实现思路：确保全选按钮自身的状态与备选框的状态一致
		$("#all").click(function() {
			//第一步：获取当前组件（即$("#all")组件）的状态
			var flag = $(this).attr("checked");
			//第二步：将所有checkbox组件状态设置为上述组件的对应状态
			$("[name=resUuids]").attr("checked", flag == "checked");
		})

		//将所有备选匡的综合状态与全选/取消全选按钮的状态进行绑定
		//实现思路：所有备选匡的综合状态决定全选反选按钮的状态
		$("[name=resUuids]").click(function() {
			checkSelect();
		})

		//反选
		//实现思路： 确保点击反选按钮时，每一个备选框的状态变为该备选匡原始状态的反状态
		$("#reverse").click(function() {
			//将所有checkbox组件状态设置原始组件($("[name=resUuids]"))状态的反状态

			//第一步：遍历集合组件(备选匡集合)
			$("[name=resUuids]").each(function() {
				//第二步：获取当前遍历结果组件的状态
				var flag = $(this).attr("checked");
				//第三步：将当前遍历结果的状态修改为对应的反状态
				$(this).attr("checked", !(flag == "checked"));
			})
			
			checkSelect();
		})
		
		function checkSelect() {
			//将所有备选匡的综合状态的默认值设置为true
			var allFlag = true;
			$("[name=resUuids]").each(function() {
				//记录当前遍历结果组件的状态
				var flag = $(this).attr("checked") == "checked";
				//将当前遍历结果的状态与默认的综合状态进行与运算，只有当所有备选匡的状态均为true，综合状态才为true
				allFlag = allFlag && flag;
			})
			
			//绑定全选按钮与所有备选匡综合状态之间的关系
			$("#all").attr("checked",allFlag);
		}
	})
</script>
<div class="content-right">
	<div class="content-r-pic_w">
		<div style="margin:8px auto auto 12px;margin-top:6px">
			<span class="page_title">角色管理</span>
		</div>
	</div>
	<div class="content-text">
		<div class="square-order">
			<s:form action="role_save" method="post">
				<s:hidden name="rm.uuid" />
				<div style="border:1px solid #cecece;">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr bgcolor="#FFFFFF">
							<td>&nbsp;</td>
						</tr>
					</table>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr bgcolor="#FFFFFF">
							<td width="18%" height="30" align="center">角色名称</td>
							<td width="32%"><s:textfield name="rm.name" size="25" /></td>
							<td width="18%" align="center">角色编码</td>
							<td width="32%"><s:textfield name="rm.code" size="25" /></td>
						</tr>
						<tr bgcolor="#FFFFFF">
							<td colspan="4">&nbsp;</td>
						</tr>
						<tr bgcolor="#FFFFFF">
							<td width="18%" height="30" align="center">资源名称</td>
							<td width="82%" colspan="3"><input type="checkbox" id="all">全选&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="checkbox" id="reverse">反选</td>
						</tr>
						<tr bgcolor="#FFFFFF">
							<td width="18%" height="30" align="center">&nbsp;</td>
							<td width="82%" colspan="3"><s:checkboxlist name="resUuids"
									list="resList" listKey="uuid" listValue="name" /></td>
						</tr>
						<tr bgcolor="#FFFFFF">
							<td width="18%" height="30" align="center">菜单名称</td>
							<td width="82%" colspan="3"><input type="checkbox" id="all">全选&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="checkbox" id="reverse">反选</td>
						</tr>
						<tr bgcolor="#FFFFFF">
							<td width="18%" height="30" align="center">&nbsp;</td>
							<td width="82%" colspan="3">
								<s:checkboxlist name="menuUuids"
									list="menuList" listKey="uuid" listValue="name" />
							</td>
						</tr>
						<tr bgcolor="#FFFFFF">
							<td colspan="4">&nbsp;</td>
						</tr>
					</table>
				</div>
				<div class="order-botton">
					<div style="margin:1px auto auto 1px;">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td><a href="javascript:document.forms[0].submit()"><img
										src="images/order_tuo.gif" border="0" /></a></td>
								<td>&nbsp;</td>
								<td><a href="#"><img src="images/order_tuo.gif"
										border="0" /></a></td>
								<td>&nbsp;</td>
								<td><a href="#"><img src="images/order_tuo.gif"
										border="0" /></a></td>
							</tr>
						</table>
					</div>
				</div>
			</s:form>
		</div>
		<!--"square-order"end-->
	</div>
	<!--"content-text"end-->
	<div class="content-bbg">
		<img src="images/content_bbg.jpg" />
	</div>
</div>
