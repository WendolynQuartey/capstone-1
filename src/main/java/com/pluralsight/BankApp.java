package com.pluralsight;

import java.io.*;
import java.util.Scanner;
import java.time.LocalDateTime;

public class BankApp {
    public static Scanner scanner = new Scanner(System.in);
    //public static BufferedReader buffReader = new BufferedReader( new FileReader("src/main/resourced/transactions.csv"));
    public static LocalDateTime rightNow = LocalDateTime.now();
    public static void main(String[] args) {
       boolean running = true;

        do {
            homeScreen();
        } while (running);

    }

    public static void homeScreen() {
        System.out.print("""
                =====HOME=====
                D-Add Deposit
                P-Make Payment (Debit)
                L-Ledger
                X-Exit
                What would you like to do:\s""");

        String userSelection = scanner.nextLine();

        switch (userSelection){
            // checks for user selection with case insensitivity
            case "D", "d":
                String newLine;
                try {
                    BufferedWriter buffWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv"));
                    System.out.print("\nWhere is this deposit from? ");
                    String depositVendor = scanner.nextLine();

                    System.out.print("\nWhat was this deposit for? ");
                    String depositDesc = scanner.nextLine();

                    System.out.print("\nHow much was deposited? $");
                    double depositAmount = scanner.nextDouble();

                    Transaction newTransaction = new Transaction(rightNow, rightNow, depositDesc, depositVendor, depositAmount);

                    newLine = newTransaction.displayTransaction();

                    buffWriter.write(newLine);
                    buffWriter.close();

                } catch (FileNotFoundException e){
                    System.err.println("File cannot be located!");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                break;
            case "P", "p":
                break;
            case "L", "l":
                break;
            case "X", "x":
                break;
        }
    }
}
