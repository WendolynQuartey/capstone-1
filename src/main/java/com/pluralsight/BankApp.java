package com.pluralsight;

import java.util.Scanner;

public class BankApp {
    Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        homeScreen();
    }

    public static void homeScreen(){
        System.out.print("""
                =====HOME=====
                D-Add Deposit
                P-Make Payment (Debit)
                L-Ledger
                X-Exit
                What would you like to do:\s""");
    }
}
