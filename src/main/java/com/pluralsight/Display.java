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

public class Display {

    public static void homeScreen() {
        boolean running = true;
        do {

            System.out.println(MenuStrings.HOME_MENU);
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
    public static void displayLedger() {
        try {
            boolean running = true;
            do {
                BufferedReader buffReader = new BufferedReader(new FileReader(FILE_NAME));

                buffReader.readLine();

                System.out.println(MenuStrings.LEDGER_MENU);

                String userChoice = scanner.nextLine();
                String fileLine;
                transactions.clear();
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
                        pause();
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

    //pause after a transaction to allow user time to read
    static void pause() {
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
