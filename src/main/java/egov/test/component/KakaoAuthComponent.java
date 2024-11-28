package egov.test.component;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.springframework.stereotype.Component;

@Component
public class KakaoAuthComponent {

	public String generateSignature(String timestamp, String nonce, String apiKey)
            throws NoSuchAlgorithmException, InvalidKeyException {
        String plainText = timestamp + nonce + apiKey;
        return signatureSHA512(plainText);
    }

    private String signatureSHA512(String plainText)
            throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(plainText.getBytes());
        return String.format("%0128x", new BigInteger(1, md.digest()));
    }

    public String generateAuthorization(String timestamp, String nonce, String sign) {
        String authValue = timestamp + "$$" + nonce + "$$" + sign;
        return Base64.getEncoder().encodeToString(authValue.getBytes());
    }
    public String signature(final String timestamp, final String
    		nonce, final String apiKey)
    		      throws InvalidKeyException, NoSuchAlgorithmException {
	    final String plainText = timestamp + nonce + apiKey;
	    return signatureSHA512(plainText);
	}
}