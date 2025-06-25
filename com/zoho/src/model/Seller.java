package zohoincubation.com.zoho.ecommerce.src.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import zohoincubation.com.zoho.ecommerce.src.interfaceController.Execute;
import zohoincubation.com.zoho.ecommerce.src.view.CategoryHelper;
import zohoincubation.com.zoho.ecommerce.src.view.OrderHelper;
import zohoincubation.com.zoho.ecommerce.src.view.ProductHelper;
import zohoincubation.com.zoho.ecommerce.src.view.UserHelper;

public class Seller extends User {
    
    private int soldItem;
    private double profit;
    private String company;
    private String companyAddress;
    private List<Product> sellerProduct;
    private List<CardProduct> saledList; 
    private final int SELLER = 2;
    
    public Seller(){}

    public Seller(int id, String name,String phone, String email,
                    String password, String gender, String company, String companyAddress){
        super(id, name, phone, email, password, gender);
        this.soldItem = 0;
        this.profit = 0;
        this.company = company;
        this.companyAddress = companyAddress;
        this.sellerProduct = new ArrayList<>();
        this.saledList = new ArrayList<>();
    }
    
    private void list(Scanner sc, User loggedInUser){ 
        this.operations = new Execute[]{
            new UserHelper(loggedInUser, sc),
            new CategoryHelper(sc, loggedInUser),       
            new ProductHelper(sc, loggedInUser),
            new OrderHelper(sc, loggedInUser)
            
        };
    }
    @Override
    public Execute[] getOperations(Scanner sc, User loggedInUser) {
        if (this.operations == null) {
            list(sc,loggedInUser);
        }
        return this.operations;
    }

    public int getSoldItem() {
        return soldItem;
    }

    public void setSoldItem(int soldItem) {
        this.soldItem = soldItem;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public List<Product> getSellerProduct() {
        return sellerProduct;
    }

    public void setSellerProduct(List<Product> sellerProduct) {
        this.sellerProduct = sellerProduct;
    }

    public List<CardProduct> getSaledList() {
        return saledList;
    }

    public void setSaledList(List<CardProduct> saledList) {
        this.saledList = saledList;
    }

    @Override
    public int getRole(){
        return SELLER;
    }
    
    @Override
    public String toString() {
        return "Seller ID : " + getId() + "\n" +
               "Name : " + getName() + "\n" +
               "Phone : " + getPhone() + "\n" +
               "Email : " + getEmail() + "\n" +
               "Company : " + company + "\n" +
               "Company Address : " + companyAddress + "\n" +
               "Sold Items : " + soldItem + "\n" +
               "Profit : " + profit;
    }
}
