package com.findapickle.backend.services;

import com.findapickle.backend.entities.ProductEntity;
import com.findapickle.backend.exceptions.EntityNotFoundException;
import com.findapickle.backend.models.Product;
import com.findapickle.backend.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductsService {
    @Autowired
    private ProductsRepository productsRepository;

    public Product getById(Integer id){
        ProductEntity productEntity = productsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "Product"));

    }
}
