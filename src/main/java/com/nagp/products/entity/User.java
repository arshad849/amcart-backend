package com.nagp.products.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @Column(name = "user_id", length = 50, nullable = false, unique = true)
    private String userId;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Column(name = "role", length = 50)
    private String role = "customer";

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

}