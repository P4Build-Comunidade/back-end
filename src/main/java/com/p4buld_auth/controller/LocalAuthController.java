package com.p4buld_auth.controller;

import com.p4buld_auth.dto.AuthProviderDTO;
import com.p4buld_auth.dto.AuthenticationResponse;
import com.p4buld_auth.model.provider.TypeProvider;
import com.p4buld_auth.service.impl.AuthService;
import com.p4buld_auth.service.impl.LocalAuthProvider;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/local")
public class LocalAuthController {

    private final AuthService authService;
    private final LocalAuthProvider localAuthProvider;

    public LocalAuthController(AuthService authService, LocalAuthProvider localAuthProvider) {
        this.authService = authService;
        this.localAuthProvider = localAuthProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registrar (@RequestBody @Valid AuthProviderDTO dataLogin){
        localAuthProvider.register(dataLogin);
         return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<AuthenticationResponse> login (@RequestBody @Valid AuthProviderDTO login){
        AuthenticationResponse autenticate = authService.autenticate(TypeProvider.LOCAL, login);
        return ResponseEntity.status(HttpStatus.OK).body(autenticate);
    }
}
