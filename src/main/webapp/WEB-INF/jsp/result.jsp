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
    
	<p>Request ID: ${requestID}</p>
	<p>Partner Order ID: ${partnerOrderID}</p>

	<div class="inner-container">
	<h3>Receipt</h3>
		<p>Current status: ${status}</p>
		<p>Order Type: ${orderType}</p>
		<p>Payment Type: ${paymentType}</p>
		<p>Order ID: ${orderID}</p>
		<p>Total Price: ${totalPrice}</p>
		<p>Histories: ${histories}</p>
    </div>
    
    <a href="${pageContext.request.contextPath}/">Home</a>
</div>
</body>
</html>