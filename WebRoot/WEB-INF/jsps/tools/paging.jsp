<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td width="51%">&nbsp;</td>
		<td width="13%">共${totalCount}条记录  
		<td width="6%">
			<a id="fir" class="sye">首&nbsp;&nbsp;页</a>
		</td>
		<td width="6%">
			<a id="pre" class="sye">上一页</a>
		</td>
		<td width="6%">
			<a id="next" class="sye">下一页</a>
		</td>
		<td width="6%">
			<a id="last" class="sye">末&nbsp;&nbsp;页</a>
		</td>
		<td width="12%">当前第<span style="color:red;">${curPageNum}</span>/${totalPage}页</td>
		</tr>
</table>

<s:hidden name="curPageNum"/>				
<script type="text/javascript">
	$(function(){
		var curPageNum = ${curPageNum};
		var totalPage = ${totalPage};
		if(totalPage == 1) {
			//不显示任何分页控件
			$("#fir").css("display","none");
			$("#pre").css("display","none");
			$("#next").css("display","none");
			$("#last").css("display","none");
		}else if(curPageNum == 1) {
			//只提示下一页和末页
			$("#fir").css("display","none");
			$("#pre").css("display","none");
			$("#next").css("display","inline");
			$("#last").css("display","inline");
		}else if(curPageNum == totalPage) {
			//只提示上一页和首页
			$("#fir").css("display","inline");
			$("#pre").css("display","inline");
			$("#next").css("display","none");
			$("#last").css("display","none");
		}else{
			//提示所有分页控件
			$("#fir").css("display","inline");
			$("#pre").css("display","inline");
			$("#next").css("display","inline");
			$("#last").css("display","inline");
		}
	
		//首页
		$("#fir").click(function(){
			$("[name=curPageNum]").val(1);
			$("form:first").submit();
		});
		//上一页
		$("#pre").click(function(){
			$("[name=curPageNum]").val($("[name=curPageNum]").val() - 1);
			$("form:first").submit();
		});
		//下一页
		$("#next").click(function(){
			//收集页码值，将页码值设置为指定值，提交表单
			//获取之前页码值，然后加1，再设置回去,
			//注意，此处$("[name=curPageNum]").val()取出的值为字符串，必须转成数字，这里通过*1转成数字
			$("[name=curPageNum]").val($("[name=curPageNum]").val()*1 + 1);
			$("form:first").submit();
		});
		
		//末页
		$("#last").click(function(){
			//需要最大页码值
			$("[name=curPageNum]").val(${totalPage});
			$("form:first").submit();
		});
	});
</script>