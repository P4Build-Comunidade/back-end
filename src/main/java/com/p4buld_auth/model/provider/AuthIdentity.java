package com.p4buld_auth.model.provider;


import com.p4buld_auth.model.user.Users;
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
public class AuthIdentity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false, length = 20)
    private TypeProvider provider;

    @Column(name = "provider_id")
    private String providerId;

    @Column(unique = true)
    @Email
    @NotBlank
    private String email;

    @Column(name = "password_hash")
    private String password;

    @Column(name= "created_at",updatable = false)
    @CreatedDate
    private Instant createdAt;
    @Column(name= "updated_at")
    private Instant updatedAt;
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }

}
