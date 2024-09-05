package com.management.franchises.management.franchises.services;

import com.management.franchises.management.franchises.models.Franchise;
import com.management.franchises.management.franchises.models.dtos.DTOFranchise;

import reactor.core.publisher.Mono;

public interface FranchiseService {
    
    Mono<DTOFranchise> create(Franchise franchise);
    Mono<DTOFranchise> updateName(Long id, String name);
}
