package in.event.util;

import java.util.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtUtil {
	
	private String secretKey = "1234567890!@#$%^&*qwertyuiokjhgfdsa";  
    
	public String generateToken(String email) {
        return JWT.create()
                  .withSubject(email)
                  .withIssuedAt(Date.from(Instant.now()))   
                  .withExpiresAt(Date.from(Instant.now().plus(10, ChronoUnit.HOURS)))  
                  .sign(Algorithm.HMAC256(secretKey));
    }
	
	// Validate JWT Token
    public boolean validateToken(String token, String email) {
        return email.equals(extractUsername(token)) && !isTokenExpired(token);
    }

    // Extract username from JWT Token
    public String extractUsername(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);  // Decode the token
        return decodedJWT.getSubject();  // Get the subject (username/email)
    }

    // Check if the token is expired
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extract expiration date from JWT Token
    public Date extractExpiration(String token) {
        DecodedJWT decodedJWT = JWT.decode(token); 
        return decodedJWT.getExpiresAt();   
    }

}
