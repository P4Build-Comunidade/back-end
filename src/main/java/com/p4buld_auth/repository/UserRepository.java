package com.p4buld_auth.repository;

import com.p4buld_auth.model.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Users,UUID > {
}
