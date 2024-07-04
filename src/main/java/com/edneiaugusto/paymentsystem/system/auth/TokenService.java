package com.edneiaugusto.paymentsystem.system.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.edneiaugusto.paymentsystem.arquitetura.RegraNegocioException;
import com.edneiaugusto.paymentsystem.system.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String generateToken(User user) {
        try {
            return JWT.create()
                    .withIssuer("Payment System")
                    .withSubject(user.getEmail())
                    .withExpiresAt(createExpirationDate())
                    .sign(Algorithm.HMAC256(jwtSecret));
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar token", e);
        }
    }

    private Instant createExpirationDate() {
        return LocalDateTime.now().plusWeeks(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String verifyToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(jwtSecret))
                    .withIssuer("Payment System")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new RegraNegocioException("Token inválido");
        }
    }
}
