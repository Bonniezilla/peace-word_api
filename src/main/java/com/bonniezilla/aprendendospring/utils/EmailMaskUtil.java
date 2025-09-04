package com.bonniezilla.aprendendospring.utils;

public class EmailMaskUtil {
    public static String maskEmail(String email) {
        if (email == null || !email.contains("@")) {
            return null;
        }

        String[] parts = email.split("@");
        String username = parts[0];
        String domain = parts[1];

        // Treating username
        String visibleUsername = username.length() <= 3
                ? username.charAt(0) + "**"
                : username.substring(0, 3);
        String maskedUsername = visibleUsername + "*".repeat(Math.max(0, username.length() - visibleUsername.length()));

        // Treating domain
        int lastDot = domain.lastIndexOf(".");
        String tld = lastDot != -1 ? domain.substring(lastDot) : "";
        String maskedDomain = "*".repeat(domain.length() - tld.length()) + tld;

        return maskedUsername + "@" + maskedDomain;
    }

    public static void main(String[] args) {
        System.out.println(maskEmail("testeemail@gmail.com"));
        System.out.println(maskEmail("userme@gmail.com"));
        System.out.println(maskEmail("crz@gmailook.com"));
    }
}
