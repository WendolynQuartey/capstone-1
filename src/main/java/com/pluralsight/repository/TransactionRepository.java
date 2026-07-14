package com.pluralsight.repository;

import com.pluralsight.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserId(Long userId);
    List<Transaction> findByUserIdAndDate(Long userId, LocalDate date);
    List<Transaction> findByUserIdAndVendor(Long userId, String vendor);
    List<Transaction> findByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);
}
