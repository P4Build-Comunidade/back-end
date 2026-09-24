package com.p4buld_auth.model.user;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_status")
    private UserStatus status;

    @Column(name= "created_at",updatable = false)
    @CreatedDate
    private Instant createdAt;
    @Column(name= "updeted_at")
    private Instant updetedAt;
}

