<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<link href="css/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script type="text/javascript">
	/* 
		$(function() {
			$("#commit").click(function() {
				$("form:first").submit();
			});
		});
	*/
	
	//页面加载完毕后执行该函数
	$(function(){
		//给commit链接绑定单击事件
		$("#commit").click(function() {
			//当单击commit链接时，提交表单；
			$("form:first").submit();
		});
	});
</script>
	<div class="content-right">
	<div class="content-r-pic_w">
		<div style="margin:8px auto auto 12px;margin-top:6px">
			<span class="page_title">部门管理</span>
		</div>
	</div>
	<div class="content-text">
		<div class="square-order">
			<s:form action="dep_save.action" method="post">
			<s:hidden name="dm.uuid"/>
  			<div style="border:1px solid #cecece;">
				<table width="100%"  border="0" cellpadding="0" cellspacing="0">
				  <tr bgcolor="#FFFFFF">
				    <td>&nbsp;</td>
				  </tr>
				</table>
				<table width="100%"  border="0" cellpadding="0" cellspacing="0">
				    <tr  bgcolor="#FFFFFF">
				      <td width="18%" height="30" align="center">部门名称</td>
				      <td width="32%">
				      	<s:textfield name="dm.name" size="25"/>
				      </td>
				      <td width="18%" align="center">电话</td>
				      <td width="32%">
				      	<s:textfield name="dm.tele" size="25"/>
				      </td>
				    </tr>
				    <tr  bgcolor="#FFFFFF">
				      <td colspan="4">&nbsp;</td>
				    </tr>
				</table>
				
			</div>
			<div class="order-botton">
				<div style="margin:1px auto auto 1px;">
					<table width="100%"  border="0" cellpadding="0" cellspacing="0">
					  <tr>
					    <td>
					    	<!-- 当需要给链接绑定事件时，必须先把链接自带的事件机制注销掉 ：通过javascript:void(0);实现 -->
					    	<a href="javascript:void(0);" id="commit">
					    		<img src="images/order_tuo.gif" border="0" />
					    	</a>
					    </td>
					    <td>&nbsp;</td>
					    <td><a href="#"><img src="images/order_tuo.gif" border="0" /></a></td>
					    <td>&nbsp;</td>
					    <td><a href="#"><img src="images/order_tuo.gif" border="0" /></a></td>
					  </tr>
					</table>
				</div>
			</div>
			</s:form>
		</div><!--"square-order"end-->
	</div><!--"content-text"end-->
	<div class="content-bbg"><img src="images/content_bbg.jpg" /></div>
</div>
