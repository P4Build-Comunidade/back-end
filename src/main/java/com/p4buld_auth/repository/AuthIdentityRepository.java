package com.p4buld_auth.repository;

import com.p4buld_auth.model.provider.AuthIdentity;
import com.p4buld_auth.model.provider.TypeProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthIdentityRepository extends JpaRepository<AuthIdentity, UUID> {
    Optional<AuthIdentity> findByProviderAndProviderId(TypeProvider provider, String id);

    Optional<AuthIdentity> findByProviderAndEmail(TypeProvider provider, String email);
    }

