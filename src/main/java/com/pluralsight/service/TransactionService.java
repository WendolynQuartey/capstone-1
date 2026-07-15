package com.pluralsight.service;

import com.pluralsight.model.Transaction;
import com.pluralsight.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService {
    
    private final TransactionRepository transactionRepository;
    
    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
    
    public Optional<Transaction> getTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId);
    }
    
    public List<Transaction> getTransactionsByUserId(Long userId) {
        return transactionRepository.findByUserId(userId);
    }
    
    public List<Transaction> getTransactionsByUserIdAndDate(Long userId, LocalDate date) {
        return transactionRepository.findByUserIdAndDate(userId, date);
    }
    
    public List<Transaction> getTransactionsByUserIdAndVendor(Long userId, String vendor) {
        return transactionRepository.findByUserIdAndVendor(userId, vendor);
    }
    
    public List<Transaction> getTransactionsByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        return transactionRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
    }
    
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
    
    public Transaction updateTransaction(Long transactionId, Transaction updatedTransaction) {
        return transactionRepository.findById(transactionId)
                .map(transaction -> {
                    transaction.setDate(updatedTransaction.getDate());
                    transaction.setTime(updatedTransaction.getTime());
                    transaction.setDescription(updatedTransaction.getDescription());
                    transaction.setVendor(updatedTransaction.getVendor());
                    transaction.setAmount(updatedTransaction.getAmount());
                    return transactionRepository.save(transaction);
                })
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found with id: " + transactionId));
    }
    
    public void deleteTransaction(Long transactionId) {
        transactionRepository.deleteById(transactionId);
    }
    
    public List<Transaction> getMonthToDateTransactions(Long userId, LocalDate currentDate) {
        LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);
        return getTransactionsByUserIdAndDateRange(userId, firstDayOfMonth, currentDate);
    }
    
    public List<Transaction> getYearToDateTransactions(Long userId, LocalDate currentDate) {
        LocalDate firstDayOfYear = currentDate.withDayOfYear(1);
        return getTransactionsByUserIdAndDateRange(userId, firstDayOfYear, currentDate);
    }

    public List<Transaction> searchTransactions(Long userId, String vendor, LocalDate startDate, LocalDate endDate, String type, String reportType) {
        LocalDate today = LocalDate.now();

        if (reportType != null && !reportType.isBlank()) {
            switch (reportType) {
                case "month-to-date" -> {
                    startDate = today.withDayOfMonth(1);
                    endDate = today;
                }
                case "previous-month" -> {
                    LocalDate lastMonthEnd = today.withDayOfMonth(1).minusDays(1);
                    startDate = lastMonthEnd.withDayOfMonth(1);
                    endDate = lastMonthEnd;
                }
                case "year-to-date" -> {
                    startDate = today.withDayOfYear(1);
                    endDate = today;
                }
                case "previous-year" -> {
                    LocalDate lastYearEnd = today.withDayOfYear(1).minusDays(1);
                    startDate = lastYearEnd.withDayOfYear(1);
                    endDate = lastYearEnd;
                }
            }
        }

        final LocalDate finalStartDate = startDate;
        final LocalDate finalEndDate = endDate;
        final String finalType = type == null ? "all" : type;

        return getTransactionsByUserId(userId).stream()
                .filter(t -> vendor == null || vendor.isBlank() || t.getVendor().toLowerCase().contains(vendor.toLowerCase()))
                .filter(t -> finalStartDate == null || !t.getDate().isBefore(finalStartDate))
                .filter(t -> finalEndDate == null || !t.getDate().isAfter(finalEndDate))
                .filter(t -> switch (finalType) {
                    case "deposits" -> t.getAmount() > 0;
                    case "payments" -> t.getAmount() < 0;
                    default -> true;
                })
                .toList();
    }
}
