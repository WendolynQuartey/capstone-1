package com.pluralsight.dto;

import com.pluralsight.model.Transaction;
import com.pluralsight.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private Long userId;
    private String userName;
    private String email;
    private List<Transaction> transactions;
    private String message;
}
