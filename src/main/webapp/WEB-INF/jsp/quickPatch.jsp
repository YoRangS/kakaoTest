<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Patch</title>
<link href="css/lookup.css" rel="stylesheet" />
</head>
<body>
  <div class="container">
    <h3 class="title">주문 변경할 ID</h3>
    <p id="vendor" hidden>${vender}</p>
    <p id="authorization" hidden>${authorization}</p>
    <form id="orderPatch" method="post">
      <label for="partnerOrderId">파트너 ID</label>
      <input type="hidden" name="_method" value="PATCH">
      <input type="text" id="partnerOrderId" name="partnerOrderId" class="input-field" placeholder="Enter Partner ID" required><br>

      <label for="orderStatus">주문 상태</label>
      <select id="orderStatus" name="orderStatus" class="select-field" required>
        <option value="ABORT">배송 강제 종료</option>
        <option value="MATCH_PICKER">배송원 배정 완료</option>
        <option value="CANCEL">배송 취소</option>
        <option value="PICKUP_COMPLETED">배송원 픽업 완료</option>
        <option value="DROPOFF_COMPLETED">배송 완료</option>
      </select><br>

      <label for="cancelBy">취소 원인</label>
      <select id="cancelBy" name="cancelBy" class="select-field" required>
        <option value="PICKUP">배송원에 의한 주문 취소</option>
        <option value="ADMIN">관리자에 의한 주문 취소</option>
      </select><br><br>

    <button type="button" class="btn-submit" onclick="submitForm()">조회</button>
    </form>
	
	
	<script>
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
	</script>
	</div>
</body>
</html>