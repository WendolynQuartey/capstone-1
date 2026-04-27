package com.pluralsight;

import java.sql.Time;
import java.util.Date;

public class Transaction {
    private Date dste;
    private Time time;
    private String description;
    private String vendor;
    private double amount;

    public Transaction(Date dste, Time time, String description, String vendor, double amount) {
        this.dste = dste;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }
    //region getters
    public Date getDste() {
        return dste;
    }

    public Time getTime() {
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
}
