package com.elissir.proyecto.jwt;

import com.elissir.proyecto.entidades.Admin;
import com.elissir.proyecto.entidades.Persona;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;

import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.HashMap;
import java.util.Date;
import java.util.function.Function;

//Generación de tokens!
@Service
public class JwtService {

    // Dane ub secret key más seguro
    private static final String SECRET_KEY = "alksdjoij423po4i3m23mflenoqp3p4npoijpuklhrlewqrljwe";

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(JwtService.class);

    public String getTokenAdmin(Admin admin) {
        return getTokenAdmin(new HashMap<>(), admin);
    }

    private String getTokenAdmin(HashMap<String, Object> extraClaims, Admin admin) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(admin.getNombre())
                // Fecha de inicio del permiso del token
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // Fecha final del permiso del token
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
//                Revisar la key con su propio método
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    public String getTokenPersona(Persona persona) {
        return getTokenPersona(new HashMap<>(), persona);

    }

    private String getTokenPersona(HashMap<String, Object> extraClaims, Persona persona) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(persona.getNombre())
                // Fecha de inicio del permiso del token
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // Fecha final del permiso del token
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
//                Revisar la key con su propio método
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUserNameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUserNameFromToken(token);
//        logger.info("Usuario es igual a userDetails: " + username + " " + userDetails.getUsername());
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Claims getAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiDateFromToken(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        return getExpiDateFromToken(token).before(new Date());
    }
}
