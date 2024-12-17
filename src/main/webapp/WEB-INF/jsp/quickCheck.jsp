<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Check Order</title>
<link href="css/lookup.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <h3 class="title">Check Order</h3>
        <form id="orderCheck" class="form-section" method="get">
            <label for="partnerOrderId">Partner ID</label>
            <input type="text" id="partnerOrderId" name="partnerOrderId">
            <button type="button" class="btn-submit" onclick="submitForm()">Check</button>
        </form>
    </div>

    <script>
        function submitForm() {
            const form = document.getElementById("orderCheck");
            const ID = document.getElementById("partnerOrderId").value;

            if (ID) {
                form.action = '/kakaoTest/order/' + ID + '.do';
                form.method = 'GET';
                form.submit();
            } else {
                alert("Invalid ID");
            }
        }
    </script>
</body>
</html>