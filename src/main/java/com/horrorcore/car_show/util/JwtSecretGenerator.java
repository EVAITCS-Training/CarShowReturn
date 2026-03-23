package com.horrorcore.car_show.util;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

/**
 * Utility to generate a secure HMAC secret key for JWT usage.
 *
 * Usage:
 *   java -cp target/classes com.horrorcore.car_show.util.JwtSecretGenerator [keySize]
 * or (when running from IDE) run as a normal Java application.
 *
 * The program prints a Base64-encoded secret suitable to place in application yaml as:
 * jwt:
 *   secret: <base64-secret>
 */
public class JwtSecretGenerator {
    public static void main(String[] args) throws Exception {
        int keySize = 256; // default 256 bits
        if (args.length > 0) {
            try {
                keySize = Integer.parseInt(args[0]);
            } catch (NumberFormatException ignored) {
                System.err.println("Invalid key size provided, using default 256 bits.");
            }
        }

        // Generate an HMAC-SHA256 key
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
        keyGen.init(keySize);
        SecretKey secretKey = keyGen.generateKey();

        String base64 = Base64.getEncoder().encodeToString(secretKey.getEncoded());

        System.out.println("# Generated HMAC-SHA256 secret (Base64) - length bits: " + (secretKey.getEncoded().length * 8));
        System.out.println(base64);
        System.out.println("\n# YAML snippet to paste into src/main/resources/application-dev.yaml or application.yaml:");
        System.out.println("# jwt:\n#   secret: " + base64);
    }
}

