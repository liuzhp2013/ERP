<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE HTML>
<html>
  <head>
    <title>error page</title>
	<meta charset="utf-8">
	<style type="text/css">
		.errorMessage{
			color:red;
		}
	</style>
  </head>
  
  <body>
    <s:actionerror/>
  </body>
</html>
