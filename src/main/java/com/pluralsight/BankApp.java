package com.pluralsight;

import java.io.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.time.LocalDateTime;

public class BankApp {
    public static Scanner scanner = new Scanner(System.in);
    public static LocalTime timeNow = LocalTime.now();
    public static LocalDate dateNow = LocalDate.now();
    public static ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    public static void main(String[] args) {
        boolean running = true;

        do {
            homeScreen();
        } while (running);

    }

    public static void homeScreen() {
        boolean running = true;
        System.out.print("""
                \n=====HOME=====
                D-Add Deposit
                P-Make Payment (Debit)
                L-Ledger
                X-Exit
                What would you like to do:\s""");

        String userSelection = scanner.nextLine();

        switch (userSelection){
            // checks for user selection with case insensitivity
            case "D", "d":
                addDeposit();
                break;
            case "P", "p":
                makePayment();
                break;
            case "L", "l":
                displayLedger();
                break;
            case "X", "x":
                break;
            default:
                System.out.println("That is not an option");
                break;
        }
    }

    public static void addDeposit(){
        String newLine;
        try {
            BufferedWriter buffWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv",true));

            //collects all user info
            System.out.print("\nWhere is this deposit from? ");
            String depositVendor = scanner.nextLine();

            System.out.print("\nWhat is this deposit for? ");
            String depositDesc = scanner.nextLine();

            System.out.print("\nHow much was deposited? $");
            double depositAmount = scanner.nextDouble();

            // creates a new instance of the transaction object with they user info filling out the fields
            Transaction newTransaction = new Transaction(dateNow, timeNow, depositDesc, depositVendor, depositAmount);

            newLine = newTransaction.displayTransaction();
            buffWriter.write(newLine);
            buffWriter.close();

        } catch (FileNotFoundException e){
            System.err.println("File cannot be located!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void makePayment(){
        String newLine;
        try {
            BufferedWriter buffWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv", true));
            System.out.print("\nWhere is this payment going? ");
            String paymentVendor = scanner.nextLine();

            System.out.print("\nWhat is this payment for? ");
            String paymentDesc = scanner.nextLine();

            System.out.print("\nHow much was withdrawn? $");
            double paymentAmount = scanner.nextDouble();

            Transaction newTransaction = new Transaction(dateNow, timeNow, paymentDesc, paymentVendor, -paymentAmount);

            newLine = newTransaction.displayTransaction();
            buffWriter.write(newLine);
            buffWriter.close();

        } catch (FileNotFoundException e){
            System.err.println("File cannot be located!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void displayLedger() {
        try {
            BufferedReader buffReader = new BufferedReader(new FileReader("src/main/resources/transactions.csv"));

            buffReader.readLine();

            System.out.print("""
                    \n=====LEDGER=====
                    A-All Entries
                    D-Deposits
                    P-Payments
                    R-Reports
                    H-Home
                    What would you like to do:\s""");


            String userChoice = scanner.nextLine();

            String fileLine;

            while((fileLine = buffReader.readLine()) != null){
                String[] transactionInfo = fileLine.split("\\|");
                Transaction newTransaction = new Transaction(
                        LocalDate.parse(transactionInfo[0]),
                        LocalTime.parse(transactionInfo[1]),
                        transactionInfo[2],
                        transactionInfo[3],
                        Double.parseDouble(transactionInfo[4])
                );
                transactions.add(newTransaction);
            }

            switch (userChoice) {
                case "A", "a":
                    sortList(transactions);
                    break;
            }
            buffReader.close();
        } catch (FileNotFoundException e){
            System.err.println("File cannot be located!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sortList(ArrayList<Transaction> transactions){
        Comparator<Transaction> transactionComparator = Comparator.comparing(Transaction::getDate).thenComparing(Transaction::getTime);
        transactions.sort(transactionComparator.reversed());

        for (Transaction t : transactions){
            System.out.println(t.displayTransaction());
        }
    }
}
