package com.p4buld_auth.service;


import com.p4buld_auth.client.DiscordClient;
import com.p4buld_auth.dto.DiscordUserResponse;
import com.p4buld_auth.dto.TokenResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.UUID;

@Service
public class DiscordAuthService {
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
    public TokenResponseDTO requestClient(String code, String state){
       if (validateState(state) == false){
           throw new RuntimeException();
       }
      return discordClient.exchangeCodeForToken(clientId,clientSecret,code,redirectUrl);

    }

    public DiscordUserResponse requestDataUSer(String code, String state){
      TokenResponseDTO tokenResponseDTO = requestClient(code,state);
        return discordClient.getUserInfo(tokenResponseDTO.accessToken());
    }

    public Boolean validateState(String state){
       if (cache.get(state) != null){
           cache.evict(state);
           return true;
       }
       return false;
    }
}
