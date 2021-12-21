package com.infopulse.resumemanager.security;

import com.infopulse.resumemanager.repository.UserRepository;
import com.infopulse.resumemanager.repository.entity.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    private CustomAuthorisationFilter customAuthorisationFilter;
    private UserRepository userRepository;

    @Autowired
    public void setJwtRequestFilter(CustomAuthorisationFilter customAuthorisationFilter) {
        this.customAuthorisationFilter = customAuthorisationFilter;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean(), userRepository);
        customAuthenticationFilter.setFilterProcessesUrl("/api/v1/login");
        //todo: make csrf enable later
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/login", "/api/v1/refresh-token").permitAll()
                .antMatchers("/api/v1/users**").hasAuthority(ERole.ADMIN.name())
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(customAuthorisationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilter(customAuthenticationFilter);

        http.cors().configurationSource(
                request -> {
                    CorsConfiguration cors = new CorsConfiguration();
                    cors.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost"));
                    cors.setAllowedMethods(Arrays.asList("GET", "POST","PUT", "DELETE", "OPTIONS"));
                    cors.setAllowedHeaders(Collections.singletonList("*"));
                    cors.setAllowCredentials(true);
                    return cors;
                }
        );
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
