package egov.test;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import egov.test.KakaoAPIKeySingleton;

import lombok.extern.slf4j.Slf4j;

public class KakaoSignKey {
	private KakaoAPIKeySingleton keys;
  final String timestamp = String.valueOf(System.currentTimeMillis());
  final String nonce = "121212";
  final String apiKey = keys.getKeyValue();//kakaoT ∙ API Sandbox API  
  
  final String sign = signature(timestamp, nonce, apiKey);

  static String signature(final String timestamp, final String nonce, final String apiKey)
      throws InvalidKeyException, NoSuchAlgorithmException {
    final String plainText = timestamp + nonce + apiKey;
    return HmacSignature.signatureSHA512(plainText);
  }

  static String signatureSHA512(final String plainText)
      throws NoSuchAlgorithmException, InvalidKeyException {

    final MessageDigest md = MessageDigest.getInstance("SHA-512");
    md.update(plainText.getBytes());
    return String.format("%0128x", new BigInteger(1,md.digest()));
  }

    
}
