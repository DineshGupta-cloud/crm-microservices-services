package com.crm.vendor.entity;
import jakarta.persistence.*; import lombok.*;
@Entity @Table(name="vendors") @Getter @Setter @NoArgsConstructor
public class Vendor { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @Column(nullable=false) private String name; private String email; private String phone; private String address; private String status; }
