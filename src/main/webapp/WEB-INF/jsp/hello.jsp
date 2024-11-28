<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
	</head>
	<body>
		<h1>Response from API</h1>
		<c:choose>
		    <c:when test="${not empty apiResponse}">
		        <p>API Response: ${apiResponse}</p>
		    </c:when>
		    <c:otherwise>
		        <p>No API Response available.</p>
		    </c:otherwise>
		</c:choose>
	</body>
</html>