package com.example.sneakershopapi.service;

import com.braintreepayments.http.HttpResponse;
import com.braintreepayments.http.exceptions.HttpException;
import com.example.sneakershopapi.dto.CaptureResponseDTO;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.orders.Order;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.OrdersAuthorizeRequest;
import com.paypal.payments.AuthorizationsCaptureRequest;
import com.paypal.payments.Capture;
import com.paypal.payments.CaptureRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class PayPalService {

    private PayPalHttpClient client;

    public PayPalService(@Value("${paypal.client.id}") String clientId,
                         @Value("${paypal.client.secret}") String clientSecret) {
        // Create a sandbox environment using the injected values
        PayPalEnvironment environment = new PayPalEnvironment.Sandbox(clientId, clientSecret);
        this.client = new PayPalHttpClient(environment);
    }

    public CaptureResponseDTO authorizeAndCaptureOrder(String orderId) {
        try {
            // First, authorize the order
            OrdersAuthorizeRequest authorizeRequest = new OrdersAuthorizeRequest(orderId);
            authorizeRequest.requestBody(new OrderRequest());
            HttpResponse<Order> authorizeResponse = this.client.execute(authorizeRequest);
            Order authorizedOrder = authorizeResponse.result();

            // Check if authorization was successful
            if (authorizedOrder == null || authorizedOrder.purchaseUnits() == null) {
                throw new RuntimeException("Authorization failed for order " + orderId);
            }

            // Extract the authorization ID
            String authorizationId = authorizedOrder.purchaseUnits().get(0).payments().authorizations().get(0).id();

            // Then, capture the authorized payment
            AuthorizationsCaptureRequest captureRequest = new AuthorizationsCaptureRequest(authorizationId);
            CaptureRequest captureRequestBody = new CaptureRequest();
            captureRequest.requestBody(captureRequestBody);
            HttpResponse<Capture> captureResponse = this.client.execute(captureRequest);

            if (captureResponse.result() != null) {
                Capture capture = captureResponse.result();
                CaptureResponseDTO dto = new CaptureResponseDTO();
                dto.setId(capture.id());
                dto.setStatus(capture.status());

                return dto;
            } else {
                throw new RuntimeException("Capture failed for authorization " + authorizationId);
            }
        } catch (HttpException e) {
            // Handle specific PayPal HTTP exceptions
            System.err.println("HttpException when trying to authorize and capture order: " + e.getMessage());
            throw new RuntimeException("Failed to authorize and capture order", e);
        } catch (IOException e) {
            // Handle IOExceptions
            e.printStackTrace();
            System.err.println("IOException when trying to authorize and capture order: " + e.getMessage());
            throw new RuntimeException("Failed to authorize and capture order", e);
        } catch (Exception e) {
            // Handle other exceptions
            e.printStackTrace();
            System.err.println("General exception when trying to authorize and capture order: " + e.getMessage());
            throw new RuntimeException("Failed to authorize and capture order", e);
        }
    }

}



