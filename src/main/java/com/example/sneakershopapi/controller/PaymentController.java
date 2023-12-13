package com.example.sneakershopapi.controller;
import com.example.sneakershopapi.dto.CaptureResponseDTO;
import com.example.sneakershopapi.entity.OrderCaptureRequest;
import com.example.sneakershopapi.service.EmailService;
import com.example.sneakershopapi.service.PayPalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api")
public class PaymentController {

    @Autowired
    private PayPalService payPalService;

    @Autowired
    private EmailService emailService; // Inject the EmailService


    @PostMapping("/capture-order")
    public ResponseEntity<?> captureOrder(@RequestBody OrderCaptureRequest orderCaptureRequest) {
        System.out.println("Order Data: " + orderCaptureRequest);

        try {
            CaptureResponseDTO captureDTO = payPalService.authorizeAndCaptureOrder(orderCaptureRequest.getOrderID());

            if (captureDTO == null) {
                System.err.println("CaptureDTO is null after attempting to capture order.");
                return ResponseEntity.badRequest().body("Order capture failed: CaptureDTO is null.");
            }

            if (!"COMPLETED".equals(captureDTO.getStatus())) {
                System.err.println("Capture status is not COMPLETED. Status: " + captureDTO.getStatus());
                return ResponseEntity.badRequest().body("Order capture failed: Capture status is not COMPLETED.");
            }

            // If status is COMPLETED, proceed to set the amount and send confirmation email
            double totalAmount = orderCaptureRequest.getSubtotal() + orderCaptureRequest.getTransportCost();
            captureDTO.setAmount(String.format("%.2f", totalAmount));

            System.out.println("CaptureDTO: " + captureDTO);
            emailService.prepareAndSendConfirmationEmail(orderCaptureRequest, captureDTO);

            return ResponseEntity.ok(captureDTO);
        } catch (RuntimeException e) {
            // Log the exception message and stack trace for debugging purposes
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Order capture failed due to an exception: " + e.getMessage());
        }
    }


    }
