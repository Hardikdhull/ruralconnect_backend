package com.ruralconnect.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    // 1. Generate a strong, secret key
    // This key is created with the HS512 algorithm information built-in
    private final Key jwtSecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    // 2. Set the token expiration time (e.g., 7 days)
    private final long jwtExpirationInMs = 1000 * 60 * 60 * 24 * 7;

    // 3. Generate a token
    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(userDetails.getUsername()) // This is the user's email
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(jwtSecretKey) // No algorithm needed, it's in the key
                .compact();
    }

    // 4. Get the user's email from the token (NEW SYNTAX)
    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .verifyWith((SecretKey) jwtSecretKey) // Replaces setSigningKey()
                .build()
                .parseSignedClaims(token) // Replaces parseClaimsJws()
                .getPayload(); // Replaces getBody()

        return claims.getSubject();
    }

    // 5. Validate the token (NEW SYNTAX)
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) jwtSecretKey)
                    .build()
                    .parseSignedClaims(authToken);
            return true;
        } catch (Exception ex) {
            // Token is invalid (expired, wrong signature, etc.)
            return false;
        }
    }
}