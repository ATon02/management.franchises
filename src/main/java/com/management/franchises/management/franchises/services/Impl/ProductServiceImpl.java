package com.management.franchises.management.franchises.services.Impl;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.franchises.management.franchises.exceptions.NotFoundException;
import com.management.franchises.management.franchises.exceptions.NotValidFieldException;
import com.management.franchises.management.franchises.models.Product;
import com.management.franchises.management.franchises.models.dtos.DTOProduct;
import com.management.franchises.management.franchises.respositories.BranchRepository;
import com.management.franchises.management.franchises.respositories.ProductRepository;
import com.management.franchises.management.franchises.services.ProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Mono<DTOProduct> create(Product product) {
        if (product.getName().isBlank()) {
            return Mono.error(new NotValidFieldException("Product name cannot be blank"));
        }
        if (product.getStock()<=0) {
            return Mono.error(new NotValidFieldException("Stock cannot be 0"));
        }
        if (product.getBranchId()==null || product.getBranchId()<=0) {
            return Mono.error(new NotValidFieldException("Branch id cannot be 0"));
        }
        return this.branchRepository.findById(product.getBranchId())
            .flatMap(branchExist -> 
                this.productRepository.save(product)
                .map(productSave -> new DTOProduct(productSave))
                .switchIfEmpty(Mono.error(new Exception("Product not created")))
            )
            .switchIfEmpty(Mono.error(new NotFoundException("Branch with id "+ product.getBranchId() +" not found")));
    }

    @Override
    public Mono<Void> delete(Long id) {
        if (id==null || id<=0) {
            return Mono.error(new NotValidFieldException("Branch id cannot be 0"));
        }
        return this.productRepository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("Product with id " + id + " not found")))
            .flatMap(product -> this.productRepository.delete(product));
    }

    @Override
    public Flux<Map<String,DTOProduct>> getProductMaxStock(Long franchiseId) {
        if (franchiseId==null || franchiseId<=0) {
            return Flux.error(new NotValidFieldException("Franchise id cannot be 0"));
        }
        return this.branchRepository.findByFranchiseId(franchiseId)
            .flatMap(branch -> 
                this.productRepository.findByBranchId(branch.getId())
                    .collectList() 
                    .map(products -> {
                        Product productWithMaxStock = products.stream()
                            .max(Comparator.comparingLong(Product::getStock))
                            .orElse(null);  
                        Map<String, DTOProduct> branchProductMap = new HashMap<>();
                        if (productWithMaxStock != null) {
                            branchProductMap.put(branch.getName(), new DTOProduct(productWithMaxStock));
                        }
                        return branchProductMap;
                    })
            ).switchIfEmpty(Flux.error(new NotFoundException("Not found branches to franchise with id " + franchiseId)));
    }

    @Override
    public Mono<DTOProduct> updateName(Long id, String name) {
        if (id == null || id <= 0) {
            return Mono.error(new NotValidFieldException("Product ID cannot be 0"));
        }
        if (name == null || name.isBlank()) {
            return Mono.error(new NotValidFieldException("Product name cannot be blank"));
        }
    
        return this.productRepository.findById(id)
            .flatMap(product -> {
                product.setName(name);
                return this.productRepository.save(product)
                    .map(updatedProduct -> new DTOProduct(updatedProduct))
                    .switchIfEmpty(Mono.error(new Exception("Product not updated")));
            })
            .switchIfEmpty(Mono.error(new NotFoundException("Product with id " + id + " not found")));
    }
    
}
