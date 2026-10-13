package com.p4buld_auth.dto;

import java.util.UUID;

public record AuthenticationResponse(String accessToken, String tokenType, UUID id, String email) {
}
