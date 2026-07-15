package com.pluralsight;

import com.pluralsight.model.Transaction;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;

public class Transactions {
    private final ArrayList<Transaction> transactions;

    public Transactions() {
        this.transactions = FileManger.loadTransactions();
        sortByDateTimeDesc();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        FileManger.saveTransaction(transaction);
        sortByDateTimeDesc();
    }

    public ArrayList<Transaction> getAllTransactions() {
        return transactions;
    }

    public ArrayList<Transaction> getDeposits() {
        ArrayList<Transaction> deposits = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getAmount() > 0) {
                deposits.add(t);
            }
        }
        return deposits;
    }

    public ArrayList<Transaction> getPayments() {
        ArrayList<Transaction> payments = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getAmount() < 0) {
                payments.add(t);
            }
        }
        return payments;
    }

    public ArrayList<Transaction> getMonthToDate() {
        LocalDate today = LocalDate.now();
        ArrayList<Transaction> results = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getDate().getMonthValue() == today.getMonthValue()
                    && t.getDate().getYear() == today.getYear()) {
                results.add(t);
            }
        }
        return results;
    }

    public ArrayList<Transaction> getPreviousMonth() {
        LocalDate today = LocalDate.now();
        LocalDate previousMonth = today.minusMonths(1);
        ArrayList<Transaction> results = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getDate().getMonthValue() == previousMonth.getMonthValue()
                    && t.getDate().getYear() == previousMonth.getYear()) {
                results.add(t);
            }
        }
        return results;
    }

    public ArrayList<Transaction> getYearToDate() {
        LocalDate today = LocalDate.now();
        ArrayList<Transaction> results = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getDate().getYear() == today.getYear() && !t.getDate().isAfter(today)) {
                results.add(t);
            }
        }
        return results;
    }

    public ArrayList<Transaction> getPreviousYear() {
        int previousYear = LocalDate.now().getYear() - 1;
        ArrayList<Transaction> results = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getDate().getYear() == previousYear) {
                results.add(t);
            }
        }
        return results;
    }

    public ArrayList<Transaction> getByVendor(String vendor) {
        ArrayList<Transaction> results = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getVendor().equalsIgnoreCase(vendor)) {
                results.add(t);
            }
        }
        return results;
    }

    public ArrayList<Transaction> getByCustomFilters(String startDate,String endDate, String description, String vendor, String amount) {
        ArrayList<Transaction> filteredTransactions = new ArrayList<>();

        for (Transaction currentTransaction : transactions) {
            filteredTransactions.add(currentTransaction);
        }
        //If start date is blank, then print output
        if (startDate.isBlank()) {
            System.out.println("No start date applied");
        } else {
            try {
                //Turn the string into a local date to be able to use for comparing in a condition
                LocalDate.parse(startDate);
                filteredTransactions = filterByStartDate(filteredTransactions, startDate);
            } catch (DateTimeParseException e) {
                System.err.println("Invalid start date. Skipping start date filter.");
            }
        }
        if (endDate.isBlank()) {
            System.out.println("No end date applied");
        } else {
            try {
                LocalDate.parse(endDate);
                filteredTransactions = filterByEndDate(filteredTransactions, endDate);
            } catch (DateTimeParseException e) {
                System.err.println("Invalid end date. Skipping end date filter.");
            }
        }

        if (amount.isBlank()) {
            System.out.println("No amount applied");
        } else {
            try {
                Double.parseDouble(amount);
                filteredTransactions = filterByAmount(filteredTransactions, amount);
            } catch (NumberFormatException e) {
                System.err.println("Invalid amount. Skipping amount filter.");
            }
        }

        if (description.isBlank()) {
            System.out.println("No description applied");
        } else {
            filteredTransactions = filterByDescription(filteredTransactions, description);
        }

        if (vendor.isBlank()) {
            System.out.println("No vendor applied");
        } else {
            filteredTransactions = filterByVendor(filteredTransactions, vendor);
        }

        return filteredTransactions;
    }

    private static ArrayList<Transaction> filterByVendor(ArrayList<Transaction> transactionsToFilter, String targetVendor) {
        ArrayList<Transaction> filteredResults = new ArrayList<>();
        for (Transaction transaction : transactionsToFilter) {
            if (targetVendor.equalsIgnoreCase(transaction.getVendor())) {
                filteredResults.add(transaction);
            }
        }
        return filteredResults;
    }

    private static ArrayList<Transaction> filterByAmount(ArrayList<Transaction> transactionsToFilter, String amountInput) {
        ArrayList<Transaction> filteredResults = new ArrayList<>();
        double targetAmount = Double.parseDouble(amountInput);
        for (Transaction transaction : transactionsToFilter) {
            if (Double.compare(targetAmount, transaction.getAmount()) == 0) {
                filteredResults.add(transaction);
            }
        }
        return filteredResults;
    }

    private static ArrayList<Transaction> filterByDescription(ArrayList<Transaction> transactionsToFilter, String targetDescription) {
        ArrayList<Transaction> filteredResults = new ArrayList<>();
        for (Transaction transaction : transactionsToFilter) {
            if (targetDescription.equalsIgnoreCase(transaction.getDescription())) {
                filteredResults.add(transaction);
            }
        }
        return filteredResults;
    }

    private static ArrayList<Transaction> filterByEndDate(ArrayList<Transaction> transactionsToFilter, String dateInput) {
        ArrayList<Transaction> filteredResults = new ArrayList<>();
        LocalDate endDateBoundary = LocalDate.parse(dateInput);
        for (Transaction transaction : transactionsToFilter) {
            if (!endDateBoundary.isBefore(transaction.getDate())) {
                filteredResults.add(transaction);
            }
        }
        return filteredResults;
    }

    private static ArrayList<Transaction> filterByStartDate(ArrayList<Transaction> transactionsToFilter, String dateInput) {
        ArrayList<Transaction> filteredResults = new ArrayList<>();
        LocalDate startDateBoundary = LocalDate.parse(dateInput);
        for (Transaction transaction : transactionsToFilter) {
            if (!startDateBoundary.isAfter(transaction.getDate())) {
                filteredResults.add(transaction);
            }
        }
        return filteredResults;
    }

    private void sortByDateTimeDesc() {
        Comparator<Transaction> comparator = Comparator.comparing(Transaction::getDate)
                .thenComparing(Transaction::getTime);
        transactions.sort(comparator.reversed());
    }


}
