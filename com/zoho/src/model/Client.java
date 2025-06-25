package com.zoho.src.model;

import com.zoho.src.interfaceController.Execute;
import com.zoho.src.view.CategoryHelper;
import com.zoho.src.view.OrderHelper;
import com.zoho.src.view.ProductHelper;
import com.zoho.src.view.UserHelper;
import com.zoho.src.view.WishlistHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client extends User {
    
    private Card card;
    private String address;
    private List<Order> previousOrderProduct;
    private final int CUSTOMER = 1;
    
    
    public Client() {}

    public Client(int id, String name,String phone, String email, String password, String gender, String address) {
        super(id, name, phone, email, password, gender);
        this.card = new Card();
        this.address = address;
        previousOrderProduct = new ArrayList<>();
        
    }
    private void list(Scanner sc , User loggedInUser){
        this.operations = new Execute[]{
            new UserHelper(loggedInUser, sc),
            new CategoryHelper(sc, loggedInUser),
            new ProductHelper(sc, loggedInUser),
            new WishlistHandler(sc,loggedInUser),
            new OrderHelper(sc, loggedInUser),
        };
    }
// list of action  done by user 
    @Override
    public Execute[] getOperations(Scanner sc, User loggedInUser) {
        if (operations == null) {
            list(sc, loggedInUser);
        }
        return operations;
    }

    public Card getcard() {
        return card;
    }

    public void setcard(Card card) {
        this.card = card;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Order> getPreviousOrderProduct() {
        return previousOrderProduct;
    }

    public void setPreviousOrderProduct(List<Order> previousOrderProduct) {
        this.previousOrderProduct = previousOrderProduct;
    }

    public int getRole(){
        return CUSTOMER;
    }
    
    @Override
    public String toString(){
        // System.out.println("Client ID : " + getId());
        System.out.println("Client Name : " + getName());
        System.out.println("Client Phone : " + getPhone());
        System.out.println("Client Email : " + getEmail());
        System.out.println("Address : " + address);
        
        return "";
    }
}
