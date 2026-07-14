package com.pluralsight;

import com.pluralsight.model.MenuStrings;
import com.pluralsight.model.Transaction;

import java.io.*;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

// Comment for Sheku
// comment for Pat

public class BankApp {
    public static Scanner scanner = new Scanner(System.in);
    public static ArrayList<Transaction> transactions = new ArrayList<>();
    public static final String FILE_NAME = "src/main/resources/transactions.csv";
    //Changer formatter to use correct pattern(before was using H:mm instead of HH:mm:ss
    public static final DateTimeFormatter INPUT_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static void main(String[] args) {
    }

    public static void addDeposit() {
            //collects all user info
            System.out.print("\nWhere is this deposit from? ");
            String depositVendor = scanner.nextLine();

            System.out.print("\nWhat is this deposit for? ");
            String depositDesc = scanner.nextLine();

            System.out.print("\nWhat date was this deposit made?(yyyy-MM-dd) ");
            String userInputString = scanner.nextLine();
            LocalDate userDate = LocalDate.parse(userInputString);

            System.out.print("\nWhat time was deposit made?(HH:mm:ss) ");
            userInputString = scanner.nextLine();
            LocalTime userTime = LocalTime.parse(userInputString, INPUT_TIME_FORMATTER);

            System.out.print("\nHow much was deposited? $");
            double depositAmount = scanner.nextDouble();
            scanner.nextLine();



            // creates a new instance of the transaction object with they user info filling out the fields
            Transaction newTransaction = new Transaction(userDate, userTime, depositDesc, depositVendor, depositAmount);

            FileManger.saveTransaction(newTransaction);
    }


    public static void makePayment() {
            System.out.print("\nWhere is this payment going? ");
            String paymentVendor = scanner.nextLine();

            System.out.print("\nWhat is this payment for? ");
            String paymentDesc = scanner.nextLine();

            System.out.print("\nWhat date was this payment made?(yyyy-MM-dd) ");
            String userInputString = scanner.nextLine();
            LocalDate userDate = LocalDate.parse(userInputString);

            System.out.print("\nWhat time was payment made?(HH:mm:ss) ");
            userInputString = scanner.nextLine();
            LocalTime userTime = LocalTime.parse(userInputString, INPUT_TIME_FORMATTER);

            System.out.print("\nHow much was withdrawn? $");
            double paymentAmount = scanner.nextDouble();
            scanner.nextLine();

            Transaction newTransaction = new Transaction(userDate, userTime, paymentDesc, paymentVendor, -paymentAmount);

            FileManger.saveTransaction(newTransaction);
    }






}