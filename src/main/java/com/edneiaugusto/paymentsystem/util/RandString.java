package com.edneiaugusto.paymentsystem.util;

import java.security.SecureRandom;

public class RandString {

    public static final String caracters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String generateRandomString(int length) {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = secureRandom.nextInt(caracters.length());
            sb.append(caracters.charAt(index));
        }
        return sb.toString();
    }

}
