package com.management.franchises.management.franchises.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.franchises.management.franchises.exceptions.NotFoundException;
import com.management.franchises.management.franchises.exceptions.NotValidFieldException;
import com.management.franchises.management.franchises.models.Franchise;
import com.management.franchises.management.franchises.models.dtos.DTOFranchise;
import com.management.franchises.management.franchises.respositories.FranchiseRepository;
import com.management.franchises.management.franchises.services.FranchiseService;

import reactor.core.publisher.Mono;

@Service
public class FranchiseServiceImpl implements FranchiseService{

    @Autowired
    private FranchiseRepository franchiseRepository;

    @Override
    public Mono<DTOFranchise> create(Franchise franchise) {
        if (franchise.getName().isBlank()) {
            return Mono.error(new NotValidFieldException("Franchise name cannot be blank"));
        }
        return this.franchiseRepository.findByName(franchise.getName())
            .flatMap(exist -> Mono.error(new NotValidFieldException("Franchise name already exists")))
            .switchIfEmpty(this.franchiseRepository.save(franchise)
                .map(franchiseSave->new DTOFranchise(franchiseSave))
                .switchIfEmpty(Mono.error(new Exception("Franchise not created")))
            )
            .map(franchiseSave -> (DTOFranchise)franchiseSave)
            .onErrorMap(Exception.class,ex -> new Exception(ex.getMessage()));
    }

    @Override
    public Mono<DTOFranchise> updateName(Long id, String name) {
        if (id == null || id <= 0) {
            return Mono.error(new NotValidFieldException("Franchise ID cannot be 0 or negative"));
        }
        if (name == null || name.isBlank()) {
            return Mono.error(new NotValidFieldException("Franchise name cannot be blank"));
        }

        return this.franchiseRepository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("Franchise with id " + id + " not found")))
            .flatMap(existingFranchise -> 
                this.franchiseRepository.findByName(name)
                    .flatMap(franchiseByName -> {
                        if (!franchiseByName.getId().equals(id)) {
                            return Mono.error(new NotValidFieldException("Franchise name already exists"));
                        }
                        existingFranchise.setName(name);
                        return updateMethod(existingFranchise);
                    })
                    .switchIfEmpty(Mono.defer(() -> {
                        existingFranchise.setName(name);
                        return updateMethod(existingFranchise);
                    }))
            )
            .onErrorMap(Exception.class, ex -> new Exception(ex.getMessage()));
    }

    private Mono<DTOFranchise> updateMethod(Franchise franchise) {
        return this.franchiseRepository.save(franchise)
            .map(updatedFranchise -> new DTOFranchise(updatedFranchise))
            .switchIfEmpty(Mono.error(new Exception("Franchise not updated")));
    }

}
