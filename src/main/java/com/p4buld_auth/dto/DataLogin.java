package com.p4buld_auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DataLogin(@NotBlank @Email String email, String password) {

}
