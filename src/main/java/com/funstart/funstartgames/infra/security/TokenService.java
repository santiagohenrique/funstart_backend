package com.funstart.funstartgames.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.funstart.funstartgames.models.User;

@Service
public class TokenService {
	
	@Value("${api.security.token.secret}")
	private String secret;

	public String generateToken(User user) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			String token = JWT
					.create()
					.withIssuer("funstart-api")
					.withSubject(user.getEmail())
					.withClaim("name", user.getName())
					.withExpiresAt(this.generateExpirationDate())
					.sign(algorithm);
			return token;
		} catch(JWTCreationException e) {
			throw new RuntimeException("Error while authenticating");
		}
	}
	
	public String validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm)
					.withIssuer("funstart-api")
					.build()
					.verify(token)
					.getSubject();
		} catch (JWTVerificationException e) {
			return null;
		}
	}
	
	public Instant generateExpirationDate() {
		return LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.of("-03:00"));
	}
	
}
