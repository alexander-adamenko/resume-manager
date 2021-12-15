package com.infopulse.resumemanager.security;

import com.infopulse.resumemanager.util.JwtTokenComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Component
public class CustomAuthorisationFilter extends OncePerRequestFilter {

    private JwtTokenComponent jwtTokenComponent;

    @Autowired
    public void setJwtTokenComponent(JwtTokenComponent jwtTokenComponent) {
        this.jwtTokenComponent = jwtTokenComponent;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(!request.getServletPath().equals("/api/v1/login") && !request.getServletPath().equals("/api/v1/refresh-token")) {
            String jwtToken = jwtTokenComponent.getJwtFromRequestHeader(request);
            String username = jwtTokenComponent.extractUsername(jwtToken);
            if (username != null && jwtTokenComponent.validateToken(jwtToken, username, JwtTokenComponent.TokenType.ACCESS)) {
                var map = jwtTokenComponent.extractClaim(jwtToken, HashMap::new);
                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                List<String> roles = (List<String>) map.get("roles");
                roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
