package egov.test;

public class KakaoAPIKeySingleton {
    private static KakaoAPIKeySingleton kakaoAPIKeySingleton;
    public KakaoAPIKeySingleton(){
        if(this.kakaoAPIKeySingleton != null) kakaoAPIKeySingleton = new KakaoAPIKeySingleton();
        else return;
    }
    public KakaoAPIKeySingleton getKakaoAPIKeySingleton(){
        return this.kakaoAPIKeySingleton;
    }

    /* singleton */

    private String kakaoAPIkeyValue = "9e7ee58f-3a2e-44bf-9da2-106cffbeeda6";
    public String getKeyValue(){
        return this.kakaoAPIkeyValue;
    }

    private String vendorID = "VZQS0W";
    public String getVendorID(){
        return this.vendorID;
    }

    
}
