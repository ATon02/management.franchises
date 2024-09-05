package com.management.franchises.management.franchises.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.management.franchises.management.franchises.models.Branch;
import com.management.franchises.management.franchises.models.dtos.DTOBranch;
import com.management.franchises.management.franchises.services.BranchService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/branches")
public class BranchesController {
    
    @Autowired
    private BranchService branchService;


    @PostMapping
    public Mono<DTOBranch> createBranch(@RequestBody Branch branch) {
        return this.branchService.create(branch);
    }

    @PutMapping("/update-name/{id}")
    public Mono<DTOBranch> updateName(@PathVariable Long id, @RequestParam String name) {
        return this.branchService.updateName(id,name);
    }

}
