package com.pluralsight;

import java.io.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class BankApp {
    public static Scanner scanner = new Scanner(System.in);
    public static ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    // public static DateTimeFormatter inputDateFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");
    public static DateTimeFormatter inputTimeFormatter = DateTimeFormatter.ofPattern("H:mm");
    public static LocalDate userDate;
    public static LocalTime userTime;
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
                running = false;
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

            System.out.print("\nWhat date was this deposit made?(yyyy-MM-dd) ");
            String depositDate = scanner.nextLine();

            userDate = LocalDate.parse(depositDate);

            System.out.print("\nWhat time was deposit made?(HH:mm:ss) ");
            String depositTime = scanner.nextLine();

            userTime = LocalTime.parse(depositTime, inputTimeFormatter);

            System.out.print("\nHow much was deposited? $");
            double depositAmount = scanner.nextDouble();

            // creates a new instance of the transaction object with they user info filling out the fields
            Transaction newTransaction = new Transaction(userDate, userTime, depositDesc, depositVendor, depositAmount);

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

            System.out.print("\nWhat date was this deposit made?(yyyy-MM-dd) ");
            String paymentDate = scanner.nextLine();

            userDate = LocalDate.parse(paymentDate);

            System.out.print("\nWhat time was deposit made?(HH:mm) ");
            String paymentTime = scanner.nextLine();

            userTime = LocalTime.parse(paymentTime, inputTimeFormatter);

            System.out.print("\nHow much was withdrawn? $");
            double paymentAmount = scanner.nextDouble();

            Transaction newTransaction = new Transaction(userDate, userTime, paymentDesc, paymentVendor, -paymentAmount);

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
                case "D", "d":
                    for (Transaction t : transactions){
                        if (t.getAmount() > 0) {
                            System.out.println(t.displayTransaction());
                        }
                    }
                    break;
                case "P", "p":
                    for (Transaction t : transactions){
                        if (t.getAmount() < 0) {
                            System.out.println(t.displayTransaction());
                        }
                    }
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
