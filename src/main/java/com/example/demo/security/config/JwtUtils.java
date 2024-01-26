package com.example.demo.security.config;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import com.example.demo.service.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
@Component
@ConfigurationProperties(prefix = "login")
public class JwtUtils {
private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
	
	@Value("${login.jwtSecret}")
	private String jwtSecret;

	@Value("${login.jwtExpirationMs}")
	private int jwtExpirationMs;

	@Value("${login.jwtCookieName}")
	private String jwtCookie;
	
	@Value("${login.jwtRefreshCookieName}")
	  private String jwtRefreshCookie;
	
	public static final String TOKEN_PREFIX = "Bearer ";
	  public static final String HEADER_STRING = "Authorization";
	  
	  public String getJwtFromCookies(HttpServletRequest request) {
		  String header = request.getHeader(HEADER_STRING);

	        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
	            
	            return null;
	        }
	        
	        String token = request.getHeader(HEADER_STRING);
	        
	        if(token!=null) {
	        	String accessToken=token.replace(TOKEN_PREFIX, "");
	        	
	        	return accessToken;
	        }
	        
	        return null;
		}
	  
	  public String generateJwtCookie(UserDetailsImpl userPrincipal) {
			String jwt = generateTokenFromUsername(userPrincipal.getUsername());
			return jwt;
	  }

	public ResponseCookie getCleanJwtCookie() {
			ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/api").build();
			return cookie;
		}
	
	public String getUserNameFromJwtToken(String token) {
		return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody().getSubject();
	}
	private java.security.Key key() {
		//SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
		String secretString = Encoders.BASE64.encode(jwtSecret.getBytes());
		logger.info("Secret key: " + secretString);
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretString));
	}
	
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
			return true;
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}
	
	public String generateTokenFromUsername(String username) {
		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))

				.signWith(key(), SignatureAlgorithm.HS256).compact();
	}
	

}
