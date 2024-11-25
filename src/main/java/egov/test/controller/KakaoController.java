package egov.test.controller;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;

import egov.test.component.KakaoAuthComponent;
import egov.test.config.ApiConfig;
import reactor.core.publisher.Mono;

@Controller
public class KakaoController {
	
	private final WebClient webClient;
	
	private final KakaoAuthComponent kakaoAuthComponent;
	
	private final ApiConfig apiConfig;
	
	@Autowired
    public KakaoController(WebClient.Builder webClientBuilder, KakaoAuthComponent kakaoAuthComponent, ApiConfig apiConfig) {
		this.kakaoAuthComponent = kakaoAuthComponent;
        this.apiConfig = apiConfig;
        this.webClient = webClientBuilder.baseUrl(apiConfig.getApiUrlAuthCheck()).build();
    }
	
	@RequestMapping(value = "/hello.do")
	public String hello(Model model) {
		String authorization = null;
		try {
            // 입력값
            final String timestamp = String.valueOf(System.currentTimeMillis());
            final String nonce = "121212";
            final String apiKey = apiConfig.getApiKey();

            // 서명 및 Authorization 생성
            String sign = kakaoAuthComponent.generateSignature(timestamp, nonce, apiKey);
            authorization = kakaoAuthComponent.generateAuthorization(timestamp, nonce, sign);

            // Authorization 값 확인
            System.out.println("Authorization: " + authorization);

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
		
		return webClient.get()
                .uri("") // 기본 URL을 설정했으므로 빈 URI로 호출
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, authorization)
                .header("vendor", apiConfig.getVendor())
                .retrieve()
                .bodyToMono(String.class) // 응답을 String으로 변환
                .onErrorResume(e -> {
                    e.printStackTrace();
                    return Mono.just("Error occurred: " + e.getMessage());
                })
                .doOnTerminate(() -> {
                    // Mono의 값을 처리한 후, Model에 값 추가
                    model.addAttribute("response", "Response Data Here");
                }).block(); // 결과를 동기적으로 받아오기 위해 block()
	}
	
	
}