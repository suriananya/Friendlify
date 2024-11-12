package api;

import java.util.Random;

public class Utility {
    public static final String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random random = new Random();

    public static String generateRandomString(int length) {
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = alphanumeric.charAt(random.nextInt(alphanumeric.length()));
        }
        return new String(text);
    }

    public static String sha256(String plain) {
        return "";
    }

    public static String base64encode(String plain) {
        return "";
    }

    public static String createCodeChallenge(String base64) {
        return "";
    }
}
