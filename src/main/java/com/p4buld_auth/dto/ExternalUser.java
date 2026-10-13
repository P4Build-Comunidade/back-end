package com.p4buld_auth.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExternalUser {

    private String providerId;
    private String email;
    private String password;

}
