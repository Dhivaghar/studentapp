package com.example.studentapp.utils;

public class Validator {

    public static boolean isValidEmail(String email) {
        return email.contains("@");
    }

    public static boolean isValidName(String name) {
        return name.length() >= 2;
    }
}