package com.pluralsight;

import com.pluralsight.model.MenuStrings;
import com.pluralsight.model.Transaction;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Display {

    private final Scanner scanner;
    private final Transactions transactions;

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("MM/dd/yyyy");

    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm:ss");

    public Display(Transactions transactions) {
        this.scanner = new Scanner(System.in);
        this.transactions = transactions;
    }

    public void homeScreen() {
        boolean running = true;

        while (running) {
            System.out.println(MenuStrings.HOME_MENU);
            System.out.print("Select an option: ");

            String userSelection = scanner.nextLine().trim();

            switch (userSelection.toUpperCase()) {
                case "D":
                    addDeposit();
                    break;

                case "P":
                    makePayment();
                    break;

                case "L":
                    displayLedger();
                    break;

                case "X":
                    running = false;
                    System.out.println(
                            "\nThank you for using the Bank Ledger System."
                    );
                    break;

                default:
                    System.out.println(
                            "\nInvalid option. Please select D, P, L, or X."
                    );
            }
        }
    }

    private void addDeposit() {
        System.out.println("\n==============================");
        System.out.println("         NEW DEPOSIT");
        System.out.println("==============================");

        Transaction deposit = getTransactionFromUser("Deposit");

        transactions.addTransaction(deposit);

        System.out.println("\nDeposit completed successfully.");
        System.out.printf(
                "Amount deposited: $%,.2f%n",
                deposit.getAmount()
        );
    }

    private void makePayment() {
        System.out.println("\n==============================");
        System.out.println("         NEW PAYMENT");
        System.out.println("==============================");

        Transaction payment = getTransactionFromUser("Payment");

        // Payments must be stored as negative amounts.
        if (payment.getAmount() > 0) {
            payment.setAmount(-payment.getAmount());
        }

        transactions.addTransaction(payment);

        System.out.println("\nPayment recorded successfully.");
        System.out.printf(
                "Payment amount: $%,.2f%n",
                Math.abs(payment.getAmount())
        );
    }

    private Transaction getTransactionFromUser(
            String transactionType
    ) {
        LocalDate date = readTransactionDate(transactionType);
        LocalTime time = readTransactionTime(transactionType);

        String description;
        String vendor;

        if (transactionType.equalsIgnoreCase("Deposit")) {
            System.out.print(
                    "Enter the deposit description "
                            + "(Payroll, Cash Deposit, Transfer): "
            );
            description = readRequiredString();

            System.out.print(
                    "Enter the source of funds "
                            + "(Employer, Bank, Customer): "
            );
            vendor = readRequiredString();
        } else {
            System.out.print(
                    "Enter the payment description "
                            + "(Rent, Utilities, Groceries): "
            );
            description = readRequiredString();

            System.out.print(
                    "Enter the payee or merchant name: "
            );
            vendor = readRequiredString();
        }

        double amount = readValidatedAmount(transactionType);

        return new Transaction(
                date,
                time,
                description,
                vendor,
                amount
        );
    }

    private LocalDate readTransactionDate(
            String transactionType
    ) {
        while (true) {
            System.out.printf(
                    "Enter the %s date (MM/dd/yyyy): ",
                    transactionType.toLowerCase()
            );

            String input = scanner.nextLine().trim();

            try {
                return LocalDate.parse(
                        input,
                        DATE_FORMATTER
                );
            } catch (DateTimeParseException e) {
                System.out.println(
                        "Invalid date. Please use MM/dd/yyyy."
                );
            }
        }
    }

    private LocalTime readTransactionTime(
            String transactionType
    ) {
        while (true) {
            System.out.printf(
                    "Enter the %s time (HH:mm:ss): ",
                    transactionType.toLowerCase()
            );

            String input = scanner.nextLine().trim();

            try {
                return LocalTime.parse(
                        input,
                        TIME_FORMATTER
                );
            } catch (DateTimeParseException e) {
                System.out.println(
                        "Invalid time. Please use HH:mm:ss."
                );
            }
        }
    }

    private String readRequiredString() {
        while (true) {
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.print(
                    "This field cannot be empty. Please try again: "
            );
        }
    }

    private double readValidatedAmount(
            String transactionType
    ) {
        while (true) {
            System.out.printf(
                    "Enter the %s amount: $",
                    transactionType.toLowerCase()
            );

            String input = scanner.nextLine()
                    .trim()
                    .replace("$", "")
                    .replace(",", "");

            try {
                double amount = Double.parseDouble(input);

                if (amount <= 0) {
                    System.out.println(
                            "The amount must be greater than zero."
                    );
                    continue;
                }

                return amount;
            } catch (NumberFormatException e) {
                System.out.println(
                        "Invalid amount. Please enter a valid number."
                );
            }
        }
    }

    public void displayLedger() {
        boolean running = true;

        while (running) {
            System.out.println(MenuStrings.LEDGER_MENU);
            System.out.print("Select a ledger option: ");

            String userChoice = scanner.nextLine().trim();

            switch (userChoice.toUpperCase()) {
                case "A":
                    System.out.println(
                            "\n===== ALL ACCOUNT TRANSACTIONS ====="
                    );

                    printTransactions(
                            transactions.getAllTransactions()
                    );
                    pause();
                    break;

                case "D":
                    System.out.println(
                            "\n===== ACCOUNT DEPOSITS ====="
                    );

                    printTransactions(
                            transactions.getDeposits()
                    );
                    pause();
                    break;

                case "P":
                    System.out.println(
                            "\n===== ACCOUNT PAYMENTS ====="
                    );

                    printTransactions(
                            transactions.getPayments()
                    );
                    pause();
                    break;

                case "R":
                    displayReports();
                    break;

                case "H":
                    running = false;
                    break;

                default:
                    System.out.println(
                            "Invalid option. Please select "
                                    + "A, D, P, R, or H."
                    );
            }
        }
    }

    public void displayReports() {
        boolean running = true;

        while (running) {
            System.out.println(MenuStrings.REPORTS_MENU);
            System.out.print("Select a report option: ");

            String reportChoice = scanner.nextLine().trim();

            switch (reportChoice) {
                case "1":
                    System.out.println(
                            "\n===== MONTH-TO-DATE TRANSACTIONS ====="
                    );

                    printTransactions(
                            transactions.getMonthToDate()
                    );
                    pause();
                    break;

                case "2":
                    System.out.println(
                            "\n===== PREVIOUS MONTH TRANSACTIONS ====="
                    );

                    printTransactions(
                            transactions.getPreviousMonth()
                    );
                    pause();
                    break;

                case "3":
                    System.out.println(
                            "\n===== YEAR-TO-DATE TRANSACTIONS ====="
                    );

                    printTransactions(
                            transactions.getYearToDate()
                    );
                    pause();
                    break;

                case "4":
                    System.out.println(
                            "\n===== PREVIOUS YEAR TRANSACTIONS ====="
                    );

                    printTransactions(
                            transactions.getPreviousYear()
                    );
                    pause();
                    break;

                case "5":
                    searchByVendor();
                    break;
                case "6":
                    searchByCustomFilter();
                    break;

                case "0":
                    running = false;
                    break;

                default:
                    System.out.println(
                            "Invalid report option. "
                                    + "Please select 0 through 5."
                    );
            }
        }
    }

    private void searchByCustomFilter() {
        //ask for start date
        System.out.println("What is the start date? (yyyy-MM-dd)");
        String startDateInput = scanner.nextLine();
        //ask for end date
        System.out.println("What is the end date? (yyyy-MM-DD)");
        String endDateInput = scanner.nextLine();
        //ask for description
        System.out.println("What is the description?");
        String descriptionInput = scanner.nextLine();
        //ask for vendor
        System.out.println("What is the vendor?");
        String vendorInput = scanner.nextLine();
        //search for amount
        System.out.println("What is the amount?");
        String amountInput = scanner.nextLine();

        ArrayList<Transaction> results =
                transactions.getByCustomFilters(startDateInput,endDateInput,descriptionInput,vendorInput,amountInput);

        System.out.printf(
                "%nSearch results for \"%s\":%n",
                vendorInput
        );

        printTransactions(results);
        pause();
    }






    private void searchByVendor() {
        System.out.println(
                "\n===== PAYEE OR MERCHANT SEARCH ====="
        );

        System.out.print(
                "Enter the payee, merchant, or source name: "
        );

        String vendor = readRequiredString();

        ArrayList<Transaction> results =
                transactions.getByVendor(vendor);

        System.out.printf(
                "%nSearch results for \"%s\":%n",
                vendor
        );

        printTransactions(results);
        pause();
    }

    private void printTransactions(
            ArrayList<Transaction> transactionList
    ) {
        if (transactionList.isEmpty()) {
            System.out.println(
                    "No matching transactions were found."
            );
            return;
        }

        System.out.println(
                "Date|Time|Description|Payee/Source|Amount"
        );
        System.out.println(
                "------------------------------------------------------------"
        );

        for (Transaction transaction : transactionList) {
            System.out.print(
                    transaction.displayTransaction()
            );
        }
    }

    public void pause() {
        while (true) {
            System.out.println();
            System.out.print(
                    "Enter B to return to the previous menu: "
            );

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("B")) {
                return;
            }

            System.out.println(
                    "Invalid input. Please enter B to go back."
            );
        }
    }
}