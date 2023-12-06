package com.example.sneakershopapi.service;

import com.example.sneakershopapi.entity.Products;
import com.example.sneakershopapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Products> getAllProducts() {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        return productRepository.findAll(sort);
    }

}
