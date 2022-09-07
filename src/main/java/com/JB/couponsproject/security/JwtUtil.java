package com.JB.couponsproject.security;

import com.JB.couponsproject.enums.UserType;
import com.JB.couponsproject.util.YAMLConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
@Component
public class JwtUtil {
    private static final int ONE_HOUR_IN_MILLIS = 1000 * 60 * 60;
    public static final String SECRET_KEY = "YAMLConfig.jwtsecret";

    public static String extractEmail(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public static String extractId(final String token) {
        return extractClaim(token, Claims::getId);
    }

    public static Date extractExpiration(final String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public static <T> T extractClaim(final String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private static Claims extractAllClaims(final String token) {
        return Jwts.parser().setSigningKey(
                SECRET_KEY
        ).parseClaimsJws(token).getBody();
    }

    public static String generateToken(final Long id, final String email, UserType uesrType) {
        final Map<String, Object> claims = new HashMap<>();
        claims.put("email",email);
        claims.put("userType", uesrType.toString());
        claims.put("id",id);
        return createToken(claims, email);
    }

    private static String createToken(final Map<String, Object> claims, final String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ONE_HOUR_IN_MILLIS))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public static boolean validateToken(final String token, final UserDetails user) {
        final String email = extractEmail(token);
        return email.equals(user.getUsername()) && !isTokenExpired(token);
    }

    private static boolean isTokenExpired(final String token) {
        return extractExpiration(token).before(new Date());
    }
}