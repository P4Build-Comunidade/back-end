package com.p4buld_auth.service.impl;


import com.p4buld_auth.dto.AuthenticationResponse;
import com.p4buld_auth.dto.AuthProviderDTO;
import com.p4buld_auth.dto.ExternalUser;
import com.p4buld_auth.model.provider.AuthIdentity;
import com.p4buld_auth.model.provider.TypeProvider;
import com.p4buld_auth.model.user.CustomUserDetails;
import com.p4buld_auth.model.user.Users;
import com.p4buld_auth.repository.AuthIdentityRepository;
import com.p4buld_auth.service.interfaces.AuthenticationProvider;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuthService  {
    private final List<AuthenticationProvider> providerList;
    private final AuthIdentityRepository authRepository;
    private final JwtService jwtService;
    private final CustomUserDetailsService serviceDetails;


    public AuthService(List<AuthenticationProvider> providerList, AuthIdentityRepository authRepository , JwtService jwtService , CustomUserDetailsService serviceDetails) {
        this.providerList = providerList;
        this.authRepository = authRepository;
        this.jwtService = jwtService;
        this.serviceDetails = serviceDetails;
    }

    public AuthenticationResponse autenticate(TypeProvider providerType, AuthProviderDTO providerDTO) {



        AuthenticationProvider provider = providerList.stream()
                .filter(p -> p.getProvider() == providerType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("provedor nao encontrado"));
        serviceDetails.setProvider(providerType);
        ExternalUser authenticate = provider.authenticateProvider(providerDTO);

        AuthIdentity authIdentity = authRepository.findByProviderAndProviderId(providerType, authenticate.getProviderId()).orElseThrow();

        Users user = authIdentity.getUser();

        CustomUserDetails customUserDetails;
        if (providerType != TypeProvider.LOCAL){
           customUserDetails = serviceDetails.loadUserByUsername(authIdentity.getEmail());
        }else {
            customUserDetails = serviceDetails.createUserDetails(user,authIdentity);
        }

        String accestoken = jwtService.generetedToken(customUserDetails);

        return new AuthenticationResponse(accestoken, providerType.toString(), user.getId(),authIdentity.getEmail());
    }



}