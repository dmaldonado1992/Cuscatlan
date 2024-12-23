package com.bd.sc.security.jwt;

import com.bd.sc.models.entity.AuthUser;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
    private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    public String generateToken(Authentication authentication){
        AuthUser authUser = (AuthUser) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(authUser.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getUsername(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean isValidToken(String token){
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }catch(MalformedJwtException e){
            logger.error("Malformed token");
        }catch(UnsupportedJwtException e){
            logger.error("Unsupported token");
        }catch(ExpiredJwtException e){
            logger.error("Expired token");
        }catch(IllegalStateException e){
            logger.error("Empty token");
        }catch(SignatureException e){
            logger.error("fail token");
        }
        return false;
    }

}
