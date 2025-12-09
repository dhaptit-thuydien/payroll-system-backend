package com.toi2.dithuydien.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    private final Key key;
    private final long jwtExpiration;

    // CONSTRUCTOR MỚI: Khởi tạo Key an toàn
    public JwtTokenProvider(@Value("${app.jwt.secret}") String jwtSecret,
                            @Value("${app.jwt.expiration}") long jwtExpiration) {
        // Khắc phục LỖI WEAKKEYEXCEPTION: Chuyển chuỗi ASCII sang Byte Array để tạo Key an toàn (>= 512 bits)
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        this.jwtExpiration = jwtExpiration;
    }

    // Phương thức 1: TẠO (SINH) JWT (ĐÃ SỬA CÚ PHÁP SIGNWITH)
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                // SỬA LỖI CÚ PHÁP JJWT 0.12.x: Chỉ sử dụng signWith(Key, SignatureAlgorithm)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // Phương thức 2: LẤY USERNAME TỪ JWT (ĐÃ SỬA CÚ PHÁP PARSER)
    public String getUsername(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }



    // Phương thức 3: XÁC THỰC (VALIDATE) JWT (ĐÃ SỬA CÚ PHÁP PARSER)
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            logger.error("Invalid JWT: {}", e.getMessage());
        }
        return false;
    }




}