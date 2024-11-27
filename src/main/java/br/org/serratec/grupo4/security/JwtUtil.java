package br.org.serratec.grupo4.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.org.serratec.grupo4.domain.Usuario;
import br.org.serratec.grupo4.repository.UsuarioRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    @Value("${auth-jwt-secret}")
    private String jwtSecret;

    @Value("${auth-jwt-expiration-miliseg}")
    private Long jwtExpirationMiliseg;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public String generateToken(String username) {
        SecretKey secretKeySepc = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        Usuario user = usuarioRepository.findByEmail(username);

        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + this.jwtExpirationMiliseg))
                .claim("id", user.getId())
                .signWith(secretKeySepc)
                .compact();
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsername(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            return claims.getSubject();
        }
        return null;
    }

    /* 
   public Long getId(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            return Long.valueOf(claims.get("id", String.class));
        }
        return null;
    }  */

    public Long getId(String bearerToken) {

        String token = bearerToken.startsWith("Bearer ")
                ? bearerToken.substring(7)
                : bearerToken;

        Claims claims = getClaims(token);
        if (claims != null) {
            Object idObj = claims.get("id");
            if (idObj instanceof Double) {
                return ((Double) idObj).longValue();
            }
            if (idObj instanceof Integer) {
                return ((Integer) idObj).longValue();
            }
            return (Long) idObj;
        }
        return null;
    }

    public boolean isValidToken(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            String username = claims.getSubject();
            Date expirationDate = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());
            if (username != null && expirationDate != null && now.before(expirationDate)) {
                return true;
            }
        }
        return false;
    }
}
