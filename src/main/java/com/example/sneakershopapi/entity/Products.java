package com.example.sneakershopapi.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Getter
@Setter
public class Products {
    @Id
    private String id;
    private String name;
    private String imageUrl;
    private Double originalPrice;
    private Double discountPrice;
    private String description;
    private String type;
    private List<Integer> numeration;
    private List<String> color;





    // ... other fields

    // Getters and setters
}
