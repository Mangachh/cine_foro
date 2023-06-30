package cbs.cine_foro.service.security;

import java.security.Key;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    
    private static final String SECRET_KEY = "634463513950616c4243614159456243596e3332496f6f6671334835577a5074";

    public String extractUserName(final String token) {
        return "not_implemented";
    }

    private Claims extractAllClaims(final String token) {

        return Jwts.parserBuilder().setSigningKey(this.getSignInKey()).build().parseClaimsJws(token).getBody();
    }
    
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
