package com.infopulse.resumemanager.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@PropertySource("classpath:application.properties")
public class JwtTokenComponent {
    //@Value(value = "${secret_key}") todo:do not work for some reason
    private final String ENCODED_KEY = "rtzjyNGqu1z9+Wa7XqTq30sSvCAYhMrKt8Cjaw4I8n/e2r9+0WiFW1O+92BJRnWTFDdmCQYeLFoxybueaENDQQ==";
    private final int ACCESS_TOKEN_LIFETIME_MS = 1000 * 60 * 600;//1h
    private final int REFRESH_TOKEN_LIFETIME_MS = 1000 * 60 * 60 * 30;//3h
//    private static final KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS512);
    private final SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(ENCODED_KEY));
    public enum TokenType {
        ACCESS,
        REFRESH
    }


    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(UserDetails userDetails, TokenType type, int lifetimeMillis) {
        Map<String, Object> claims = new HashMap<>();
        List<String> authorities = new ArrayList<>();
        userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).forEach(authorities::add);
        claims.put("roles", authorities);
        claims.put("token_type", type.name());
        return createToken(claims, userDetails.getUsername(), lifetimeMillis);
    }

    private String createToken(Map<String, Object> claims, String subject, int lifetimeMillis) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + lifetimeMillis))
                .signWith(key)
                .compact();
    }

    public Boolean validateToken(String token, String username, TokenType type) {
        final String usernameFromToken = extractUsername(token);
        return (usernameFromToken.equals(username) && !isTokenExpired(token)
                && extractClaim(token, claims -> claims.get("token_type")).equals(type.name()));
    }

    public String getJwtFromRequestHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        } else {
            bearerToken = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("access_token")).findFirst().orElseThrow().getValue();
        }
        return bearerToken;
    }

    public String createAccessToken(org.springframework.security.core.userdetails.User user) throws JsonProcessingException {
        return generateToken(user, TokenType.ACCESS, ACCESS_TOKEN_LIFETIME_MS);
    }

    public String createRefreshToken(org.springframework.security.core.userdetails.User user) throws JsonProcessingException {
        return generateToken(user, TokenType.REFRESH, REFRESH_TOKEN_LIFETIME_MS);
    }

    public void generateTokensForUser(User user, HttpServletResponse response) throws IOException {
        String accessToken = createAccessToken(user);
        String refreshToken = createRefreshToken(user);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);
        response.setContentType(APPLICATION_JSON_VALUE);
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(tokens));

        Cookie cookie = new Cookie("access_token", accessToken);
        cookie.setMaxAge(5*60*60);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setDomain("");
        response.addCookie(cookie);
    }
}
