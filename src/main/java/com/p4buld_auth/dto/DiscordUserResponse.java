package com.p4buld_auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DiscordUserResponse(
        String id,
        String username,
        @JsonProperty("global_name") String globalName,
        String email,
        String avatar
) {
}

