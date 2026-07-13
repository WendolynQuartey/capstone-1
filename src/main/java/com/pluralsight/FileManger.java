package com.pluralsight;

import com.pluralsight.model.Transaction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class FileManger {
    public static final String FILE_NAME = "src/main/resources/transactions.csv";
    public static ArrayList<Transaction> loadTransactions() {
        ArrayList<Transaction> oldTransactions = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(FILE_NAME);
            BufferedReader reader = new BufferedReader(fileReader);
            String header = "date|time|description|vendor|amount";
            String line;
            while ((line = reader.readLine()) != null) {
                // Handling the header if it exits
                if (!line.equalsIgnoreCase(header)) {
                    String[] lineSpilt = line.split("\\|");
                    String date = lineSpilt[0];
                    String time = lineSpilt[1];
                    String dateTimeString = date + "T" + time;
                    LocalDate date1 = LocalDate.parse(date);
                    LocalTime time1 = LocalTime.parse(time);
                    String description = lineSpilt[2];
                    String vendor = lineSpilt[3];
                    double amount = Double.parseDouble(lineSpilt[4]);
                    oldTransactions.add(new Transaction(date1, time1, description, vendor, amount));

                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("IO Exception: " + e);
        }
        return oldTransactions;
    }

}
