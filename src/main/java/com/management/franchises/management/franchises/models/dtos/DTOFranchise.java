package com.management.franchises.management.franchises.models.dtos;

import com.management.franchises.management.franchises.models.Franchise;

public class DTOFranchise {
    private Long id;
    private String name;
    
    

    public DTOFranchise(Franchise franchise) {
        this.id = franchise.getId();
        this.name = franchise.getName();
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    
}
