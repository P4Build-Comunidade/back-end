package com.p4buld_auth.service.impl;

import com.p4buld_auth.model.provider.AuthIdentity;
import com.p4buld_auth.model.provider.TypeProvider;
import com.p4buld_auth.model.user.CustomUserDetails;
import com.p4buld_auth.model.user.Users;
import com.p4buld_auth.repository.AuthIdentityRepository;
import org.jspecify.annotations.NonNull;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final AuthIdentityRepository authIdentityRepository;
    private TypeProvider providers;

    public CustomUserDetailsService(AuthIdentityRepository authIdentityRepository) {
        this.authIdentityRepository = authIdentityRepository;
    }

    @Override
    public CustomUserDetails loadUserByUsername(@NonNull String email)

            throws UsernameNotFoundException {

        AuthIdentity identity = authIdentityRepository
                .findByProviderAndEmail(
                        providers,
                        email
                )
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Credenciais inválidas"
                        )
                );

        Users user = identity.getUser();

        return createUserDetails(user, identity);
    }
    public void setProvider(TypeProvider provider){
        this.providers = provider;
    }

    public CustomUserDetails createUserDetails(Users user,AuthIdentity identity){
       return new CustomUserDetails(
                user.getId(),
                identity.getEmail(),
                identity.getPassword(),
                user.getRoles());
    }
}
