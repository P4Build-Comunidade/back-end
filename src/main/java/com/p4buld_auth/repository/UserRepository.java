package com.p4buld_auth.repository;

import com.p4buld_auth.model.provider.Authidentity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Authidentity,UUID > {
}
