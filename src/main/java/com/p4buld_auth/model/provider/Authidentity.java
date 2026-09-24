package com.p4buld_auth.model.provider;


import com.p4buld_auth.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

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
@Table(name="auth_identity")
public class Authidentity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false, length = 20)
    private typeProvider provider;

    @Column(name = "provider_id")
    private String providerID;

    @Column(unique = true)
    @Email
    @NotBlank
    private String email;

    @Column(name = "password_hash")
    private String password;

    @Column(name= "created_at",updatable = false)
    @CreatedDate
    private Instant createdAt;
    @Column(name= "updeted_at")
    private Instant updetedAt;
}
