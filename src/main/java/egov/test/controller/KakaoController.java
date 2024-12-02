package egov.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import egov.test.component.KakaoAuthComponent;
import egov.test.config.ApiConfig;

@Controller
public class KakaoController {
	
	@Autowired
	private KakaoAuthComponent kakaoAuthComponent;
	
	private ApiConfig apiConfig;
	
	private RestTemplate restTemplate = new RestTemplate();
	
	@RequestMapping("/quickHome.do")
	public String quickHome() {
		return "/quickHome";
	}
	
	@RequestMapping("/test")
	@GetMapping("/{name}")
	public String testFunction(@PathVariable String name) {
		String returnValue = "test return value RESTAPI: " + name;
		
		return returnValue;
	}
	
	@RequestMapping(value = "/hello.do")
	public String hello(Model model) {
		apiConfig = ApiConfig.getApiConfigSingleton();
		String authorization = null;
		String apiResponse = null;  // API 응답을 저장할 변수
		try {
            // 입력값
            final String timestamp = String.valueOf(System.currentTimeMillis());
            final String nonce = "121212";
            final String apiKey = apiConfig.getKeyValue();
            System.out.println("tesT");

            // 서명 및 Authorization 생성
            String sign = kakaoAuthComponent.generateSignature(timestamp, nonce, apiKey);
            authorization = kakaoAuthComponent.generateAuthorization(timestamp, nonce, sign);

            // Authorization 값 확인
            System.out.println("Authorization: " + authorization);
            
            // API 호출을 위한 URL 설정
            String apiUrl = apiConfig.getHostURL();

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
}