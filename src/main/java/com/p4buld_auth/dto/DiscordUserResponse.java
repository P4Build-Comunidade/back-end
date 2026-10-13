package com.p4buld_auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DiscordUserResponse(
        @JsonProperty("id")
        String providerId,
        String username,
        @JsonProperty("global_name") String globalName,
        String email,
        String avatar
) {
}