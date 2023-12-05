package com.example.sneakershopapi.controller;

import com.example.sneakershopapi.dto.CaptureResponseDTO;
import com.example.sneakershopapi.entity.OrderCaptureRequest;
import com.example.sneakershopapi.service.PayPalService;
import com.paypal.orders.Order;
import com.paypal.payments.Capture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @Autowired
    private PayPalService payPalService;

    @PostMapping("/capture-order")
    public ResponseEntity<?> captureOrder(@RequestBody OrderCaptureRequest orderCaptureRequest) {

        try {
            CaptureResponseDTO captureDTO = payPalService.authorizeAndCaptureOrder(orderCaptureRequest.getOrderId());

            if (captureDTO != null && "COMPLETED".equals(captureDTO.getStatus())) {
                System.out.println("CaptureDTO " + captureDTO);
                return ResponseEntity.ok(captureDTO);
            } else {
                return ResponseEntity.badRequest().body("Order capture failed");
            }
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
