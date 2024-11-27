package egov.test.config;

import org.springframework.stereotype.Component;

@Component
public class ApiConfig {
    private static ApiConfig apiConfig;
    public ApiConfig(){
        if(apiConfig != null) apiConfig = new ApiConfig();
        else return;
    }
    public static ApiConfig getApiConfigSingleton(){
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
    
    private String HostURL = ApiConfigValue.HostURL;
    public String getHostURL(){
    	System.out.println("URL : " + this.HostURL);
        return this.HostURL;
    }
}