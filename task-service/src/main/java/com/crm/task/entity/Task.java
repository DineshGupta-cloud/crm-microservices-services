package com.crm.task.entity;
import jakarta.persistence.*; import lombok.*; import java.time.LocalDateTime;
@Entity @Table(name="tasks") @Getter @Setter @NoArgsConstructor
public class Task { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @Column(nullable=false) private String title; private String description; private String status; private String priority; private Long assignedTo; private LocalDateTime dueDate; }
