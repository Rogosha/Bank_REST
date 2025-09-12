package com.example.bankcards.util;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    client,
    admin;

    @Override
    public String getAuthority() {
        return name();
    }
}
