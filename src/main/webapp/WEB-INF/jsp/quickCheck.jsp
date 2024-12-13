<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Check</title>
</head>
<body>
	<h3>주문 조회할 ID</h3>
	<form id="orderCheck" method="get">
		<label for="orderCheck">파트너 ID</label>
		<input type="text" id="partnerOrderId" name="partnerOrderId">
		<button type="button" onclick="submitForm()">조회</button>
	</form>
	
	<buttom type="button" onclick="test()">test</buttom>
	
	<script>
	function submitForm() {
		const form = document.getElementById("orderCheck");
		const ID = document.getElementById("partnerOrderId").value;
		
		//alert(ID);
		
		if (ID) {
			form.action = '/kakaoTest/order/' + ID + '.do';
			form.method = 'GET';
			form.submit();
		} else {
			alert("Invaild ID");
		}
	}
	
	function test() { alert("Hello"); }
	</script>
</body>
</html>