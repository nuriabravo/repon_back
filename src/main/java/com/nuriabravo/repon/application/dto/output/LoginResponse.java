package com.nuriabravo.repon.application.dto.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private Instant expiration;
}