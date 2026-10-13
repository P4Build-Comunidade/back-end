package com.p4buld_auth.service.impl;


import com.p4buld_auth.client.DiscordClient;
import com.p4buld_auth.dto.AuthProviderDTO;
import com.p4buld_auth.dto.DiscordUserResponse;
import com.p4buld_auth.dto.ExternalUser;
import com.p4buld_auth.dto.TokenResponseDiscord;
import com.p4buld_auth.model.provider.TypeProvider;
import com.p4buld_auth.service.interfaces.AuthenticationProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@Component
public class DiscordAuthProvider implements AuthenticationProvider {
    @Value("${CLIENT_ID}")
    private String clientId;
    @Value("${REDIRECT_URI}")
    private String redirectUrl;
    @Value("${CLIENT_SECRET}")
    private String clientSecret;
    private final DiscordClient discordClient;
    private static final String DISCORD_AUTHORIZE_URL = "https://discord.com/oauth2/authorize";
    private final Cache cache;
    private final AuthorizationService authorizationService;


    public DiscordAuthProvider(CacheManager cache, DiscordClient discordClient, AuthorizationService authorizationService) {
        this.cache = cache.getCache("oauth2States");
        this.discordClient = discordClient;

        this.authorizationService = authorizationService;
    }


    @Override
    public ExternalUser authenticateProvider(AuthProviderDTO code) {
        TokenResponseDiscord tokenResponseDiscord = discordClient.exchangeCodeForToken(clientId, clientSecret, code.code(), redirectUrl);
        DiscordUserResponse response = discordClient.getUserInfo(tokenResponseDiscord.accessToken());
        ExternalUser user = new ExternalUser();
        user.setProviderId(response.providerId());
        user.setEmail(response.email());
        authorizationService.createNewUserAndIdentity(user,TypeProvider.DISCORD);
        return user;
    }


    public String generateUlrAuth() {

        String state = UUID.randomUUID().toString();

        cache.put(state, true);

        return UriComponentsBuilder.fromUriString(DISCORD_AUTHORIZE_URL)
                .queryParam("client_id", clientId)
                .queryParam("response_type", "code")
                .queryParam("redirect_uri", redirectUrl)
                .queryParam("scope", "identify email")
                .queryParam("state", state)
                .build()
                .encode()
                .toUriString();

    }

    @Override
    public TypeProvider getProvider() {
        return TypeProvider.DISCORD;
    }


}
