<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>주문 조회할 ID</h3>
	<form id="orderPicker" method="get">
		<label for="orderPicker">파트너 ID</label>
		<input type="text" id="partnerOrderId" name="partnerOrderId">
		<button type="button" onclick="submitForm()">조회</button>
	</form>
	
	<script>
	function submitForm() {
		const form = document.getElementById("orderPicker");
		const ID = document.getElementById("partnerOrderId").value;
		
		//alert(ID);
		
		if (ID) {
			form.action = '/kakaoTest/picker/' + ID + '.do';
			form.method = 'GET';
			form.submit();
		} else {
			alert("Invaild ID");
		}
	}
	</script>
</body>
</html>