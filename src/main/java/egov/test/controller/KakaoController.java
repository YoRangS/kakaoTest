package egov.test.controller;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import egov.test.component.KakaoAuthComponent;
import egov.test.config.ApiConfig;
import egov.test.service.OrderVO;

@Controller
public class KakaoController {
	
	@Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }
	
	@Autowired
	private KakaoAuthComponent kakaoAuthComponent;
	
	private final ApiConfig apiConfig = ApiConfig.getApiConfigSingleton();
	
	private RestTemplate restTemplate = new RestTemplate();
	
	@RequestMapping("/quickHome.do")
	public String quickHome() {
		return "/quickHome";
	}
	
	@RequestMapping("/quickCheck.do")
	public String quickCheck() {
		return "/quickCheck";
	}
	
	@RequestMapping("/quickPrice.do")
	public String quickPrice() {
		return "/quickPrice";
	}
	
	@RequestMapping("/quickPatch.do")
	public String quickPatch(Model model) throws InvalidKeyException, NoSuchAlgorithmException {
		final String VENDOR_ID = apiConfig.getVendorID();
        final String authorization = getAuthorization();
        
        model.addAttribute("vender", VENDOR_ID);
        model.addAttribute("authorization", authorization);
		
		return "/quickPatch";
	}
	
	@RequestMapping("/quickPicker.do")
	public String quickPicker() {
		return "/quickPicker";
	}
	
	@RequestMapping("/test")
	@GetMapping("/{name}")
	public String testFunction(@PathVariable String name) {
		String returnValue = "test return value RESTAPI: " + name;
		
		return returnValue;
	}
	
	public String getAuthorization() throws InvalidKeyException, NoSuchAlgorithmException {
		// 입력값
        final String timestamp = String.valueOf(System.currentTimeMillis());
        final String nonce = "121212";
        final String apiKey = apiConfig.getKeyValue();
        System.out.println("tesT");

        // 서명 및 Authorization 생성
        String sign = kakaoAuthComponent.generateSignature(timestamp, nonce, apiKey);
        String authorization = kakaoAuthComponent.generateAuthorization(timestamp, nonce, sign);
        
        return authorization;
	}
	
	// Authorization 생성 및 검증
	@RequestMapping(value = "/hello.do")
	public String hello(Model model) {
		String authorization = null;
		String apiResponse = null;  // API 응답을 저장할 변수
		try {
            authorization = getAuthorization();

            // Authorization 값 확인
            System.out.println("Authorization: " + authorization);
            
            // API 호출을 위한 URL 설정
            String apiUrl = apiConfig.getAuthURL();

            // HttpHeaders 객체 생성
            HttpHeaders headers = new HttpHeaders();
            headers.set("accept", "application/json");
            headers.set("Authorization", authorization);  // 동적으로 생성된 Authorization
            headers.set("Content-Type", "application/json");
            headers.set("vendor", apiConfig.getVendorID());  // vendor_id 설정

            // HttpEntity 생성 (헤더 포함)
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // GET 요청 보내기
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

         // 응답 본문을 model에 저장
            apiResponse = response.getBody();

        } catch (Exception e) {
            e.printStackTrace();
            // 예외 발생 시 메시지 저장
            apiResponse = "API 요청 중 오류 발생: " + e.getMessage();
        }

        // 모델에 API 응답을 전달
        model.addAttribute("apiResponse", apiResponse);

        // "hello" 뷰 이름을 반환
        return "hello";
	}
	
	// 주문 정보 전달하기 (quickHome에서 받아옴)
//	@PostMapping("/order.do")
	@RequestMapping(value = "/order.do", method = RequestMethod.POST)
	public String submitOrder(@ModelAttribute OrderVO orderVO, Model model) {
        try {
        	final String API_ENDPOINT = apiConfig.getHostURL() + "/api/v2/orders";
            final String VENDOR_ID = apiConfig.getVendorID();
            
            final String authorization = getAuthorization();
            
	        JSONObject json = new JSONObject();
	
	        // "pickup" 객체 생성
	        JSONObject pickupLocation = new JSONObject();
	        pickupLocation.put("basicAddress", orderVO.getPickupBasicAddress());
	        pickupLocation.put("detailAddress", orderVO.getPickupDetailAddress());
	        pickupLocation.put("latitude", orderVO.getPickupLatitude());
	        pickupLocation.put("longitude", orderVO.getPickupLongitude());
	
	        JSONObject pickupContact = new JSONObject();
	        pickupContact.put("name", orderVO.getPickupContactName());
	        pickupContact.put("phone", orderVO.getPickupContactPhone());
	
	        JSONObject pickup = new JSONObject();
	        pickup.put("location", pickupLocation);
	        pickup.put("contact", pickupContact);
	
	        // "dropoff" 객체 생성
	        JSONObject dropoffLocation = new JSONObject();
	        dropoffLocation.put("basicAddress", orderVO.getDropoffBasicAddress());
	        dropoffLocation.put("detailAddress", orderVO.getDropoffDetailAddress());
	        dropoffLocation.put("latitude", orderVO.getDropoffLatitude());
	        dropoffLocation.put("longitude", orderVO.getDropoffLongitude());
	
	        JSONObject dropoffContact = new JSONObject();
	        dropoffContact.put("name", orderVO.getDropoffContactName());
	        dropoffContact.put("phone", orderVO.getDropoffContactPhone());

	        JSONObject dropoff = new JSONObject();
	        dropoff.put("location", dropoffLocation);
	        dropoff.put("contact", dropoffContact);
	
	        // "productInfo" 객체 생성
	        JSONArray productsArray = new JSONArray();
	        JSONObject product = new JSONObject();
	        product.put("name", orderVO.getProductName());
	        product.put("quantity", orderVO.getProductQuantity());
	        product.put("price", orderVO.getProductPrice());
	        product.put("detail", orderVO.getProductDetail());
	
	        productsArray.put(product);
	
	        JSONObject productInfo = new JSONObject();
	        productInfo.put("trayCount", orderVO.getTrayCount());
	        productInfo.put("size", orderVO.getSize());
	        productInfo.put("totalPrice", orderVO.getProductPrice() * orderVO.getProductQuantity());
	        productInfo.put("products", productsArray);
	
	        // 최종 JSON 객체에 데이터 삽입
	        json.put("partnerOrderId", orderVO.getPartnerOrderId());
	        json.put("orderType", orderVO.getOrderType());
	        json.put("pickup", pickup);
	        json.put("dropoff", dropoff);
	        json.put("productInfo", productInfo);
            
            System.out.println("Generated JSON: " + json.toString());
            
            // HTTP Header 설정
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            headers.set("Authorization", authorization);
            headers.set("vendor", VENDOR_ID);

            // HTTP 요청 생성
//            String testJsonString = "{\"partnerOrderId\":\"{연동사 주문 ID - 유니크 해야합니다}\",\"orderType\":\"QUICK\",\"pickup\":{\"location\":{\"latitude\":37.4354059,\"basicAddress\":\"서울특별시 강남구 역삼동 xxx\",\"detailAddress\":\"1층\",\"longitude\":126.74551},\"contact\":{\"name\":\"전달하는 사람 이름\",\"phone\":\"010-1000-0001\"}},\"dropoff\":{\"location\":{\"basicAddress\":\"서울특별시 강남구 일원동 xxx\",\"detailAddress\":\"2층\",\"latitude\":37.569691,\"longitude\":126.825791},\"contact\":{\"name\":\"받는 사람 이름\",\"phone\":\"010-1000-0002\"}},\"productInfo\":{\"trayCount\":1,\"size\":\"XS\",\"totalPrice\":29800,\"products\":[{\"name\":\"양념게장 (대)\",\"quantity\":\"1\",\"price\":29800,\"detail\":\"터지면 큰일남\"}]}}";
            HttpEntity<String> requestEntity = new HttpEntity<>(json.toString(), headers);
//            HttpEntity<String> requestEntity = new HttpEntity<>(testJsonString, headers);
            
//            System.out.println("testJsonString : " + testJsonString);
            System.out.println("Authorization : " + authorization);
            
            // API 호출
//            ResponseEntity<String> response = restTemplate.postForEntity(API_ENDPOINT, requestEntity, String.class);
            ResponseEntity<String> response = null;
            try {
                response = restTemplate.postForEntity(API_ENDPOINT, requestEntity, String.class);

                System.out.println("Response Status Code: " + response.getStatusCode());
                System.out.println("Response Body: " + response.getBody());
            } catch (HttpClientErrorException e) {
                System.out.println("HTTP Error Status Code: " + e.getStatusCode());
                System.out.println("HTTP Error Response Body: " + e.getResponseBodyAsString());
                e.printStackTrace();
            } catch (HttpMessageNotReadableException e) {
                System.out.println("JSON 형식 오류: " + e.getMessage());
                e.printStackTrace();
            } catch (RestClientException e) {
            	if (e instanceof HttpClientErrorException) {
                    HttpClientErrorException httpError = (HttpClientErrorException) e;
                    System.out.println("HTTP 에러 발생: " + httpError.getStatusCode());
                    System.out.println("응답 본문: " + httpError.getResponseBodyAsString());
                } else if (e instanceof HttpServerErrorException) {
                    HttpServerErrorException serverError = (HttpServerErrorException) e;
                    System.out.println("서버 에러 발생: " + serverError.getStatusCode());
                    System.out.println("응답 본문: " + serverError.getResponseBodyAsString());
                } else if (e instanceof ResourceAccessException) {
                    System.out.println("리소스 접근 에러: " + e.getMessage());
                } else {
                    System.out.println("기타 예외 발생: " + e.getMessage());
                }
                e.printStackTrace();
            } catch (Exception e) {
                System.out.println("기타 예외 발생");
                e.printStackTrace();
            }
            
            if (response == null) System.out.println("response is null");
            else System.out.println("response is not null");

            // 응답 처리
            model.addAttribute("responseCode", response.getStatusCodeValue());
            model.addAttribute("responseBody", response.getBody());
            
            return "result";

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
            
            return "result";
        }
    }
	
	@GetMapping("/testtest.do")
	public String test2() {
		return "/quickHome";
	}
	
	
	@GetMapping("/order/{partnerOrderId}.do")
	public String checkOrder(@PathVariable String partnerOrderId, Model model) {
		try {
			ApiConfig apiconfig = ApiConfig.getApiConfigSingleton();
			
			String authorization = getAuthorization();
			String vendorID = apiConfig.getVendorID();

			// API 호출을 위한 URL 설정
            String apiUrl = apiConfig.getHostURL() + "/api/v2/orders/" + partnerOrderId;

            // HttpHeaders 객체 생성
            HttpHeaders headers = new HttpHeaders();
            headers.set("accept", "application/json");
            headers.set("Authorization", authorization);  // 동적으로 생성된 Authorization
            headers.set("Content-Type", "application/json");
            headers.set("vendor", apiConfig.getVendorID());  // vendor_id 설정

            // HttpEntity 생성 (헤더 포함)
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // GET 요청 보내기
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);
            
            // 응답 처리
            model.addAttribute("responseCode", response.getStatusCodeValue());
            model.addAttribute("responseBody", response.getBody());
            
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("responseBody", "API 요청 중 오류 발생: " + e.getMessage());
        }
		
		return "result";
	}
	
	@RequestMapping(value = "/price.do", method = RequestMethod.POST)
	public String CheckPrice(OrderVO orderVO, Model model) {
		try {
        	final String API_ENDPOINT = apiConfig.getHostURL() + "/api/v2/orders/price";
            final String VENDOR_ID = apiConfig.getVendorID();
            
            final String authorization = getAuthorization();
            
	        JSONObject json = new JSONObject();
	
	        // "pickup" 객체 생성
		        JSONObject pickupLocation = new JSONObject();
		        pickupLocation.put("basicAddress", orderVO.getPickupBasicAddress());
		        pickupLocation.put("detailAddress", orderVO.getPickupDetailAddress());
		        pickupLocation.put("latitude", orderVO.getPickupLatitude());
		        pickupLocation.put("longitude", orderVO.getPickupLongitude());
		
		        JSONObject pickupContact = new JSONObject();
		        pickupContact.put("name", orderVO.getPickupContactName());
		        pickupContact.put("phone", orderVO.getPickupContactPhone());
	
	        JSONObject pickup = new JSONObject();
	        pickup.put("location", pickupLocation);
	        pickup.put("contact", pickupContact);
	
	        // "dropoff" 객체 생성
		        JSONObject dropoffLocation = new JSONObject();
		        dropoffLocation.put("basicAddress", orderVO.getDropoffBasicAddress());
		        dropoffLocation.put("detailAddress", orderVO.getDropoffDetailAddress());
		        dropoffLocation.put("latitude", orderVO.getDropoffLatitude());
		        dropoffLocation.put("longitude", orderVO.getDropoffLongitude());
		
		        JSONObject dropoffContact = new JSONObject();
		        dropoffContact.put("name", orderVO.getDropoffContactName());
		        dropoffContact.put("phone", orderVO.getDropoffContactPhone());

	        JSONObject dropoff = new JSONObject();
	        dropoff.put("location", dropoffLocation);
	        dropoff.put("contact", dropoffContact);
	
	        // 최종 JSON 객체에 데이터 삽입
	        json.put("orderType", orderVO.getOrderType());
	        json.put("pickup", pickup);
	        json.put("dropoff", dropoff);
	        json.put("productSize", orderVO.getSize());
            
            System.out.println("Generated JSON: " + json.toString());
            
            // HTTP Header 설정
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            headers.set("Authorization", authorization);
            headers.set("vendor", VENDOR_ID);

            // HTTP 요청 생성
            HttpEntity<String> requestEntity = new HttpEntity<>(json.toString(), headers);
            System.out.println("Authorization : " + authorization);
            
            // API 호출
            ResponseEntity<String> response = null;
            try {
                response = restTemplate.postForEntity(API_ENDPOINT, requestEntity, String.class);

                System.out.println("Response Status Code: " + response.getStatusCode());
                System.out.println("Response Body: " + response.getBody());
            } catch (HttpClientErrorException e) {
                System.out.println("HTTP Error Status Code: " + e.getStatusCode());
                System.out.println("HTTP Error Response Body: " + e.getResponseBodyAsString());
                e.printStackTrace();
            } catch (HttpMessageNotReadableException e) {
                System.out.println("JSON 형식 오류: " + e.getMessage());
                e.printStackTrace();
            } catch (RestClientException e) {
            	if (e instanceof HttpClientErrorException) {
                    HttpClientErrorException httpError = (HttpClientErrorException) e;
                    System.out.println("HTTP 에러 발생: " + httpError.getStatusCode());
                    System.out.println("응답 본문: " + httpError.getResponseBodyAsString());
                } else if (e instanceof HttpServerErrorException) {
                    HttpServerErrorException serverError = (HttpServerErrorException) e;
                    System.out.println("서버 에러 발생: " + serverError.getStatusCode());
                    System.out.println("응답 본문: " + serverError.getResponseBodyAsString());
                } else if (e instanceof ResourceAccessException) {
                    System.out.println("리소스 접근 에러: " + e.getMessage());
                } else {
                    System.out.println("기타 예외 발생: " + e.getMessage());
                }
                e.printStackTrace();
            } catch (Exception e) {
                System.out.println("기타 예외 발생");
                e.printStackTrace();
            }
            
            if (response == null) System.out.println("response is null");
            else System.out.println("response is not null");

            // 응답 처리
            model.addAttribute("responseCode", response.getStatusCodeValue());
            model.addAttribute("responseBody", response.getBody());
            
            return "result";

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
            
            return "result";
        }
	}
	
	@RequestMapping(value = "/patch/{partnerOrderId}.do", method = RequestMethod.POST, params = "_method=PATCH")
	public String patchOrder(@PathVariable String partnerOrderId, @ModelAttribute OrderVO orderVO, Model model) {
		try {
        	final String API_ENDPOINT = apiConfig.getHostURL() + "/api/v1/developers/orders/" + partnerOrderId + "/status";
            final String VENDOR_ID = apiConfig.getVendorID();
            
            final String authorization = getAuthorization();
            
	        JSONObject json = new JSONObject();
	
	        // JSON 객체에 데이터 삽입
	        json.put("orderStatus", orderVO.getOrderStatus());
	        json.put("cancelBy", orderVO.getCancelBy());
            
            System.out.println("Generated JSON: " + json.toString());
            
            // HTTP Header 설정
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            headers.set("Authorization", authorization);
            headers.set("vendor", VENDOR_ID);

            // HTTP 요청 생성
            HttpEntity<String> requestEntity = new HttpEntity<>(json.toString(), headers);
//            System.out.println("Authorization : " + authorization);
            
            // API 호출
            ResponseEntity<String> response = null;
            try {
                response = restTemplate.postForEntity(API_ENDPOINT, requestEntity, String.class);

                System.out.println("Response Status Code: " + response.getStatusCode());
                System.out.println("Response Body: " + response.getBody());
            } catch (HttpClientErrorException e) {
                System.out.println("HTTP Error Status Code: " + e.getStatusCode());
                System.out.println("HTTP Error Response Body: " + e.getResponseBodyAsString());
                e.printStackTrace();
            } catch (HttpMessageNotReadableException e) {
                System.out.println("JSON 형식 오류: " + e.getMessage());
                e.printStackTrace();
            } catch (RestClientException e) {
            	if (e instanceof HttpClientErrorException) {
                    HttpClientErrorException httpError = (HttpClientErrorException) e;
                    System.out.println("HTTP 에러 발생: " + httpError.getStatusCode());
                    System.out.println("응답 본문: " + httpError.getResponseBodyAsString());
                } else if (e instanceof HttpServerErrorException) {
                    HttpServerErrorException serverError = (HttpServerErrorException) e;
                    System.out.println("서버 에러 발생: " + serverError.getStatusCode());
                    System.out.println("응답 본문: " + serverError.getResponseBodyAsString());
                } else if (e instanceof ResourceAccessException) {
                    System.out.println("리소스 접근 에러: " + e.getMessage());
                } else {
                    System.out.println("기타 예외 발생: " + e.getMessage());
                }
                e.printStackTrace();
            } catch (Exception e) {
                System.out.println("기타 예외 발생");
                e.printStackTrace();
            }

            // 응답 처리
            model.addAttribute("responseCode", response.getStatusCodeValue());
            model.addAttribute("responseBody", response.getBody());
            
            return "result";

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
            
            return "result";
        }
	}
	
	@GetMapping("/picker/{partnerOrderId}.do")
	public String checkPicker(@PathVariable String partnerOrderId, Model model) {
		try {
			ApiConfig apiconfig = ApiConfig.getApiConfigSingleton();
			
			String authorization = getAuthorization();

			// API 호출을 위한 URL 설정
            String apiUrl = apiConfig.getHostURL() + "/api/v2/orders/" + partnerOrderId + "/picker";
//            String apiUrl = apiConfig.getHostURL() + "/api/v2/orders/" + partnerOrderId;
            System.out.println("url : " + apiUrl);

            // HttpHeaders 객체 생성
            HttpHeaders headers = new HttpHeaders();
//            headers.set("accept", "application/json");
            headers.set("Content-Type", "application/json");
            headers.set("Authorization", authorization);  // 동적으로 생성된 Authorization
            headers.set("vendor", apiConfig.getVendorID());  // vendor_id 설정
            
            System.out.println("Authorization : " + authorization);

            // HttpEntity 생성 (헤더 포함)
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // GET 요청 보내기
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);
            
            // 응답 처리
            model.addAttribute("responseCode", response.getStatusCodeValue());
            model.addAttribute("responseBody", response.getBody());
            
		} catch (HttpClientErrorException e) {
            System.out.println("HTTP Error Status Code: " + e.getStatusCode());
            System.out.println("HTTP Error Response Body: " + e.getResponseBodyAsString());
            e.printStackTrace();
            model.addAttribute("responseBody", "API 요청 중 오류 발생: " + e.getMessage());
        } catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("responseBody", "API 요청 중 오류 발생: " + e.getMessage());
        }
		
		return "result";
	}
}