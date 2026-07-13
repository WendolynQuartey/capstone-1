package com.pluralsight;

import com.pluralsight.model.Transaction;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
// this is a test Main you can check you Funtions/class/method here
public class TestMain {
    public static void main(String[] args) {
        String password = PasswordHashing.hashPassword("password");
        String password2 = PasswordHashing.hashPassword("password");
        System.out.println(password);
        System.out.println(password2);

        System.out.println(PasswordHashing.checkPassword("password","$2a$12$lobDNkTmQLkEWV3kkNQpQu9FQIevOcofYizE6UyAR8NksVxi5LhJO"));
        System.out.println(PasswordHashing.checkPassword("password","$2a$12$TMaM/3ISapXfKQPAyG1BAORRxh2SdchmHqUzuSqHDw9lHCvfx5csO"));

    }
}
