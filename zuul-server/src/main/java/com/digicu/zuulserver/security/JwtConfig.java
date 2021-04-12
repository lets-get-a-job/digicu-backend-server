package com.digicu.zuulserver.security;

import org.springframework.beans.factory.annotation.Value;

public class JwtConfig {
    @Value("${security.jwt.header:Authorization}")
    private String header;

    @Value("${security.jwt.prefix:Bearer }")
    private String prefix;

    @Value("${security.jwt.secret:86ae5efb008c10740e15}")
    private String secret;

    @Value("${security.jwt.expiration:#{7200000}}")
    private int expiration;

    public String getHeader() {
        return header;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSecret() {
        return secret;
    }
}
