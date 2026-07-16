package com.pluralsight.controller;

import com.pluralsight.model.Transaction;
import com.pluralsight.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TransactionController {
    
    private final TransactionService transactionService;
    
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        try {
            Transaction createdTransaction = transactionService.createTransaction(transaction);
            return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/{transactionId}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long transactionId) {
        Optional<Transaction> transaction = transactionService.getTransactionById(transactionId);
        return transaction.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Transaction>> searchTransactions(
            @RequestParam Long userId,
            @RequestParam(required = false) String vendor,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String reportType) {
        List<Transaction> transactions = transactionService.searchTransactions(userId, vendor, startDate, endDate, type, reportType);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Transaction>> getTransactionsByUserId(@PathVariable Long userId) {
        List<Transaction> transactions = transactionService.getTransactionsByUserId(userId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
    
    @GetMapping("/user/{userId}/date/{date}")
    public ResponseEntity<List<Transaction>> getTransactionsByUserIdAndDate(@PathVariable Long userId, @PathVariable LocalDate date) {
        List<Transaction> transactions = transactionService.getTransactionsByUserIdAndDate(userId, date);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
    
    @GetMapping("/user/{userId}/vendor/{vendor}")
    public ResponseEntity<List<Transaction>> getTransactionsByUserIdAndVendor(@PathVariable Long userId, @PathVariable String vendor) {
        List<Transaction> transactions = transactionService.getTransactionsByUserIdAndVendor(userId, vendor);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
    
    @GetMapping("/user/{userId}/date-range")
    public ResponseEntity<List<Transaction>> getTransactionsByUserIdAndDateRange(
            @PathVariable Long userId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        List<Transaction> transactions = transactionService.getTransactionsByUserIdAndDateRange(userId, startDate, endDate);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
    
    @GetMapping("/user/{userId}/month-to-date")
    public ResponseEntity<List<Transaction>> getMonthToDateTransactions(@PathVariable Long userId) {
        List<Transaction> transactions = transactionService.getMonthToDateTransactions(userId, LocalDate.now());
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
    
    @GetMapping("/user/{userId}/year-to-date")
    public ResponseEntity<List<Transaction>> getYearToDateTransactions(@PathVariable Long userId) {
        List<Transaction> transactions = transactionService.getYearToDateTransactions(userId, LocalDate.now());
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
    
    @PutMapping("/{transactionId}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long transactionId, @RequestBody Transaction updatedTransaction) {
        try {
            Transaction transaction = transactionService.updateTransaction(transactionId, updatedTransaction);
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long transactionId) {
        try {
            transactionService.deleteTransaction(transactionId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
