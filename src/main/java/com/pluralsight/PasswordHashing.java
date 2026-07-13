package com.pluralsight;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHashing {

    // Hash a plain text password
    public static String hashPassword(String plainPassword) {
        // 12 is the work factor (cost) — higher is more secure but slower
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    // Verify a password against a stored hash
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

//    public static void main(String[] args) {
//        String password = "MySecureP@ssw0rd";
//
//        // Hash the password
//        String hashed = hashPassword(password);
//        System.out.println("Hashed password: " + hashed);
//
//        // Verify the password
//        boolean match = checkPassword("MySecureP@ssw0rd", hashed);
//        System.out.println("Password match: " + match);
//    }
}