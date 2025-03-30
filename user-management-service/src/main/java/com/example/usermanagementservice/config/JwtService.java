package com.example.usermanagementservice.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Key;
import java.util.Date;

@Service
@Getter
@Setter
public class JwtService {

    public final  String secretKeyBase64;

    public JwtService(@Value("${jwt.secret}") String secretKeyBase64) {
        this.secretKeyBase64 = secretKeyBase64;
    }

    // Generar token
    public String generateToken(String username, String role) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKeyBase64);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        long now = System.currentTimeMillis();
        long expiration = now + (1000 * 60 * 60); // 1 hora
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(expiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromToken(String token, HttpServletResponse response) throws IOException {
        System.out.println("Secret token: " + token);
        try {
            System.out.println("Secret key: " + secretKeyBase64);
            byte[] keyBytes = Decoders.BASE64.decode(secretKeyBase64);
            Key key = Keys.hmacShaKeyFor(keyBytes);
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public String getRoleFromToken(String token) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKeyBase64);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }

}
