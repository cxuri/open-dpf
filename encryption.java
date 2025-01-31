/**
 * Created by github.com/cxuri
 * 
 * Java Implementation of AES-256 Encryption Algorithm, designed for use in Open Data Protected Format (ODPF).
 * This implementation provides methods for encrypting and decrypting data using AES in CBC mode with PKCS5Padding.
 * The encryption key is derived using PBKDF2 with HmacSHA256 to provide secure key management.
 * 
 * Features:
 * - AES-256 encryption (AES with a 256-bit key) in CBC (Cipher Block Chaining) mode.
 * - Secure key derivation using PBKDF2 with a salt and a passphrase (instead of using a static key).
 * - Data is encrypted and decrypted with the same passphrase and salt.
 * - Supports the use of a randomly generated initialization vector (IV) for each encryption to ensure security.
 * 
 * Encryption Process:
 * 1. Generate a random 16-byte IV for each encryption operation.
 * 2. Derive a 256-bit secret key using PBKDF2 with a passphrase and salt.
 * 3. Encrypt data using AES in CBC mode with PKCS5 padding.
 * 4. Prepend the IV to the encrypted data before returning it, so it can be used for decryption.
 * 
 * Decryption Process:
 * 1. Extract the IV from the beginning of the encrypted data.
 * 2. Use the same passphrase and salt to derive the same secret key.
 * 3. Decrypt the data using AES in CBC mode with the extracted IV.
 * 
 * This class can be used to securely encrypt and decrypt sensitive data, ensuring that the data remains protected during storage or transmission.
 * 
 * Notes:
 * - Make sure to replace `KEY` and `SALT` with secure values before using this in production.
 * - This implementation assumes that the encrypted data is Base64-encoded for safe transmission over text-based protocols.
 * - The IV is stored with the encrypted data to ensure it can be used for decryption.
 *
 */


import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class encryption {

    public static String encrypt(String data ,String KEY ,String SALT) {
        try {
            byte[] iv = new byte[16];  // AES block size (128-bit)
            new SecureRandom().nextBytes(iv);
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(KEY.toCharArray(), SALT.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);

            byte[] encryptedData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            byte[] encryptedDataWithIv = new byte[iv.length + encryptedData.length];

            System.arraycopy(iv, 0, encryptedDataWithIv, 0, iv.length);
            System.arraycopy(encryptedData, 0, encryptedDataWithIv, iv.length, encryptedData.length);

            return Base64.getEncoder().encodeToString(encryptedDataWithIv);
        } catch (Exception e) {
            e.printStackTrace();
            return null;  // Return null in case of an error
        }
    }

    public static String generate(int length) {
        final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(ALPHA_NUMERIC_STRING.length());
            sb.append(ALPHA_NUMERIC_STRING.charAt(randomIndex));
        }
        
        return sb.toString();
    }

    public static String decrypt(String encryptedData, String KEY, String SALT) {
        try {
            byte[] encryptedDataWithIv = Base64.getDecoder().decode(encryptedData);
            
            byte[] iv = new byte[16];
            System.arraycopy(encryptedDataWithIv, 0, iv, 0, iv.length);
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            byte[] encryptedDataWithoutIv = new byte[encryptedDataWithIv.length - iv.length];
            System.arraycopy(encryptedDataWithIv, iv.length, encryptedDataWithoutIv, 0, encryptedDataWithoutIv.length);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(KEY.toCharArray(), SALT.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);

            byte[] decryptedData = cipher.doFinal(encryptedDataWithoutIv);
            return new String(decryptedData, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;  // Return null in case of an error
        }
    }
}
