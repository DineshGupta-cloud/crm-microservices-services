package com.crm.notification.entity;
import jakarta.persistence.*; import lombok.*; import java.time.LocalDateTime;
@Entity @Table(name="notifications") @Getter @Setter @NoArgsConstructor
public class Notification { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @Column(nullable=false) private Long userId; @Column(nullable=false) private String title; private String message; private String type; private boolean readFlag=false; private LocalDateTime createdAt=LocalDateTime.now(); }
