package com.zoho.src.controller;

import com.zoho.src.model.Card;
import com.zoho.src.model.CardProduct;
import com.zoho.src.model.Client;
import com.zoho.src.model.Order;
import com.zoho.src.model.OrderStatus;
import com.zoho.src.model.User;
import java.util.List;

public class OrderController {
   
    private static int  idGenerator;
    private static final List<Order> orders = DataManager.getDataManager().getOrders();

    public static boolean isOrderEmpty() {
        return orders.isEmpty();
    }

    public  static Order  createOrder(Card card, double amount,String payment, User loggedInUser) {
       
        Order order = new Order(++idGenerator,(Client)loggedInUser,((Client)loggedInUser).getAddress(),
                                    OrderStatus.CONFIRMED,amount,payment);
        updateSellerSales(card.getProduct());
        productOrder(order.getProduct(), card.getProduct());
        card.getProduct().clear();
        orders.add(order);
        
        ((Client)loggedInUser).getPreviousOrderProduct().add(order);
        return order;
        
    }
    private static void updateSellerSales(List<CardProduct> products) {
        for(CardProduct product : products) {
            product.setProducStatus(OrderStatus.CONFIRMED);
            product.getSeller().setSoldItem(product.getSeller().getSoldItem() + product.getQuantity());
            product.getSeller().setProfit(product.getSeller().getProfit() +(product.getPrice()*product.getQuantity()));
            product.getSeller().getSaledList().add(product);
        }
    }

    private  static List<CardProduct>  productOrder(List<CardProduct> orderProducts, List<CardProduct> cardProducts) {
        for (CardProduct product : cardProducts) {    
                orderProducts.add(product);
               
        }   
        return orderProducts;
    }

}
