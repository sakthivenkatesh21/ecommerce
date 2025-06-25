package com.zoho.src.controller;

import java.util.List;
import com.zoho.src.model.Client;
import com.zoho.src.model.Seller;
import com.zoho.src.model.User;

public class UserController {
    private static int idGenerator;
    private static final List<User> userList = DataManager.getDataManager().getUser();
    
// creation of Client(Customer)
    public static User createUser(String name, String phone, String email, String password, String gender, String address) {
        User user = new Client(++idGenerator, name, phone, email, password, gender, address);
        userList.add(user);
        return user;
    }
// creation of seller
    public static User createUser(String name, String phone, String email, String password, String gender, String company, String companyAddress) {
        User user = new Seller(++idGenerator, name, phone, email, password, gender, company, companyAddress);
        userList.add(user);
        return user;
    }
    // checking duplicate mail exists
    public static boolean isMailExists(String email,User loggedInUser) {
        for (User user : userList) {
            if (user.getEmail().equals(email) && ( loggedInUser != null && !user.getEmail().equals(loggedInUser.getEmail()))) {
                return true;
            }
        }
        return false;
    }
    // checking duplicate phone number exists
    public static boolean isPhoneExists(String phone,User loggedInUser) {

        for (User user : userList) {
            if (user.getPhone().equals(phone) && (loggedInUser != null && !user.getPhone().equals(loggedInUser.getPhone()))) {
                return true;
            }
        }
        return false;
    }
}
