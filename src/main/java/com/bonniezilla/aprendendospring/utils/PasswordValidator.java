package com.bonniezilla.aprendendospring.utils;

public class PasswordValidator {


    /**
     *
     * @param password to be validated
     * @return true if the password attends requirements, else false
     */
    public static boolean isStrongPassword(String password) {
        String strongPasswordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^a-zA-Z\\d]).{8,}$";
        if (password == null ) return false;
        if (password.length() < 8) return false;
        return (password).matches(strongPasswordRegex);
    }

    public static void validatePassword(String password) {
        if (!isStrongPassword(password)) {
           throw new IllegalArgumentException(
                   "Password must contain at least one uppercase letter, one number, one special character and has at least of 8 digits"
           );
        }
    }
}


