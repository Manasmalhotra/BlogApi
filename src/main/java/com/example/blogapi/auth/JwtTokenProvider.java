package com.example.blogapi.auth;


import com.example.blogapi.Exceptions.BlogApiException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


import java.security.Key;
import java.security.KeyStore;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value(value = "${app.jwt-secret}")
    private String jwtSecret;
    @Value(value="${app.jwt-expiration-milliseconds}")
    private long jwtExpirationDate;

    public JwtTokenProvider() {
    }

    public String generateToken(Authentication authentication){
        String username=authentication.getName();
        Date currentDate=new Date();
        Date expirationDate=new Date(currentDate.getTime()+jwtExpirationDate);
        String token= Jwts.builder()
        .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(key())
                .compact();
        return token;
    }

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
        String username = claims.getSubject();
        return username;
    }
    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
            return true;
        }
        catch(MalformedJwtException e){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Invalid token");
        }
        catch(ExpiredJwtException e){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Token Expired");
        }
        catch(UnsupportedJwtException e){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Unsupported Token");
        }
        catch(IllegalArgumentException e){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Jwt claims string is empty");
        }
    }
}
