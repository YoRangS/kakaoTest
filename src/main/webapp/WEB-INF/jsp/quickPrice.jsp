<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Check Price</title>
    <link href="<%=request.getContextPath()%>/css/orders.css" rel="stylesheet">
</head>
<body>
    <div class="container">
    <h1>가격 조회하기</h1>
    <form id="orderForm" method="post" action="/kakaoTest/price.do">
        <label for="orderType">주문 유형:</label><br>
        <select id="orderType" name="orderType" required>
            <option value="QUICK">퀵</option>
            <option value="QUICK_ECONOMY">퀵 이코노미</option>
            <option value="QUICK_EXPRESS">퀵 급송</option>
            <option value="DOBO">도보 배송</option>
        </select><br><br>

        <h3>출발지 정보</h3>
        <!-- 출발지 위치 정보  -->
	        <label for="pickupBasicAddress">출발지 기본주소:</label><br>
	        <input type="text" id="pickupBasicAddress" name="pickupBasicAddress" required><br>
	        <label for="pickupDetailAddress">출발지 상세주소:</label><br>
	        <input type="text" id="pickupDetailAddress" name="pickupDetailAddress"><br>
	        <label for="pickupLatitude">출발지 위도정보:</label><br>
	        <input type="number" step="any" id="pickupLatitude" name="pickupLatitude" required><br>
	        <label for="pickupLongitude">출발지 경도정보:</label><br>
	        <input type="number" step="any" id="pickupLongitude" name="pickupLongitude" required><br>

        <h3>목적지 정보</h3>
        <!-- 목적지 위치 정보  -->
	        <label for="dropoffBasicAddress">목적지 기본주소:</label><br>
	        <input type="text" id="dropoffBasicAddress" name="dropoffBasicAddress" required><br>
	        <label for="dropoffDetailAddress">목적지 상세주소:</label><br>
	        <input type="text" id="dropoffDetailAddress" name="dropoffDetailAddress"><br>
	        <label for="dropoffLatitude">목적지 위도정보:</label><br>
	        <input type="number" step="any" id="dropoffLatitude" name="dropoffLatitude" required><br>
	        <label for="dropoffLongitude">목적지 경도정보:</label><br>
	        <input type="number" step="any" id="dropoffLongitude" name="dropoffLongitude" required><br>

        <h3>제품 정보</h3>
        <label for="size">상품 크기</label>
        <select id="size" name="size">
        	<option value="XS">서류, 초소형</option>
        	<option value="S">소형</option>
        	<option value="M">중형</option>
        	<option value="L">대형</option>
        </select><br>

        <button type="submit">Submit Order</button>
    </form>
    </div>
</body>
</html>
