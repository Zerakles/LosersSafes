package losers.club.classes;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Passwort {

    /**
     *
     *  Passwort Class
     *  Erstelle ein Passwort und lasse es verschlüsseln.
     *
     */

    private final String password;
    private SecretKey secretKey;

    public Passwort(String password){
        long seed = 233222;
        try {
            this.password = cryptPassword(password, seed);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    private  String cryptPassword(String cryptPassword, long seed) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(seed);
        keyGenerator.init(secureRandom);
        this.secretKey = keyGenerator.generateKey();
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(cryptPassword.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    private String unCryptPassword( )throws InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] encryptedBytes = Base64.getDecoder().decode(this.password);
        return new String (cipher.doFinal(encryptedBytes));
    }

    public String getPassword(){
        try {
            return unCryptPassword();
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException |
                 IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        }
    }

    public String getCryptPassword(){
        return this.password;
    }

}
