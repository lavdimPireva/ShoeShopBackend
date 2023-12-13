package com.example.sneakershopapi.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class OrderCaptureRequest {

    private String orderID;
    private UserDetails userDetails;
    private List<CartItem> cartItems;
    private double subtotal;
    private double transportCost;

    // Inner class to model the user details
    @Getter
    @Setter
    @ToString
    public static class UserDetails {
        private String fullName;
        private String address;
        private String city;
        private String country;
        private String email;
    }

    // Inner class to model the cart items
    @Getter
    @Setter
    @ToString
    public static class CartItem {
        private String id;
        private String name;
        private String imageUrl;
        private List<String> selectedSizes;
        private String totalPrice;
    }
}

