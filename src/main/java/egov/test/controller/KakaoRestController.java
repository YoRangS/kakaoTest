package egov.test.controller;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import egov.test.component.KakaoAuthComponent;
import egov.test.config.ApiConfig;

@RestController
@RequestMapping("/rest")
public class KakaoRestController {
	
	@Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }
	
	public RestTemplate restTemplate() {
	    CloseableHttpClient httpClient = HttpClients.createDefault();
	    HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
	    return new RestTemplate(factory);
	}
	
	@Autowired
	private KakaoAuthComponent kakaoAuthComponent;
	
	private final ApiConfig apiConfig = ApiConfig.getApiConfigSingleton();
	
	private RestTemplate restTemplate = restTemplate();
	
	public String getAuthorization() throws InvalidKeyException, NoSuchAlgorithmException {
		// 입력값
        final String timestamp = String.valueOf(System.currentTimeMillis());
        final String nonce = "121212";
        final String apiKey = apiConfig.getKeyValue();

        // 서명 및 Authorization 생성
        String sign = kakaoAuthComponent.generateSignature(timestamp, nonce, apiKey);
        String authorization = kakaoAuthComponent.generateAuthorization(timestamp, nonce, sign);
        
        return authorization;
	}
	
	
	@GetMapping("/{name}")
	public String testFunction(@PathVariable String name) {
		String returnValue = "test return value RESTAPI: " + name;
		
		return returnValue;
	}
	
	@RequestMapping(value = "/orderJson.do", method = RequestMethod.POST)
	public String submitOrder(@RequestBody String jsonInput) {
	    try {
	        final String API_ENDPOINT = apiConfig.getHostURL() + "/api/v2/orders";
	        final String VENDOR_ID = apiConfig.getVendorID();
	        final String authorization = getAuthorization();

	        //http headers
	        HttpHeaders headers = new HttpHeaders();
	        headers.set("Content-Type", "application/json");
	        headers.set("Authorization", authorization);
	        headers.set("vendor",VENDOR_ID);

	        HttpEntity<String> requestEntity = new HttpEntity<>(jsonInput, headers);

	        //API Call
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
            
            return response.getBody();
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Error: " + e.getMessage());
	        return "Error";
	    }
	}
	
	@GetMapping("/order/{partnerOrderId}")
	public String checkOrder(@PathVariable String partnerOrderId, Model model) {
		try {
			ApiConfig apiconfig = ApiConfig.getApiConfigSingleton();
			
			String authorization = getAuthorization();

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
            return response.getBody();
            
		} catch (Exception e) {
			e.printStackTrace();
//			model.addAttribute("responseBody", "API 요청 중 오류 발생: " + e.getMessage());
			return "API 요청 중 오류 발생: " + e.getMessage();
        }
	}
	
	@RequestMapping(value = "/price.do", method = RequestMethod.POST)
	public String CheckPrice(@RequestBody String jsonInput) {
		try {
        	final String API_ENDPOINT = apiConfig.getHostURL() + "/api/v2/orders/price";
            final String VENDOR_ID = apiConfig.getVendorID();
            
            final String authorization = getAuthorization();
            
            // HTTP Header 설정
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            headers.set("Authorization", authorization);
            headers.set("vendor", VENDOR_ID);

            // HTTP 요청 생성
            HttpEntity<String> requestEntity = new HttpEntity<>(jsonInput, headers);
            
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
            
            return response.getBody();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
            
            return "Error: " + e.getMessage();
        }
	}

	
	@RequestMapping(value = "/patch/{partnerOrderId}.do", method = RequestMethod.PATCH)
	public String patchOrder(@PathVariable String partnerOrderId, @RequestBody String jsonInput) {
		try {
        	final String API_ENDPOINT = apiConfig.getHostURL() + "/api/v1/developers/orders/" + partnerOrderId + "/status";
            final String VENDOR_ID = apiConfig.getVendorID();
            
            final String authorization = getAuthorization();
            
            // HTTP Header 설정
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            headers.set("Authorization", authorization);
            headers.set("vendor", VENDOR_ID);
            
            System.out.println("authorization : " + authorization);

            // HTTP 요청 생성
            HttpEntity<String> requestEntity = new HttpEntity<>(jsonInput, headers);
            
            // API 호출
            String response = null;
            try {
                response = restTemplate.patchForObject(API_ENDPOINT, requestEntity, String.class);

                System.out.println("Response: " + response);
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
            
            return response;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
            
            return "Error: " + e.getMessage();
        }
	}
}
