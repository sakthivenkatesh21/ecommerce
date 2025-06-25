package com.zoho.src.interfaceController;

import com.zoho.src.model.User;
import java.util.Scanner;

public interface Execute {
    String getfunctionName();
    void operation(Scanner sc,User loggedInUser);
}


