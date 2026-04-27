package com.pluralsight;

import java.time.LocalDateTime;

public class Transaction {
    private LocalDateTime date;
    private LocalDateTime time;
    private String description;
    private String vendor;
    private double amount;

    public Transaction(LocalDateTime date, LocalDateTime time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    //region getters
    public LocalDateTime getDate() {
        return date;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public String getVendor() {
        return vendor;
    }

    public double getAmount() {
        return amount;
    }
    //endregion

    public String displayTransaction(){
        return String.format("%tF|%tT|%s|%s|$%.2f%n",
               this.date, this.time, this.description, this.vendor, this.amount);
    }
}
