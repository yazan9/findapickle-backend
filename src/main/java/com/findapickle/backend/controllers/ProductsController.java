package com.findapickle.backend.controllers;

import com.findapickle.backend.models.Product;
import com.findapickle.backend.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping(path="/products")
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @GetMapping(path="/{id}")
    public @ResponseBody Product getById(@PathVariable Integer id){
        return productsService.getById(id);
    }

}
