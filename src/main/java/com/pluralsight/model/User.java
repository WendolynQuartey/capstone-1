package com.pluralsight.model;

public class User {
    private Long userId;
    private String userName;
    private String email;
    private String hashed_Password;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashed_Password() {
        return hashed_Password;
    }

    public void setHashed_Password(String hashed_Password) {
        this.hashed_Password = hashed_Password;
    }
}
