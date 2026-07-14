package com.pluralsight.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    
    @Column(nullable = false)
    private Long userId;
    
    @Column(nullable = false)
    private LocalDate date;
    
    @Column(nullable = false)
    private LocalTime time;
    
    @Column(nullable = false)
    private String description;
    
    @Column(nullable = false)
    private String vendor;
    
    @Column(nullable = false)
    private double amount;

    public Transaction(LocalDate date, LocalTime time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    public String displayTransaction(){
        return String.format("%tF|%tT|%s|%s|%.2f%n",
               this.date, this.time, this.description, this.vendor, this.amount);
    }
}
