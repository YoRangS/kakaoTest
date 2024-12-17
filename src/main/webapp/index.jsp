<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>mvc test</title>
		<link href="/css/index.css" rel="stylesheet"/>
	</head>
	<body>
		<br><br>
		<div class="midblock">
		<a href="hello.do">Hello World</a><br>
		<a href="quickHome.do">quickHome</a><br>
		<a href="quickCheck.do">quickCheck</a><br>
		<a href="quickPrice.do">quickPrice</a><br>
		<a href="quickPatch.do">quickPatch</a><br>
		</div>
	</body>
</html>