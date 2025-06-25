package com.zoho.src.controller;

import com.zoho.src.model.User;
import java.util.List;

public class SignIn {

    private static List<User> user = DataManager.getDataManager().getUser();
    private static boolean isValidUser = false;

    public static User validateLogIn(String email, String password) {
        for (User data : user) {
            if (data.getEmail().equals(email) && data.getPassword().equals(password)) {
                isValidUser = true;
                return data;
            }
        }
        return null;
    }

    public boolean isValidUser() {
        return isValidUser;
    }
}
