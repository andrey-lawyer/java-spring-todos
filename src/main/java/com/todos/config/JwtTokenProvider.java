package com.todos.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
	
	@Value("${jwt.secret}")
	private String SECRET_KEY;
	
	@Value("${jwt.expiration}")
	private long EXPIRATION_TIME;
	
	private Key getSigningKey() {
		byte[] keyBytes = this.SECRET_KEY.getBytes(StandardCharsets.UTF_8);
		if (keyBytes.length < 32) { // 32 байта = 256 бит
			throw new IllegalArgumentException("The specified key is too short. It should be at least 256 bits.");
		}
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	public String createToken(String email) {
		Claims claims = Jwts.claims().setSubject(email);
		Date now = new Date();
		Date validity = new Date(now.getTime() + this.EXPIRATION_TIME); // Токен действителен 1 час
		
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(validity)
				.signWith(getSigningKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	public Claims getClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	public String getUsername(String token) {
		return getClaims(token).getSubject();
	}
	
	public String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7); // Убираем "Bearer " из начала строки
		}
		return null;
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
					.setSigningKey(getSigningKey())
					.build()
					.parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
