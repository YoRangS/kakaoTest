package egov.test.config;

import org.springframework.stereotype.Component;

@Component
public class ApiConfig {
    private static ApiConfig apiConfig;
    private ApiConfig() {}
    public static ApiConfig getApiConfigSingleton(){
    	if(apiConfig == null) apiConfig = new ApiConfig();
        return apiConfig;
    }

    /* singleton */
    private String APIkeyValue = ApiConfigValue.APIKeyValue;
    public String getKeyValue(){
    	System.out.println("Key : " + this.APIkeyValue);
        return this.APIkeyValue;
    }

    private String vendorID = ApiConfigValue.venderID;
    public String getVendorID(){
    	System.out.println("Vendor : " + this.vendorID);
        return this.vendorID;
    }
    
    private String AuthURL = ApiConfigValue.AuthURL;
    public String getAuthURL() {
    	System.out.println("AuthURL : " + this.AuthURL);
        return this.AuthURL;
    }
    
    private String HostURL = ApiConfigValue.HostURL;
    public String getHostURL(){
    	System.out.println("HostURL : " + this.HostURL);
        return this.HostURL;
    }
}