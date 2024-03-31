package cbc.webclient.util;

import cbc.webclient.serviceImpl.WebClientServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Token {
    Logger logger = LoggerFactory.getLogger(Token.class);
    public String generateToken() {
        String sharedKey = "Com-c";
        String secretKey = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String message = sharedKey;
        String token = "";

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
            token = hexString.toString();
            logger.info("Token: " + token);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            logger.info("Exception " + e.getMessage());
        }
        return token;
    }
}
