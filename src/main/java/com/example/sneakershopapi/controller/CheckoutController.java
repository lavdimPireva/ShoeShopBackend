package com.example.sneakershopapi.controller;


import com.example.sneakershopapi.entity.CheckoutForm;
import com.example.sneakershopapi.entity.ResponseMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    @PostMapping(produces = "application/json")
    public ResponseEntity<ResponseMessage> processCheckoutForm(@RequestBody CheckoutForm form) {
        // Implement your business logic here

        System.out.println("Checkoutform"  + form);


        // For example, process the form, save to database, send confirmation email, etc.

        // If everything is successful
        return ResponseEntity.ok().body(new ResponseMessage("Checkout process successful"));
        // If there's an error
        // return ResponseEntity.badRequest().body("Error message");
    }
}




