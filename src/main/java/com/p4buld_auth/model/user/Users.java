package com.p4buld_auth.model.user;


import com.p4buld_auth.model.user.enums.UserRole;
import com.p4buld_auth.model.user.enums.UserStatus;
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
@Table(name="users")
public class Users       {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_status")
    private UserStatus status;

    @Column(name= "created_at",updatable = false)
    @CreatedDate
    private Instant createdAt;

    @Column(name= "updated_at")
    private Instant updatedAt;

    @Enumerated(EnumType.STRING)
    private UserRole roles;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }

}

