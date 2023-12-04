package com.example.sneakershopapi.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PaymentVerificationRequest {
    private String paymentID;
    private String payerID;
}
