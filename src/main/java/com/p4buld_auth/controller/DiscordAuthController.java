package com.p4buld_auth.controller;


import com.p4buld_auth.service.DiscordAuthService;
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

    private final DiscordAuthService discordAuthService;

    public DiscordAuthController(DiscordAuthService discordAuthService) {
        this.discordAuthService = discordAuthService;
    }

    @GetMapping
    public ResponseEntity<Void> authInitial(){
        String uri = discordAuthService.generateUlrAuth();
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(uri)).build();
    }

    @GetMapping("/callback")
    public ResponseEntity<Void> callback(@RequestParam String code, @RequestParam String state){
        discordAuthService.requestDataUSer(code,state);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
