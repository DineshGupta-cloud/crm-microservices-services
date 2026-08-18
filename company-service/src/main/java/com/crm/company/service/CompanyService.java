package com.crm.company.service;

import com.crm.company.entity.Company;
import com.crm.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository repository;
    public List<Company> findAll(){ return repository.findAll(); }
    public Company findById(Long id){ return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Company not found: " + id)); }
    public Company create(Company company){ if(repository.existsByNameIgnoreCase(company.getName())) throw new IllegalArgumentException("Company already exists"); return repository.save(company); }
    public Company update(Long id, Company input){ Company c=findById(id); c.setName(input.getName()); c.setCode(input.getCode()); c.setEmail(input.getEmail()); c.setPhone(input.getPhone()); c.setAddress(input.getAddress()); c.setActive(input.isActive()); return repository.save(c); }
    public void delete(Long id){ repository.delete(findById(id)); }
}