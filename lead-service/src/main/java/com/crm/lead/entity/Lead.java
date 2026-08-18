package com.crm.lead.entity;
import jakarta.persistence.*; import lombok.*;
@Entity @Table(name="leads") @Getter @Setter @NoArgsConstructor
public class Lead { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @Column(nullable=false) private String name; private String email; private String phone; private String status; private Long customerId; }
