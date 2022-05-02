package com.vinsguru.grpc.utility;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jws;
import java.util.UUID;
public class Tokens {

    public static String generateToken(String username, String email) {
        return  Jwts.builder()
                .claim("username", username)
                .claim("email", email)
                .setSubject(username)
                .setId(UUID.randomUUID().toString())
                .compact();
    }

    public static Jwt parseToken(String token){
        return Jwts.parser().parse(token);
    }
}
