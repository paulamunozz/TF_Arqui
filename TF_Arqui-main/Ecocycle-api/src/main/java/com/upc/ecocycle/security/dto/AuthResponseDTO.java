package com.upc.ecocycle.security.dto;

import java.util.Set;

@lombok.Data
public class AuthResponseDTO {
    private String jwt;
    private Set<String> roles;
}