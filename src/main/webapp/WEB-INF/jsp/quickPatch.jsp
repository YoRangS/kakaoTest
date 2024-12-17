<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Patch</title>
</head>
<body>
	<h3>주문 변경할 ID</h3>
	<p id="vendor" hidden>${vender}</p>
	<p id="authorization" hidden>${authorization}</p>
	<form id="orderPatch" method="post">
		<label for="orderPatch">파트너 ID</label>
		<input type="hidden" name="_method" value="PATCH">
		<input type="text" id="partnerOrderId" name="partnerOrderId"><br>
		
		<select id="orderStatus" name="orderStatus" required>
            <option value="ABORT">배송 강제 종료</option>
            <option value="MATCH_PICKER">배송원 배정 완료</option>
            <option value="CANCEL">배송 취소</option>
            <option value="PICKUP_COMPLETED">배송원 픽업 완료</option>
            <option value="DROPOFF_COMPLETED">배송 완료</option>
        </select><br><br>
        <select id="cancelBy" name="cancelBy" required>
            <option value="PICKUP">배송원에 의한 주문 취소</option>
            <option value="ADMIN">관리자에 의한 주문 취소</option>
        </select><br><br>
		<button type="button" onclick="submitForm()">조회</button>
	</form>
	
	<script>
	function submitForm() {
	    const ID = document.getElementById("partnerOrderId").value;
	    const orderStatus = document.getElementById("orderStatus").value;
	    const cancelBy = document.getElementById("cancelBy").value;

	    if (ID) {
	        const url = '/kakaoTest/patch/' + ID + '.do';
	        const data = {
	            orderStatus: orderStatus,
	            cancelBy: cancelBy
	        };
	
	        const vendorId = document.getElementById("vendor").value;
		    const authorization = document.getElementById("authorization").value;
	
	        fetch(url, {
	            method: 'PATCH',
	            headers: {
	                'Content-Type': 'application/json',
	                'Vendor-ID': vendorId,
	                'Authorization': authorization
	            },
	            body: JSON.stringify(data)
	        })
	        .then(response => {
	            if (!response.ok) {
	                throw new Error('Network response was not ok ' + response.statusText);
	            }
	            return response.json();
	        })
	        .then(data => {
	            console.log('Success:', data);
	            document.getElementById("responseMessage").innerText = "Success: " + JSON.stringify(data);
	        })
	        .catch((error) => {
	            console.error('Error:', error);
	            document.getElementById("responseMessage").innerText = "Error: " + error.message;
	        });
	    } else {
	        alert("Invalid ID");
	    }
	}
	</script>
	
	<!-- <script>
	function submitForm() {
		const form = document.getElementById("orderPatch");
		const ID = document.getElementById("partnerOrderId").value;
		
		//alert(ID);
		
		if (ID) {
			form.action = '/kakaoTest/patch/' + ID + '.do';
			//form.method = 'POST';
			form.submit();
		} else {
			alert("Invaild ID");
		}
	}
	</script> -->
</body>
</html>