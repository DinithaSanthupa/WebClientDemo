package cbc.webclient.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Token {
    public void generateToken() {
        String sharedKey = "Com-c";
        String secretKey = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String message = sharedKey;

        try {
            // Getting an HMAC-SHA-256 Mac instance
            Mac hmacSha256 = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
            hmacSha256.init(secretKeySpec);

            // Generating the token
            byte[] hashBytes = hmacSha256.doFinal(message.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            String token = hexString.toString();
            System.out.println("Token: " + token);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }
}
