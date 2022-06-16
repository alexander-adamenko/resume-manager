package com.study.resumemanager.service;

import com.study.resumemanager.dto.UserDto;
import com.study.resumemanager.mapper.ObjectMapper;
import com.study.resumemanager.repository.UserRepository;
import com.study.resumemanager.repository.entity.User;
import com.study.resumemanager.util.JwtTokenComponent;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Service
@AllArgsConstructor
public class JwtUserWebService implements UserDetailsService {
    private final JwtTokenComponent jwtTokenComponent;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user = getUser(username);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.roles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.name())));
        return new org.springframework.security.core.userdetails.User(
                user.username(), user.password(), authorities);
    }

    private UserDto getUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " not found.");
        }
        return objectMapper.userToUserDto(user);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String jwtToken = jwtTokenComponent.getJwtFromRequestHeader(request);
        String username = jwtTokenComponent.extractUsername(jwtToken);
        if (username != null && jwtTokenComponent.validateToken(jwtToken, username, JwtTokenComponent.TokenType.REFRESH)) {
            org.springframework.security.core.userdetails.User user =
                    (org.springframework.security.core.userdetails.User) loadUserByUsername(username);
            jwtTokenComponent.generateTokensForUser(user, response);
        }
        else {
            //todo: refactor
            throw new IllegalArgumentException();
        }
    }

}
