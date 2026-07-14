package com.pluralsight;

import com.pluralsight.model.MenuStrings;
import com.pluralsight.model.Transaction;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.Scanner;

public class Display {
    private static final Scanner scanner = new Scanner(System.in);
    //Todo add an instance of Transactions

    public static void homeScreen() {
        boolean running = true;

        do {
            System.out.println(MenuStrings.HOME_MENU);
            String userSelection = scanner.nextLine();

            switch (userSelection.toUpperCase()) {
                // checks for user selection with case insensitivity
                case "D": {
                    //AddDeposit method from transactions method
//                    addDeposit();
                    break;
                }
                case "P": {
                    //MakePayment method from payment method
//                    makePayment();
                    break;
                }
                case "L":
                    displayLedger();
                    break;
                case "X": {
                    running = false;
                    break;
                }
                default:
                    System.out.println("That is not an option");
            }
        } while (running);
    }


    public static void displayLedger() {
        boolean running = true;
//        try {
        //Todo: add to transactions class
//
//
//                BufferedReader buffReader = new BufferedReader(new FileReader(FILE_NAME));
//
//                buffReader.readLine();
//
//
//                String fileLine;
//                transactions.clear();
//                while ((fileLine = buffReader.readLine()) != null) {
//                    String[] transactionInfo = fileLine.split("\\|");
//                    Transaction newTransaction = new Transaction(
//                            LocalDate.parse(transactionInfo[0]),
//                            LocalTime.parse(transactionInfo[1]),
//                            transactionInfo[2],
//                            transactionInfo[3],
//                            Double.parseDouble(transactionInfo[4])
//                    );
//                    transactions.add(newTransaction);
//                }
//
//                // Compares the Transaction objects in my transactions array list by their dates and then their times
//                Comparator<Transaction> transactionComparator = Comparator.comparing(Transaction::getTime).thenComparing(Transaction::getDate);
//                transactions.sort(transactionComparator.reversed());
        do {
            System.out.println(MenuStrings.LEDGER_MENU);
            String userChoice = scanner.nextLine();

            switch (userChoice.toUpperCase()) {
                case "A":
//                for (Transaction t : transactions) {
//                    System.out.println(t.displayTransaction());
//                }
//                pause();
                    break;
                case "D":
//                for (Transaction t : transactions) {
//                    if (t.getAmount() > 0) {
//                        System.out.println(t.displayTransaction());
//                    }
//                }
                    break;
                case "P":
//                for (Transaction t : transactions) {
//                    if (t.getAmount() < 0) {
//                        System.out.println(t.displayTransaction());
//                    }
//                }
                    break;
                case "R":
                    displayReports();
                    break;
                case "H":
                    running = false;
                    break;
                default:
                    System.out.println("That's not an option");
            }
//        buffReader.close();
        } while (running);

//        } catch (FileNotFoundException e) {
//            System.err.println("File cannot be located!");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    //pause after a transaction to allow user time to read
    public static void pause() {
        String input = "";
        while (!input.equalsIgnoreCase("B")) {
            System.out.println();
            System.out.print("Enter B to go back: ");
            input = scanner.nextLine().trim();
        }
    }

    public static void displayReports() {
        boolean running = true;

        do {

            System.out.println(MenuStrings.REPORTS_MENU);

            int reportChoice = scanner.nextInt();
            scanner.nextLine();

            switch (reportChoice) {
                case 1:
//                    for (Transaction t : transactions) {
//                        LocalDate today = LocalDate.now();
//                        int thisMonth = today.getMonthValue();
//                        int thisYear = today.getYear();
//                        if (t.getDate().getMonthValue() == thisMonth && t.getDate().getYear() == thisYear) {
//                            System.out.println(t.displayTransaction());
//                        }
//                    }
                    break;
                case 2:
//                    for (Transaction t : transactions) {
//                        LocalDate today = LocalDate.now();
//                        int lastMonth = today.getMonthValue() - 1;
//                        int thisYear = today.getYear();
//                        if (t.getDate().getMonthValue() == lastMonth && t.getDate().getYear() == thisYear) {
//                            System.out.println(t.displayTransaction());
//                        }
//                    }
                    break;
                case 3:
//                    for (Transaction t : transactions) {
//                        LocalDate today = LocalDate.now();
//                        int thisYear = today.getYear();
//                        if (t.getDate().getYear() == thisYear && t.getDate().isBefore(today)) {
//                            System.out.println(t.displayTransaction());
//                        }
//                    }
                    break;
                case 4:
//                    for (Transaction t : transactions) {
//                        LocalDate today = LocalDate.now();
//                        int lastYear = today.getYear() - 1;
//                        if (t.getDate().getYear() == lastYear) {
//                            System.out.println(t.displayTransaction());
//                        }
//                    }
                    break;
                case 5:
                    System.out.print("\nWhich vendor would you like to search? ");
                    String searchVendor = scanner.nextLine();

//                    for (Transaction t : transactions) {
//                        if (t.getVendor().equalsIgnoreCase(searchVendor)) {
//                            System.out.println(t.displayTransaction());
//                        }
//                    }
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
