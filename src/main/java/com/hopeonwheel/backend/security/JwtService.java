package com.hopeonwheel.backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JwtService {

    private final SecretKey key;
    private final long expMinutes;

    public JwtService(
            @Value("${app.jwt.secret}") String base64Secret,
            @Value("${app.jwt.exp-min:120}") long expMinutes
    ){
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Secret));
        this.expMinutes = expMinutes;
    }

    public String create(Long userId, String userType, String name, List<String> roles){
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(expMinutes * 60);
        return Jwts.builder()
                .setSubject("how-auth")
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(exp))
                .addClaims(Map.of(
                        "uid", userId,
                        "ut", userType,
                        "name", name,
                        "roles", roles
                ))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Jws<Claims> parse(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }

    public long getExpirationEpochSeconds(String token){
        var jws = parse(token);
        return jws.getBody().getExpiration().toInstant().getEpochSecond();
    }
}
