package io.reflectoring.buckpal.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.reflectoring.buckpal.security.domain.UserOAuth;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JwtProvider {

    private final SignatureAlgorithm algorithm = SignatureAlgorithm.HS512;
    private final String jwtSecret = "ACACIAN-ACACIAN-ACACIAN-ACACIAN-ACACIAN-ACACIAN-ACACIAN-ACACIAN-ACACIAN-ACACIAN-ACACIAN-ACACIAN-ACACIAN-ACACIAN"; //7번
    private final Long jwtExpiration = 864000000L;
    private final SecretKey secretKey;

    public JwtProvider() {
        byte[] keyBytes = jwtSecret.getBytes();
        secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Authentication authentication) {
        UserOAuth userPrincipal = (UserOAuth) authentication.getPrincipal();
        Date expiryDate = new Date(System.currentTimeMillis() + jwtExpiration);
        return Jwts.builder()
                .setSubject(userPrincipal.getIdentifier())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(secretKey, algorithm)
                .compact();
    }

    public String getAuthorizationHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authorizationHeader)) {
            return authorizationHeader;
        }
        throw new IllegalArgumentException("NotExistAuthorizationHeader");
    }

    public String getToken(String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        if (StringUtils.hasText(token)) {
            return token;
        }
        throw new IllegalArgumentException("NotExistToken");
    }

    public Jws<Claims> validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }

    }
}
