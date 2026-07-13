package com.pluralsight;

import com.pluralsight.model.Transaction;

import java.io.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

// Comment for Sheku
// comment for Pat

public class BankApp {
    public static Scanner scanner = new Scanner(System.in);
    public static ArrayList<Transaction> transactions = new ArrayList<>();
    public static final String FILE_NAME = "src/main/resources/transactions.csv";
    //Changer formatter to use correct pattern(before was using H:mm instead of HH:mm:ss
    public static final DateTimeFormatter INPUT_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static void main(String[] args) {
        homeScreen();
    }

    public static void homeScreen() {
        boolean running = true;
        do {
            System.out.print("""
                    \n╔═══════════════════╗
                    ║  \u001B[1;33m==== HOME ====\u001B[0m   ║
                    ╠═══════════════════╣
                    ║ \u001B[32m♦ D\u001B[0m  Add Deposit  ║
                    ║ \u001B[31m♦ P\u001B[0m  Make Payment ║
                    ║ \u001B[34m♦ L\u001B[0m  Ledger       ║
                    ║ \u001B[35m♦ X\u001B[0m  Exit         ║
                    ╚═══════════════════╝
                    \u001B[36m?\u001B[0m What would you like to do: \s""");


            String userSelection = scanner.nextLine();

            switch (userSelection) {
                // checks for user selection with case insensitivity
                case "D", "d": {
                    addDeposit();
                    break;
                }
                case "P", "p": {
                    makePayment();
                    break;
                }
                case "L", "l":
                    displayLedger();
                    break;
                case "X", "x": {
                    running = false;
                    break;
                }
                default:
                    System.out.println("That is not an option");
            }
        } while (running);
    }

    public static void addDeposit() {
        String newLine;
        try {
            BufferedWriter buffWriter = new BufferedWriter(new FileWriter(FILE_NAME, true));

            //collects all user info
            System.out.print("\nWhere is this deposit from? ");
            String depositVendor = scanner.nextLine();

            System.out.print("\nWhat is this deposit for? ");
            String depositDesc = scanner.nextLine();

            System.out.print("\nWhat date was this deposit made?(yyyy-MM-dd) ");
            String userInputString = scanner.nextLine();
            LocalDate userDate = LocalDate.parse(userInputString);

            System.out.print("\nWhat time was deposit made?(HH:mm:ss) ");
            userInputString = scanner.nextLine();
            LocalTime userTime = LocalTime.parse(userInputString, INPUT_TIME_FORMATTER);

            System.out.print("\nHow much was deposited? $");
            double depositAmount = scanner.nextDouble();
            scanner.nextLine();


            // creates a new instance of the transaction object with they user info filling out the fields
            Transaction newTransaction = new Transaction(userDate, userTime, depositDesc, depositVendor, depositAmount);

            newLine = newTransaction.displayTransaction();
            buffWriter.write(newLine);
            buffWriter.flush();
            buffWriter.close();

        } catch (FileNotFoundException e) {
            System.err.println("File cannot be located: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
//
//    public static Transaction getTransactionFromUser() {
//        return null;
//    }
//
//    public static void appendTransaction(Transaction t, String fileName) {
//
//    }

    public static void makePayment() {
        String newLine;
        try {
            BufferedWriter buffWriter = new BufferedWriter(new FileWriter(FILE_NAME, true));
            System.out.print("\nWhere is this payment going? ");
            String paymentVendor = scanner.nextLine();

            System.out.print("\nWhat is this payment for? ");
            String paymentDesc = scanner.nextLine();

            System.out.print("\nWhat date was this deposit made?(yyyy-MM-dd) ");
            String userInputString = scanner.nextLine();
            LocalDate userDate = LocalDate.parse(userInputString);

            System.out.print("\nWhat time was deposit made?(HH:mm:ss) ");
            userInputString = scanner.nextLine();
            LocalTime userTime = LocalTime.parse(userInputString, INPUT_TIME_FORMATTER);

            System.out.print("\nHow much was withdrawn? $");
            double paymentAmount = scanner.nextDouble();
            scanner.nextLine();

            Transaction newTransaction = new Transaction(userDate, userTime, paymentDesc, paymentVendor, -paymentAmount);

            newLine = newTransaction.displayTransaction();
            buffWriter.write(newLine);
            buffWriter.close();

        } catch (FileNotFoundException e) {
            System.err.println("File cannot be located!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void displayLedger() {
        try {
            boolean running = true;
            do {
                BufferedReader buffReader = new BufferedReader(new FileReader(FILE_NAME));

                buffReader.readLine();

                System.out.print("""
                        \n╔═══════════════════╗
                        ║ \u001B[1;33m==== LEDGER ====\u001B[0m  ║
                        ╠═══════════════════╣
                        ║ \u001B[32m♦ A\u001B[0m  All Entries  ║
                        ║ \u001B[31m♦ D\u001B[0m  Deposits     ║
                        ║ \u001B[34m♦ P\u001B[0m  Payments     ║
                        ║ \u001B[35m♦ R\u001B[0m  Reports      ║
                        ║ \u001B[36m♦ H\u001B[0m  Home         ║
                        ╚═══════════════════╝
                        \u001B[36m?\u001B[0m What would you like to do: \s""");

                String userChoice = scanner.nextLine();
                String fileLine;

                while ((fileLine = buffReader.readLine()) != null) {
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

                // Compares the Transaction objects in my transactions array list by their dates and then their times
                Comparator<Transaction> transactionComparator = Comparator.comparing(Transaction::getTime).thenComparing(Transaction::getDate);
                transactions.sort(transactionComparator.reversed());

                switch (userChoice) {
                    case "A", "a":
                        for (Transaction t : transactions) {
                            System.out.println(t.displayTransaction());
                        }
                        break;
                    case "D", "d":
                        for (Transaction t : transactions) {
                            if (t.getAmount() > 0) {
                                System.out.println(t.displayTransaction());
                            }
                        }
                        break;
                    case "P", "p":
                        for (Transaction t : transactions) {
                            if (t.getAmount() < 0) {
                                System.out.println(t.displayTransaction());
                            }
                        }
                        break;
                    case "R", "r":
                        displayReports();
                        break;
                    case "H", "h":
                        running = false;
                        break;
                    default:
                        System.out.println("That's not an option");
                }
                buffReader.close();
            } while (running);
        } catch (FileNotFoundException e) {
            System.err.println("File cannot be located!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void displayReports() {
        boolean running = true;
        do {

            System.out.print("""
                    \n╔═════════════════════════╗
                    ║     \u001B[1;33m==== REPORTS ====\u001B[0m   ║
                    ╠═════════════════════════╣
                    ║ \u001B[32m♦ 1\u001B[0m  Month To Date      ║
                    ║ \u001B[31m♦ 2\u001B[0m  Previous Month     ║
                    ║ \u001B[34m♦ 3\u001B[0m  Year To Date       ║
                    ║ \u001B[35m♦ 4\u001B[0m  Previous Year      ║
                    ║ \u001B[36m♦ 5\u001B[0m  Search by Vendor   ║
                    ║ \u001B[36m♦  6\u001B[0m  Custom Search     ║
                    ║ \u001B[37m♦ 0\u001B[0m  Back               ║
                    ╚═════════════════════════╝
                    \u001B[36m?\u001B[0m What would you like to do: \s""");

            int reportChoice = scanner.nextInt();
            scanner.nextLine();

            switch (reportChoice) {
                case 1:
                    for (Transaction t : transactions) {
                        LocalDate today = LocalDate.now();
                        int thisMonth = today.getMonthValue();
                        int thisYear = today.getYear();
                        if (t.getDate().getMonthValue() == thisMonth && t.getDate().getYear() == thisYear) {
                            System.out.println(t.displayTransaction());
                        }
                    }
                    break;
                case 2:
                    for (Transaction t : transactions) {
                        LocalDate today = LocalDate.now();
                        int lastMonth = today.getMonthValue() - 1;
                        int thisYear = today.getYear();
                        if (t.getDate().getMonthValue() == lastMonth && t.getDate().getYear() == thisYear) {
                            System.out.println(t.displayTransaction());
                        }
                    }
                    break;
                case 3:
                    for (Transaction t : transactions) {
                        LocalDate today = LocalDate.now();
                        int thisYear = today.getYear();
                        if (t.getDate().getYear() == thisYear && t.getDate().isBefore(today)) {
                            System.out.println(t.displayTransaction());
                        }
                    }
                    break;
                case 4:
                    for (Transaction t : transactions) {
                        LocalDate today = LocalDate.now();
                        int lastYear = today.getYear() - 1;
                        if (t.getDate().getYear() == lastYear) {
                            System.out.println(t.displayTransaction());
                        }
                    }
                    break;
                case 5:
                    System.out.print("\nWhich vendor would you like to search? ");
                    String searchVendor = scanner.nextLine();

                    for (Transaction t : transactions) {
                        if (t.getVendor().equalsIgnoreCase(searchVendor)) {
                            System.out.println(t.displayTransaction());
                        }
                    }
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("This is not an option");
            }
        } while (running);
    }
}