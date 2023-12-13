package com.example.sneakershopapi.service;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.sneakershopapi.dto.CaptureResponseDTO;
import com.example.sneakershopapi.entity.OrderCaptureRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    private AmazonSimpleEmailService sesClient;

    @PostConstruct
    private void initializeAmazon() {
        this.sesClient = AmazonSimpleEmailServiceClientBuilder.standard()
                .withRegion(Regions.EU_CENTRAL_1)
                .build();
    }


    public void prepareAndSendConfirmationEmail(OrderCaptureRequest orderCaptureRequest, CaptureResponseDTO captureDTO) {
        String userEmail = orderCaptureRequest.getUserDetails().getEmail();
        String fullName = orderCaptureRequest.getUserDetails().getFullName();

        Map<String, String> templateDataMap = new HashMap<>();
        templateDataMap.put("clientFullName", fullName);

        String templateData = convertToJSON(templateDataMap);

        SendTemplatedEmailRequest templatedEmailRequest = new SendTemplatedEmailRequest()
                .withDestination(new Destination().withToAddresses(userEmail))
                .withTemplate("SimpleOrderConfirmation")
                .withTemplateData(templateData)
                .withSource("\"Atletja ime\" <info@atletjaime.com>");

        sesClient.sendTemplatedEmail(templatedEmailRequest);
    }


    private String convertToJSON(Map<String, String> data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            // Handle the error appropriately
            System.err.println("Error converting template data to JSON: " + e.getMessage());
            e.printStackTrace();
            return "{}"; // Return empty JSON object on error
        }
    }



}
