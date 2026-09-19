package com.example.nto.service;

import com.example.nto.entity.User;
import io.jsonwebtoken.Claims;

import java.util.function.Function;

public interface JwtService {
    String genRefresh(User user);
    String genAccess(User user);
    boolean isValid(String token, String username);
    boolean isValidRefresh(String token, User user);
    boolean isValidAccess(String token, User user);
    Claims getClaims(String token);
    <T> T getClaim(String token, Function<Claims, T> resolver);

}
