package zohoincubation.com.zoho.ecommerce.src.controller;

import java.util.List;
import zohoincubation.com.zoho.ecommerce.src.model.Client;
import zohoincubation.com.zoho.ecommerce.src.model.Seller;
import zohoincubation.com.zoho.ecommerce.src.model.User;

public class UserController {
    private static int idGenerator;
    private static final List<User> userList = DataManager.getDataManager().getUser();

    public static User createUser(String name, String phone, String email, String password, String gender, String address) {
        User user = new Client(++idGenerator, name, phone, email, password, gender, address);
        userList.add(user);
        return user;
    }

    public static User createUser(String name, String phone, String email, String password, String gender, String company, String companyAddress) {
        User user = new Seller(++idGenerator, name, phone, email, password, gender, company, companyAddress);
        userList.add(user);
        return user;
    }
    public static boolean isMailExists(String email,User loggedInUser) {
        for (User user : userList) {
            if (user.getEmail().equals(email) && ( loggedInUser != null && !user.getEmail().equals(loggedInUser.getEmail()))) {
                return true;
            }
        }
        return false;
    }
    public static boolean isPhoneExists(String phone,User loggedInUser) {

        for (User user : userList) {
            if (user.getPhone().equals(phone) && (loggedInUser != null && !user.getPhone().equals(loggedInUser.getPhone()))) {
                return true;
            }
        }
        return false;
    }
}
