package com.management.franchises.management.franchises.services;

import java.util.Map;

import com.management.franchises.management.franchises.models.Product;
import com.management.franchises.management.franchises.models.dtos.DTOProduct;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {
    Mono<DTOProduct> create(Product product);
    Mono<Void> delete(Long id);
    Flux<Map<String,DTOProduct>> getProductMaxStock(Long franchiseId);
    Mono<DTOProduct> updateName(Long id, String name);


}
