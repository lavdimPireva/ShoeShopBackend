package com.example.sneakershopapi.repository;

import com.example.sneakershopapi.entity.Products;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Products, String> {


}
