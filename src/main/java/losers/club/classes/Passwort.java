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

    /**
     * Erstellt ein neues Passwort und einen SecretKey
     * @param password
     */
    public Passwort(String password){
        long seed = 233222;
        try {
            this.password = cryptPassword(password, seed);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Verwendet einen Seed um das erstellte Passwort zu verschlüsseln und einen SecretKey zu erstellen.
     * @param cryptPassword
     * @param seed
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
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

    /**
     * Diese Methode entschlüsselt das Password und gibt es zurück.
     * @return Password
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     */
    private String unCryptPassword( )throws InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] encryptedBytes = Base64.getDecoder().decode(this.password);
        return new String (cipher.doFinal(encryptedBytes));
    }

    /**
     * Diese Methode gibt das Passwort zurück.
     * @return
     */
    public String getPassword(){
        try {
            return unCryptPassword();
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException |
                 IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * DIese Methode gibt das verschlüsselte Passwort zurück.
     * @return
     */
    public String getCryptPassword(){
        return this.password;
    }

}
