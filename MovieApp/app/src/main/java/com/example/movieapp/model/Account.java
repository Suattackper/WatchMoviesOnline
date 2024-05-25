package com.example.movieapp.model;

import java.util.List;
import com.example.movieapp.model.AccountList;

public class Account {
    private int AccountCode;
    private String Email;
    private List<AccountList> AccountList;
    private String Name;
    private String Password;
    private String PhoneNumber;
    private String Image;

    public Account() {
    }

    public List<AccountList> getAccountList() {
        return AccountList;
    }

    public void setAccountList(List<AccountList> accountList) {
        AccountList = accountList;
    }

    public int getAccountCode() {
        return AccountCode;
    }

    public void setAccountCode(int accountCode) {
        AccountCode = accountCode;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
