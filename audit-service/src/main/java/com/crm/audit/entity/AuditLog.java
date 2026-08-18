package com.crm.audit.entity;
import jakarta.persistence.*; import lombok.*; import java.time.LocalDateTime;
@Entity @Table(name="audit_logs") @Getter @Setter @NoArgsConstructor
public class AuditLog { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; private Long userId; private String serviceName; private String action; private String entityName; private Long entityId; @Column(length=4000) private String details; private String ipAddress; private LocalDateTime createdAt=LocalDateTime.now(); }
