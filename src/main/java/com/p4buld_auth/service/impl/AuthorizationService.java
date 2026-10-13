package com.p4buld_auth.service.impl;

import com.p4buld_auth.dto.ExternalUser;
import com.p4buld_auth.model.provider.AuthIdentity;
import com.p4buld_auth.model.provider.TypeProvider;
import com.p4buld_auth.model.user.Users;
import com.p4buld_auth.model.user.enums.UserRole;
import com.p4buld_auth.model.user.enums.UserStatus;
import com.p4buld_auth.repository.AuthIdentityRepository;
import com.p4buld_auth.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorizationService {

    private final AuthIdentityRepository authIdentityRepository;
    private final UserRepository userRepository;

    public AuthorizationService(AuthIdentityRepository authIdentityRepository, UserRepository userRepository) {
        this.authIdentityRepository = authIdentityRepository;
        this.userRepository = userRepository;
    }

    public AuthIdentity createNewUserAndIdentity(ExternalUser externalUser, TypeProvider providerType) {
        Optional<AuthIdentity> existingIdentity =
                authIdentityRepository.findByProviderAndEmail(
                        providerType,
                        externalUser.getEmail()
                );

        if (existingIdentity.isPresent()) {
            return existingIdentity.get();
        }
        Users newUser = new Users();

        newUser.setStatus(UserStatus.ACTIVE);
        newUser.setRoles(UserRole.USER);
        userRepository.save(newUser);

        AuthIdentity newIdentity = new AuthIdentity();
        newIdentity.setUser(newUser);
        newIdentity.setProviderId(String.valueOf(externalUser.getProviderId()));
        newIdentity.setProvider(providerType);
        newIdentity.setEmail(externalUser.getEmail());
        newIdentity.setPassword(externalUser.getPassword());

        return authIdentityRepository.save(newIdentity);
    }

}