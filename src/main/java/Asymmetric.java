import javax.crypto.Cipher;
import java.security.*;
import java.util.Scanner;

public class Asymmetric {

    private static final String RSA
            = "RSA";
    private static Scanner sc;

    // Generating public & private keys
    // using RSA algorithm.
    public static
    KeyPair generateRSAKkeyPair() throws Exception {
        SecureRandom secureRandom
                = new SecureRandom();
        KeyPairGenerator keyPairGenerator
                = KeyPairGenerator.getInstance(RSA);

        keyPairGenerator.initialize(
                2048, secureRandom);
        return keyPairGenerator
                .generateKeyPair();
    }

    // Encryption function which converts
    // the plainText into a cipherText
    // using private Key.
    public static byte[] do_RSAEncryption(String plainText, PrivateKey privateKey) throws Exception
    {
        Cipher cipher
                = Cipher.getInstance(RSA);

        cipher.init(
                Cipher.ENCRYPT_MODE, privateKey);

        return cipher.doFinal(
                plainText.getBytes());
    }

    // Decryption function which converts
    // the ciphertext back to the
    // original plaintext.
    public static String do_RSADecryption(byte[] cipherText, PublicKey publicKey) throws Exception
    {
        Cipher cipher
                = Cipher.getInstance(RSA);

        cipher.init(Cipher.DECRYPT_MODE,
                publicKey);
        byte[] result
                = cipher.doFinal(cipherText);

        return new String(result);
    }
}
