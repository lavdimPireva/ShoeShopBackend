package com.example.sneakershopapi.controller;
import com.example.sneakershopapi.entity.Products;
import com.example.sneakershopapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/products/")
public class ProductController {


    private int count = 0;


    @Autowired
    private ProductService productService;



    @GetMapping
    public List<Products> getAllProducts() {
        count++;

        System.out.println("new call > " + count);
        return productService.getAllProducts();
    }


    @GetMapping("/")
    public String home() {
        return "Health check passed! The application is running.";
    }



}
