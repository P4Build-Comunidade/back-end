package com.p4buld_auth.client;

import com.p4buld_auth.dto.DiscordUserResponse;
import com.p4buld_auth.dto.TokenResponseDiscord;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Component
public class DiscordClient {

    private final RestClient restClient;

    public DiscordClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    public TokenResponseDiscord exchangeCodeForToken(
            String clientId,
            String clientSecret,
            String code,
            String redirectUri) {

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("grant_type", "authorization_code");
        body.add("code", code);
        body.add("redirect_uri", redirectUri);

        return restClient.post()
                .uri("https://discord.com/api/oauth2/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(body)
                .retrieve()
                .body(TokenResponseDiscord.class);
    }


    public DiscordUserResponse getUserInfo(String accessToken) {
        return restClient.get()
                .uri("https://discord.com/api/users/@me")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .body(DiscordUserResponse.class);
    }
}

