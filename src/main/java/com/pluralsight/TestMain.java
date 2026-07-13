package com.pluralsight;

import com.pluralsight.model.Transaction;

import java.util.ArrayList;
// this is a test Main you can check you Funtions/class/method here
public class TestMain {
    public static void main(String[] args) {
        ArrayList<Transaction> transactions = FileManger.loadTransactions();
        System.out.println();
    }
}
