package com.crm.company.repository;

import com.crm.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByCode(String code);
    boolean existsByNameIgnoreCase(String name);
}