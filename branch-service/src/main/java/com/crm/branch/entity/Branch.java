package com.crm.branch.entity;
import com.crm.common.entity.BaseEntity; import jakarta.persistence.*; import jakarta.validation.constraints.NotBlank; import lombok.*;
@Entity @Table(name="branches") @Getter @Setter @NoArgsConstructor
public class Branch extends BaseEntity { @NotBlank @Column(nullable=false,length=100) private String name; @Column(nullable=false,length=30) private String code; @Column(nullable=false) private Long companyId; private String address; private String phone; private boolean active=true; }