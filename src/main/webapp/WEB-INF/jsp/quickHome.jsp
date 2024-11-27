<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Order Form</title>
</head>
<body>
    <h1>Create a New Order</h1>
    <form id="orderForm" method="post" action="orderProcessor.jsp">
        <label for="partnerOrderId">Partner Order ID:</label><br>
        <input type="text" id="partnerOrderId" name="partnerOrderId" required><br><br>

        <label for="orderType">Order Type:</label><br>
        <select id="orderType" name="orderType" required>
            <option value="QUICK">Quick</option>
            <option value="QUICK_ECONOMY">Quick Economy</option>
            <option value="QUICK_EXPRESS">Quick Express</option>
            <option value="DOBO">Walking Delivery</option>
        </select><br><br>

        <h3>배송자 정보</h3>
        <label for="pickupBasicAddress">Basic Address:</label><br>
        <input type="text" id="pickupBasicAddress" name="pickupBasicAddress" required><br>
        <label for="pickupDetailAddress">Detail Address:</label><br>++99
        <input type="text" id="pickupDetailAddress" name="pickupDetailAddress"><br>
        <label for="pickupLatitude">Latitude:</label><br>
        <input type="number" step="any" id="pickupLatitude" name="pickupLatitude" required><br>
        <label for="pickupLongitude">Longitude:</label><br>
        <input type="number" step="any" id="pickupLongitude" name="pickupLongitude" required><br>
        <label for="pickupContactName">Contact Name:</label><br>
        <input type="text" id="pickupContactName" name="pickupContactName" required><br>
        <label for="pickupContactPhone">Contact Phone:</label><br>
        <input type="text" id="pickupContactPhone" name="pickupContactPhone" required><br><br>

        <h3>배송지 정보</h3>
        <label for="dropoffBasicAddress">Basic Address:</label><br>
        <input type="text" id="dropoffBasicAddress" name="dropoffBasicAddress" required><br>
        <label for="dropoffDetailAddress">Detail Address:</label><br>
        <input type="text" id="dropoffDetailAddress" name="dropoffDetailAddress"><br>
        <label for="dropoffLatitude">Latitude:</label><br>
        <input type="number" step="any" id="dropoffLatitude" name="dropoffLatitude" required><br>
        <label for="dropoffLongitude">Longitude:</label><br>
        <input type="number" step="any" id="dropoffLongitude" name="dropoffLongitude" required><br>
        <label for="dropoffContactName">Contact Name:</label><br>
        <input type="text" id="dropoffContactName" name="dropoffContactName" required><br>
        <label for="dropoffContactPhone">Contact Phone:</label><br>
        <input type="text" id="dropoffContactPhone" name="dropoffContactPhone" required><br><br>

        <h3>제품 정보</h3>
        <label for="productName">Product Name:</label><br>
        <input type="text" id="productName" name="productName" required><br>
        <label for="productQuantity">Quantity:</label><br>
        <input type="number" id="productQuantity" name="productQuantity" required><br>
        <label for="productPrice">Price:</label><br>
        <input type="number" id="productPrice" name="productPrice" required><br>
        <label for="productDetail">Detail:</label><br>
        <textarea id="productDetail" name="productDetail"></textarea><br><br>

        <button type="submit">Submit Order</button>
    </form>
</body>
</html>
