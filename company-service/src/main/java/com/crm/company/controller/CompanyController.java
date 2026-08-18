package com.crm.company.controller;

import com.crm.company.entity.Company;
import com.crm.company.service.CompanyService;
import com.crm.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/v1/companies") @RequiredArgsConstructor
public class CompanyController {
    private final CompanyService service;
    @GetMapping public ApiResponse<List<Company>> all(){ return ApiResponse.<List<Company>>builder().success(true).message("Companies fetched").data(service.findAll()).build(); }
    @GetMapping("/{id}") public ApiResponse<Company> get(@PathVariable Long id){ return ApiResponse.<Company>builder().success(true).message("Company fetched").data(service.findById(id)).build(); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public ApiResponse<Company> create(@Valid @RequestBody Company c){ return ApiResponse.<Company>builder().success(true).message("Company created").data(service.create(c)).build(); }
    @PutMapping("/{id}") public ApiResponse<Company> update(@PathVariable Long id,@Valid @RequestBody Company c){ return ApiResponse.<Company>builder().success(true).message("Company updated").data(service.update(id,c)).build(); }
    @DeleteMapping("/{id}") public ApiResponse<Void> delete(@PathVariable Long id){ service.delete(id); return ApiResponse.<Void>builder().success(true).message("Company deleted").build(); }
}