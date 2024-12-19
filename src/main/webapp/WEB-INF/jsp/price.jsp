<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Order Result</title>
    <link href="<%=request.getContextPath()%>/css/result.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h1>Order Result</h1>
    <p>Response Code: ${responseCode}</p>
    
	<p>Request ID: ${requestId}</p>

	<div class="inner-container">
	<h3>Receipt</h3>
		<p>Total Price: ${totalPrice}</p>
    </div>
    
    <a href="${pageContext.request.contextPath}/">Home</a>
</div>
</body>
</html>