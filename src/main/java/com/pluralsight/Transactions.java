package com.pluralsight;

import com.pluralsight.model.Transaction;

import java.time.LocalDate;
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

    private void sortByDateTimeDesc() {
        Comparator<Transaction> comparator = Comparator.comparing(Transaction::getDate)
                .thenComparing(Transaction::getTime);
        transactions.sort(comparator.reversed());
    }
}
