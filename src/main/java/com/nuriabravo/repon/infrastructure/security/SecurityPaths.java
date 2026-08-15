package com.nuriabravo.repon.infrastructure.security;

import java.util.List;

public final class SecurityPaths {

    public static final List<String> PUBLIC_PATHS = List.of(
            "/api/login",
            "/v3/api-docs",
            "/swagger-ui"
    );

    public static final String[] SECURITY_MATCHERS = {
            "/api/login",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };

    private SecurityPaths() {
    }
}