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
}
