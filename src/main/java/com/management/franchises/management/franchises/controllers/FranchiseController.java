package com.management.franchises.management.franchises.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.management.franchises.management.franchises.models.Franchise;
import com.management.franchises.management.franchises.models.dtos.DTOFranchise;
import com.management.franchises.management.franchises.services.FranchiseService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/franchises")
public class FranchiseController {
    
    @Autowired
    private FranchiseService franchiseService;


    @PostMapping
    public Mono<DTOFranchise> createFranchise(@RequestBody Franchise franchise) {
        return this.franchiseService.create(franchise);
    }

    @PutMapping("/update-name/{id}")
    public Mono<DTOFranchise> updateName(@PathVariable Long id, @RequestParam String name) {
        return this.franchiseService.updateName(id,name);
    }


}
