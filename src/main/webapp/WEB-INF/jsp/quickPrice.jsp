<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Check Price</title>
</head>
<body>
    <h1>가격 조회하기</h1>
    <form id="orderForm" method="post" action="/kakaoTest/price.do">
        <label for="orderType">주문 유형:</label><br>
        <select id="orderType" name="orderType" required>
            <option value="QUICK">퀵</option>
            <option value="QUICK_ECONOMY">퀵 이코노미</option>
            <option value="QUICK_EXPRESS">퀵 급송</option>
            <option value="DOBO">도보 배송</option>
        </select><br><br>
        
        <label for="fleetOption">차량 선택:
        <select id="fleet" name="fleet">
        	<option value="MOTORCYCLE">오토바이</option>
        	<option value="JIMBAJI_MOTORCYCLE">짐받이 오토바이</option>
        	<option value="PASSENGER_CAR">승용차</option>
        	<option value="DAMAS">다마스</option>
        	<option value="LABO">라보</option>
        	<option value="TON">1톤</option>
        </select><br>
        <select id="fleetType" name="fleetType">
        	<option value="MINIMUM">최소 차량 배차</option>
        	<option value="REQUIRED">지정 차량 배차</option>
        </select>
        </label><br>

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
        <label for="pickupLongitude">출발지 상하차 방법:</label><br> <!-- 퀵, size:L 일 경우만 설정 -->
        <select id="pickupLoadingMethod" name="pickupLoadingMethod">
        	<option value="PICKER">기사님이 상하차</option>
        	<option value="USER">고객이 상하차</option>
        	<option value="TOGETHER">같이 상하차</option>
        </select><br>
        <label for="pickupWishTime">픽업 희망 시간</label><br>
        <input type="date" id="pickupWishTime" name="pickupWishTime"><br>
        <label for="pickupContactName">출발지 이름:</label><br>
        <input type="text" id="pickupContactName" name="pickupContactName" required><br>
        <label for="pickupContactPhone">출발지 전화번호:</label><br>
        <input type="text" id="pickupContactPhone" name="pickupContactPhone" required><br><br>
		<label for="pickupNote">출발지 메모:</label><br>
        <input type="text" id="pickupNote" name="pickupNote"><br><br>

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
        <label for="dropoffLongitude">목적지 상하차 방법:</label><br> <!-- 퀵, size:L 일 경우만 설정 -->
        <select id="dropoffLoadingMethod" name="dropoffLoadingMethod">
        	<option value="PICKER">기사님이 상하차</option>
        	<option value="USER">고객이 상하차</option>
        	<option value="TOGETHER">같이 상하차</option>
        </select><br>
        <label for="dropoffWishTime">픽업 희망 시간</label><br>
        <input type="date" id="dropoffWishTime" name="dropoffWishTime"><br>
        <label for="dropoffContactName">목적지 이름:</label><br>
        <input type="text" id="dropoffContactName" name="dropoffContactName" required><br>
        <label for="dropoffContactPhone">목적지 전화번호:</label><br>
        <input type="text" id="dropoffContactPhone" name="dropoffContactPhone" required><br><br>
		<label for="dropoffNote">목적지 메모:</label><br>
        <input type="text" id="dropoffNote" name="dropoffNote"><br><br>

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
</body>
</html>