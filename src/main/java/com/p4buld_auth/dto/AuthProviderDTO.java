package com.p4buld_auth.dto;

public record AuthProviderDTO(
        String code,
        String email,
        String password
) {
    public static AuthProviderDTO ofCode(String code) {
        return new AuthProviderDTO(code, null, null);
    }

    public static AuthProviderDTO ofLocal(String email, String password) {
        return new AuthProviderDTO(null, email, password);
    }
}