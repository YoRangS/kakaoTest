<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.OutputStream" %>
<%@ page import="java.net.HttpURLConnection" %>
<%@ page import="java.net.URL" %>
<%@ page import="org.json.JSONObject" %>
<%
    // Retrieve form data
    String partnerOrderId = request.getParameter("partnerOrderId");
    String orderType = request.getParameter("orderType");

    // Pickup details
    String pickupBasicAddress = request.getParameter("pickupBasicAddress");
    String pickupDetailAddress = request.getParameter("pickupDetailAddress");
    double pickupLatitude = Double.parseDouble(request.getParameter("pickupLatitude"));
    double pickupLongitude = Double.parseDouble(request.getParameter("pickupLongitude"));
    String pickupContactName = request.getParameter("pickupContactName");
    String pickupContactPhone = request.getParameter("pickupContactPhone");

    // Dropoff details
    String dropoffBasicAddress = request.getParameter("dropoffBasicAddress");
    String dropoffDetailAddress = request.getParameter("dropoffDetailAddress");
    double dropoffLatitude = Double.parseDouble(request.getParameter("dropoffLatitude"));
    double dropoffLongitude = Double.parseDouble(request.getParameter("dropoffLongitude"));
    String dropoffContactName = request.getParameter("dropoffContactName");
    String dropoffContactPhone = request.getParameter("dropoffContactPhone");

    // Product details
    String productName = request.getParameter("productName");
    int productQuantity = Integer.parseInt(request.getParameter("productQuantity"));
    int productPrice = Integer.parseInt(request.getParameter("productPrice"));
    String productDetail = request.getParameter("productDetail");

    // Create JSON payload
    JSONObject json = new JSONObject();
    json.put("partnerOrderId", partnerOrderId);
    json.put("orderType", orderType);
    json.put("pickup", new JSONObject()
            .put("location", new JSONObject()
                .put("basicAddress", pickupBasicAddress)
                .put("detailAddress", pickupDetailAddress)
                .put("latitude", pickupLatitude)
                .put("longitude", pickupLongitude))
            .put("contact", new JSONObject()
                .put("name", pickupContactName)
                .put("phone", pickupContactPhone)));
    json.put("dropoff", new JSONObject()
            .put("location", new JSONObject()
                .put("basicAddress", dropoffBasicAddress)
                .put("detailAddress", dropoffDetailAddress)
                .put("latitude", dropoffLatitude)
                .put("longitude", dropoffLongitude))
            .put("contact", new JSONObject()
                .put("name", dropoffContactName)
                .put("phone", dropoffContactPhone)));
    json.put("productInfo", new JSONObject()
            .put("trayCount", 1)
            .put("size", "XS")
            .put("totalPrice", productPrice * productQuantity)
            .put("products", new JSONObject()
                .put("name", productName)
                .put("quantity", productQuantity)
                .put("price", productPrice)
                .put("detail", productDetail)));

    // API endpoint and headers
    String apiEndpoint = "https://open-api-logistics.kakaomobility.com/goa-sandbox-service/api/v2/orders";
    String authorization = "XXX"; // Replace with actual Authorization value
    String vendorId = "kakaoT"; // Replace with actual vendor ID

    try {
        URL url = new URL(apiEndpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", authorization);
        conn.setRequestProperty("vendor", vendorId);

        // Send JSON payload
        OutputStream os = conn.getOutputStream();
        os.write(json.toString().getBytes("UTF-8"));
        os.close();

        // Handle response
        int responseCode = conn.getResponseCode();
        out.println("Response Code: " + responseCode);
    } catch (Exception e) {
    	System.out.println(e.toString());
    }
%>
