package com.tutores.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    private static final int ROUNDS = 12;

    public static String hash(String plainText) {
        return BCrypt.hashpw(plainText, BCrypt.gensalt(ROUNDS));
    }

    public static boolean verify(String plainText, String hashedPassword) {
        try {
            return BCrypt.checkpw(plainText, hashedPassword);
        } catch (Exception e) {
            return false;
        }
    }
}
