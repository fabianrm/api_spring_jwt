package com.hampcode.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    //Para acceder a la propiedad jwt.secret del application.properties
    @Value("${jwt.secret}")
    private String jwtSecret;

    //Tiempo de expiración del token
    public final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 1000; //5 horas

    public String generateToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        // Suponiendo que UserPrincipal tiene métodos para obtener firstName, lastName y dni
        String firstName = userPrincipal.getFirstName();
        String lastName = userPrincipal.getLastName();
        String dni = userPrincipal.getDni();

        return Jwts.builder()
                .setSubject(userPrincipal.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(new SecretKeySpec(Base64.getDecoder().decode(jwtSecret), SignatureAlgorithm.HS512.getJcaName()))

                .claim("firstName", firstName)
                .claim("lastName", lastName)
                .claim("dni", dni)
                .claim("roles", userPrincipal.getAuthorities())
                .compact();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            // Log y manejo de excepción de firma JWT incorrecta
        } catch (MalformedJwtException ex) {
            // Log y manejo de JWT mal formado
        } catch (ExpiredJwtException ex) {
            // Log y manejo de JWT expirado
        } catch (UnsupportedJwtException ex) {
            // Log y manejo de JWT no soportado
        } catch (IllegalArgumentException ex) {
            // Log y manejo de cadena JWT vacía
        }
        return false;
    }


    public String getEmailFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
