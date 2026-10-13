package com.p4buld_auth.service.impl;


import com.p4buld_auth.client.DiscordClient;
import com.p4buld_auth.dto.ExternalUser;
import com.p4buld_auth.dto.TokenResponseDTO;
import com.p4buld_auth.model.provider.TypeProvider;
import com.p4buld_auth.service.interfaces.AuthenticationProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.UUID;

@Service
public class DiscordAuthService implements AuthenticationProvider {
    @Value("${CLIENT_ID}")
    private String clientId;

    @Value("${REDIRECT_URI}")
    private String redirectUrl;

    @Value("${CLIENT_SECRET}")
    private String clientSecret;

    private final DiscordClient discordClient;

    private static final String DISCORD_AUTHORIZE_URL= "https://discord.com/oauth2/authorize";

    private final Cache cache;

    public DiscordAuthService(CacheManager cache, DiscordClient discordClient) {
        this.cache = cache.getCache("oauth2States");
        this.discordClient = discordClient;
    }


    @Override
    public ExternalUser authenticate(String code) {
        TokenResponseDTO tokenResponseDTO = discordClient.exchangeCodeForToken(clientId,clientSecret,code,redirectUrl);
        return discordClient.getUserInfo(tokenResponseDTO.accessToken());

    }

    public String generateUlrAuth()  {

        String state = UUID.randomUUID().toString();

        cache.put(state,true);

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
