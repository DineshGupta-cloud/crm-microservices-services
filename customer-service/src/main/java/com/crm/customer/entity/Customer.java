package com.crm.customer.entity;
import jakarta.persistence.*; import lombok.*;
@Entity @Table(name="customers") @Getter @Setter @NoArgsConstructor
public class Customer { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @Column(nullable=false) private String name; private String email; private String phone; private String address; private String status; }
