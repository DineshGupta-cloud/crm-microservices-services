package com.crm.company.entity;

import com.crm.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name="companies") @Getter @Setter @NoArgsConstructor
public class Company extends BaseEntity {
    @NotBlank @Column(nullable=false, unique=true, length=150) private String name;
    @Column(length=100) private String code;
    @Column(length=255) private String email;
    @Column(length=30) private String phone;
    @Column(length=500) private String address;
    @Column(nullable=false) private boolean active = true;
}