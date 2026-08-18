package com.crm.product.entity;
import jakarta.persistence.*; import lombok.*;
@Entity @Table(name="products") @Getter @Setter @NoArgsConstructor
public class Product { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @Column(nullable=false) private String name; private String sku; private String description; private java.math.BigDecimal price; private boolean active=true; }
