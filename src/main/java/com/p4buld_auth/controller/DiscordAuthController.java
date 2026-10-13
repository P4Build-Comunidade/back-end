package com.p4buld_auth.controller;


import com.p4buld_auth.dto.AuthProviderDTO;
import com.p4buld_auth.dto.AuthenticationResponse;
import com.p4buld_auth.model.provider.TypeProvider;
import com.p4buld_auth.service.impl.AuthService;
import com.p4buld_auth.service.impl.DiscordAuthProvider;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/auth/discord")
public class DiscordAuthController {

    private final AuthService authService;
    private final DiscordAuthProvider discordAuthProvider;
    private final Cache cache;

    public DiscordAuthController(AuthService authService, DiscordAuthProvider discordAuthProvider , CacheManager cache) {
        this.authService = authService;
        this.cache =cache.getCache("oauth2States");
        this.discordAuthProvider = discordAuthProvider;
    }

    @GetMapping
    public ResponseEntity<Void> authInitial(){
        String uri = discordAuthProvider.generateUlrAuth();
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(uri)).build();
    }

    @GetMapping("/callback")
    public ResponseEntity<AuthenticationResponse> callback(@RequestParam String code, @RequestParam String state){

        if (!validateState(state)) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build();
        }
       AuthenticationResponse response =  authService.autenticate(TypeProvider.DISCORD,AuthProviderDTO.ofCode(code));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public Boolean validateState(String state){
        if (cache.get(state) != null){
            cache.evict(state);
            return true;
        }
        return false;
    }


}
