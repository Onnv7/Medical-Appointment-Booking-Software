package com.example.doctorapp.Utils;

public class CheckTextInput {
    public static boolean isValidEmail(String email) {

        String regex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        System.out.println(email.matches(regex));
        return email.matches(regex);
    }

}
