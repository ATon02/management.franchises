package com.management.franchises.management.franchises.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.management.franchises.management.franchises.models.Product;
import com.management.franchises.management.franchises.models.dtos.DTOProduct;
import com.management.franchises.management.franchises.services.ProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;


    @PostMapping
    public Mono<DTOProduct> createProduct(@RequestBody Product product) {
        return this.productService.create(product);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteMapping(@PathVariable Long id) {
        return this.productService.delete(id);
    }

    @GetMapping("/max-product/{franchiseId}")
    public Flux<Map<String,DTOProduct>> getProductMaxStock(@PathVariable Long franchiseId) {
        return this.productService.getProductMaxStock(franchiseId);
    }

    @PutMapping("/update-name/{id}")
    public Mono<DTOProduct> updateName(@PathVariable Long id, @RequestParam String name) {
        return this.productService.updateName(id,name);
    }

}
