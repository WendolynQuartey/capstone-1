package com.pluralsight;

import com.pluralsight.model.Transaction;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
// this is a test Main you can check you Funtions/class/method here
public class TestMain {
    public static void main(String[] args) {
    Transaction transaction = new Transaction(LocalDate.now(), LocalTime.now(),"testSave","testSave",100);
    FileManger.saveTransaction(transaction);
    }
}
