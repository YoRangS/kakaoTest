package egov.test.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApiConfig {
	
	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Value("${api.key}")
    private String apiKey;
    
    @Value("${api.url.auth.check}")
    private String apiUrlAuthCheck;
    
    @Value("${api.vendor}")
    private String vendor;

    public String getApiKey() {
        return apiKey;
    }
    
    public String getApiUrlAuthCheck() {
    	return apiUrlAuthCheck;
    }
    
    public String getVendor() {
    	return vendor;
    }
}