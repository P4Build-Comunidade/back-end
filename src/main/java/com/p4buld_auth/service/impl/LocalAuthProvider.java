package com.p4buld_auth.service.impl;


import com.p4buld_auth.dto.AuthProviderDTO;
import com.p4buld_auth.dto.ExternalUser;
import com.p4buld_auth.model.provider.TypeProvider;
import com.p4buld_auth.repository.AuthIdentityRepository;
import com.p4buld_auth.service.interfaces.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class LocalAuthProvider implements AuthenticationProvider {

    private final AuthIdentityRepository authIdentityRepository;
    private final AuthorizationService authorizationService;
    private final AuthenticationManager authenticationManager;

    public LocalAuthProvider(AuthIdentityRepository authIdentityRepository, AuthorizationService authorizationService, AuthenticationManager authenticationManager) {
        this.authIdentityRepository = authIdentityRepository;
        this.authorizationService = authorizationService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public TypeProvider getProvider() {
        return TypeProvider.LOCAL;
    }

    @Override
    public ExternalUser authenticateProvider(AuthProviderDTO code) {
        var userNamePasswoard = new UsernamePasswordAuthenticationToken(code.email(), code.password());
        this.authenticationManager.authenticate(userNamePasswoard);

       if (authIdentityRepository.findByProviderAndEmail(TypeProvider.LOCAL,code.email()).isEmpty())
           throw new RuntimeException("Usuario não cadastrado");

        ExternalUser user = new ExternalUser();
        user.setPassword(userNamePasswoard.toString());
        user.setEmail(code.email());
        user.setProviderId(code.email());

        return user;
    }

    public void register(AuthProviderDTO data){
       if (authIdentityRepository.findByProviderAndEmail(TypeProvider.LOCAL,data.email()).isPresent())
           throw new RuntimeException("usuario já cadastrado");
        String encoder = new BCryptPasswordEncoder().encode(data.password());
        authorizationService.createNewUserAndIdentity(new ExternalUser(data.email(),data.email(),encoder),TypeProvider.LOCAL);
    }
}
