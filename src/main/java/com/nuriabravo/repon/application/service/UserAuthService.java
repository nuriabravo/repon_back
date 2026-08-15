package com.nuriabravo.repon.application.service;

import com.nuriabravo.repon.application.dto.output.LoginResponse;

public interface UserAuthService {
    LoginResponse login(String username, String rawPassword);
    boolean isTokenValid(String token);
    String getUsernameFromToken(String token);
}