package com.example.nto.service.impl;

import com.example.nto.entity.User;
import com.example.nto.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;
@Service
public class JwtServiceImpl implements JwtService {
    @Value("${security.jwt.access-expiration}")
    private String accessExp;
    @Value("${security.jwt.secret}")
    private String secret;
    @Value("${security.jwt.refresh-expiration}")
    private String refreshExp;

    private SecretKey getKey(){
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
    }

    private String genToken(String username, long exp, String issuer){
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + exp))
                .issuer(issuer)
                .signWith(getKey())
                .compact();
    }
    @Override
    public boolean isValid(String token, String username){
        Jwts.parser().verifyWith(getKey());
        return username.equals(getClaim(token, Claims::getSubject)) && getClaim(token, Claims::getExpiration).after(new Date(System.currentTimeMillis()));
    }

    @Override
    public String genRefresh(User user) {
        return genToken(user.getUsername(), Long.valueOf(refreshExp), "refresh");
    }

    @Override
    public String genAccess(User user) {
        return genToken(user.getUsername(), Long.valueOf(accessExp), "access");
    }

    @Override
    public boolean isValidRefresh(String token, User user) {
        return isValid(token, user.getUsername()) && getClaim(token, Claims::getIssuer).equals("refresh");
    }

    @Override
    public boolean isValidAccess(String token, User user) {
        return isValid(token, user.getUsername()) && getClaim(token, Claims::getIssuer).equals("access");
    }

    @Override
    public Claims getClaims(String token) {
        return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
    }

    @Override
    public <T> T getClaim(String token, Function<Claims, T> resolver) {
        return resolver.apply(getClaims(token));
    }
}
