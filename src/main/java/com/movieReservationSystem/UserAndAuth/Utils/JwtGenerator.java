package com.movieReservationSystem.UserAndAuth.Utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.movieReservationSystem.UserAndAuth.Entity.Role;

@Component
public class JwtGenerator {
    private static final String SECRET = "867d234be92475f1a256c0afdbbd8a1a823a6b9557da2aa16af894c2a7660292cd4429488fe1c988015061c954ad9791049816821c79d3a30929467cca7e13f19e6d816da4bcb80cbfb151222aebccacba04221ac62bd63f2c2af8d07ed93db30575971a48cf509e46be2bdd13668acd8d8620a19366421eac6ef567df87d989ab724f2f179ed944876df02108537ed11b72648478cbdabfb1f87beaa935313f7ea0431218b5d8dc7ac854c6e075cb37130ce264755a20bd49434ccecccb25831c7d576a3cf4faed7fa7ebbb73812d1e5ee32cae39b4576371cbd26b376b0863d2c14ecb5dbc2fd227102ac747e4a7410181326500c65cef98bad4b8201ba450"; // Replace
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             // with
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             // a
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             // strong
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             // secret!
    private static final long EXPIRATION_TIME = 86400000L; // 1 day

    public String generateToken(String userName, Set<Role> role) {
        return Jwts.builder()
                .setSubject(userName)
                .claim("role", role)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()), SignatureAlgorithm.HS512)
                .compact();
    }

    public String generateToken(String userName) {
        return Jwts.builder()
                .setSubject(userName)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()), SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUserNameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String getRoleFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes()))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
