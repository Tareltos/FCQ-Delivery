package by.tareltos.fcqdelivery.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class IdGenerator {
    private static Random random = new Random();
    private static String base = "";
    private static MessageDigest messageDigest;

    static {
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private IdGenerator() {
    }

    public static BigInteger generate() {
        base += (char) random.nextInt(127);
        byte[] bytesEncoded = {0};
        try {
            messageDigest.update(base.getBytes("utf8"));
            bytesEncoded = messageDigest.digest();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new BigInteger( 1, bytesEncoded);
    }
}
