package ru.ponitkov.music.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HashPassword {
    private static final int SALT_LENGTH = 16; // длина соли
    private static final int ITERATIONS = 65536; // количество итераций
    private static final int KEY_LENGTH = 256; // длина ключа
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final Logger LOGGER = Logger.getLogger(HashPassword.class.getName());

    public static String hash(String password) {
        try {
            byte[] salt = generateSalt();
            byte[] hash = hashPassword(password, salt);

            String saltBase = Base64.getEncoder().encodeToString(salt);
            String hashBase = Base64.getEncoder().encodeToString(hash);

            return saltBase + ":" + hashBase;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            LOGGER.log(Level.SEVERE, "Ошибка при хешировании пароля: ", e);

            return null;
        }
    }

    public static boolean verify(String password, String hash) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String[] parts = hash.split(":");

        byte[] salt = Base64.getDecoder().decode(parts[0]);
        byte[] storedHash = Base64.getDecoder().decode(parts[1]);
        byte[] hashToVerify = hashPassword(password, salt);

        // Сравнение полученного хеша с сохраненным
        return MessageDigest.isEqual(storedHash, hashToVerify);
    }

    private static byte[] generateSalt() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstanceStrong();
        byte[] salt = new byte[SALT_LENGTH];

        random.nextBytes(salt);

        return salt;
    }

    private static byte[] hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);

        return factory.generateSecret(spec).getEncoded();
    }
}
