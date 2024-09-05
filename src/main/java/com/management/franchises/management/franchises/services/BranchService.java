package com.management.franchises.management.franchises.services;

import com.management.franchises.management.franchises.models.Branch;
import com.management.franchises.management.franchises.models.dtos.DTOBranch;

import reactor.core.publisher.Mono;

public interface BranchService {
    Mono<DTOBranch> create(Branch branch);
    Mono<DTOBranch> updateName(Long id, String name);

}
