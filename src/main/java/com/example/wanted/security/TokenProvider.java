package com.example.wanted.security;

import com.example.wanted.common.response.CodeSet;
import com.example.wanted.common.response.CustomException;
import com.example.wanted.user.adapter.in.web.dto.CustomUserDetails;
import com.example.wanted.user.domain.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class TokenProvider {

    private static final long TOKEN_VALIDATION_SECOND = 1000L * 60 * 120;
    private static final long REFRESH_TOKEN_VALIDATION_TIME = 1000L * 60 * 60 * 48;

    @Value("${secure.jwt.issuer}")
    private String ISSUER;



    private final Key key;

    @Autowired
    private UserDetailsService userDetailsService;

    public TokenProvider(@Value("${secure.jwt.secretKey}") String secret_key) {
        byte[] keyBytes = Decoders.BASE64.decode(secret_key);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(User userInfo) {
        return generateToken(userInfo, TOKEN_VALIDATION_SECOND);
    }

    public String generateRefreshToken(User userInfo) {
        return generateToken(userInfo, REFRESH_TOKEN_VALIDATION_TIME);
    }

    private String generateToken(User userInfo, Long expire_time) {
        Date now = new Date();
        Date expiresIn = new Date(now.getTime() + expire_time);

        return Jwts.builder()
                .setSubject("authorization")
                .claim("userId", userInfo.getId())
                .claim("email", userInfo.getEmail())
                .setIssuer(ISSUER)
                .setIssuedAt(now)
                .setExpiration(expiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 토큰으로부터 클레임을 만들고, 이를 통해 User 객체를 생성하여 Authentication 객체를 반환
     * @param token
     * @return
     */
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key).build()
                .parseClaimsJws(token).getBody();

        String username = claims.get("email", String.class);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * http 헤더로부터 bearer 토큰을 가져옴.
     * @param req
     * @return
     */
    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(final String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new CustomException(CodeSet.INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new CustomException(CodeSet.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException | IllegalArgumentException e) {
            throw new CustomException(CodeSet.UNSUPPORTED_TOKEN);
        }
    }

    /**
     * 토큰을 검증
     * @param token
     * @return
     */
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parserBuilder()
//                    .setSigningKey(key).build()
//                    .parseClaimsJws(token);
//            return true;
//        } catch (JwtException e) {
//            // MalformedJwtException | ExpiredJwtException | IllegalArgumentException
//            throw new CustomException(CodeSet.INVALID_TOKEN);
//        }
//    }
}
