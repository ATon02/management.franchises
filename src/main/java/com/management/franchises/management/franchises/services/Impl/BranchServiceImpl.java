package com.management.franchises.management.franchises.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.franchises.management.franchises.exceptions.NotFoundException;
import com.management.franchises.management.franchises.exceptions.NotValidFieldException;
import com.management.franchises.management.franchises.models.Branch;
import com.management.franchises.management.franchises.models.dtos.DTOBranch;
import com.management.franchises.management.franchises.respositories.BranchRepository;
import com.management.franchises.management.franchises.respositories.FranchiseRepository;
import com.management.franchises.management.franchises.services.BranchService;

import reactor.core.publisher.Mono;

@Service
public class BranchServiceImpl implements BranchService {

    @Autowired 
    private FranchiseRepository franchiseRepository;
    @Autowired
    private BranchRepository branchRepository;

    @Override
    public Mono<DTOBranch> create(Branch branch) {
        if (branch.getName().isBlank()) {
            return Mono.error(new NotValidFieldException("Branch name cannot be blank"));
        }
        if (branch.getFranchiseId()==null || branch.getFranchiseId()<=0) {
            return Mono.error(new NotValidFieldException("Branch franchise id cannot be 0"));
        }
        return this.franchiseRepository.findById(branch.getFranchiseId())
            .flatMap(franchiseExist -> 
                this.branchRepository.save(branch)
                    .map(branchSave -> new DTOBranch(branchSave))
                    .switchIfEmpty(Mono.error(new Exception("Branch not created")))
            )
            .switchIfEmpty(Mono.error(new NotFoundException("Franchise with id "+ branch.getFranchiseId() +" not found")));
    }


    @Override
    public Mono<DTOBranch> updateName(Long id, String name) {
        if (id == null || id <= 0) {
            return Mono.error(new NotValidFieldException("Branch Id cannot be 0"));
        }
        if (name == null || name.isBlank()) {
            return Mono.error(new NotValidFieldException("Branch name cannot be blank"));
        }
    
        return this.branchRepository.findById(id)
            .flatMap(branch -> {
                branch.setName(name);
                return this.branchRepository.save(branch)
                    .map(updatedBranch -> new DTOBranch(updatedBranch))
                    .switchIfEmpty(Mono.error(new Exception("Branch not updated")));
            })
            .switchIfEmpty(Mono.error(new NotFoundException("Branch with id " + id + " not found")));
    }

    
    
}
