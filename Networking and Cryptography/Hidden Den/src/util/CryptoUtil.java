package util;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by Nathan Purpura on 4/5/2016.
 */
public class CryptoUtil {

    //The encrypting cipher
    private static Cipher encipher;

    //The decrypting cipher
    private static Cipher decipher;

    //The password hasher
    private static MessageDigest digest;

    //The stored keyspec derived from the password
    private static SecretKeySpec secretKey;

    //Stores the state of initialization
    private static boolean initialized = false;

    static {
        try {
            encipher = Cipher.getInstance("AES");
            decipher = Cipher.getInstance("AES");
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the Crypto Utilities with the given password. This <B>must be called before <u>ANY</u> encryption or decryption</B>
     *
     * @param password the password that you want to use
     */
    public static void init(String password) {
        secretKey = generateKey(password);
        try {
            encipher.init(Cipher.ENCRYPT_MODE, secretKey);
            decipher.init(Cipher.DECRYPT_MODE, secretKey);
            initialized = true;
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            initialized = false;
        }
    }


    /**
     * Encrypts the message with the previously provided password
     *
     * @param message the message that you want to encrypt
     * @return the encrypted binary blob or null if not initialized
     */
    public static byte[] encrypt(String message) {
        if (isInitialized()) {
            try {
                byte[] output = encipher.doFinal(message.getBytes("UTF-8"));
                return output;
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    /**
     * Decrypts the supplied binary blob into the original message as a string
     *
     * @param encrypted the encrypted binary blob
     * @return the original message or null if not initialized
     */
    public static String decrypt(byte[] encrypted) {
        if (isInitialized()) {
            try {
                byte[] output = decipher.doFinal(encrypted);
                return new String(output, "UTF-8");
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    private static SecretKeySpec generateKey(String passcode)
    {
        try
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] digestedKey = digest.digest(passcode.getBytes("UTF-8"));
            SecretKeySpec spec = new SecretKeySpec(Arrays.copyOf(digestedKey,16),"AES");
            return spec;

        }catch(Exception e)
        {
            return null;
        }
    }

    /**
     * Gets whether the Utility has been initialized with a password or not
     *
     * @return true if initialized, false if not
     */
    public static boolean isInitialized() {
        return initialized;
    }



}
