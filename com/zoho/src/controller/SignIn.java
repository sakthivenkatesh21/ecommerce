package zohoincubation.com.zoho.ecommerce.src.controller;

import java.util.List;

import zohoincubation.com.zoho.ecommerce.src.model.User;

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
