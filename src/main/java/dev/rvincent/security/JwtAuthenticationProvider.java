package dev.rvincent.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtService jwtService;
    private final UserDetailsService jpaUserDetailsService;

    public JwtAuthenticationProvider(JwtService jwtService, UserDetailsService jpaUserDetailsService) {
        this.jwtService = jwtService;
        this.jpaUserDetailsService = jpaUserDetailsService;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication;
        final String token = jwtAuthentication.getToken();
        final String userName = jwtService.extractUserNameFromToken(token);
        if (!Objects.isNull(userName)) {
            UserDetails userDetails = jpaUserDetailsService.loadUserByUsername(userName);
            if (jwtService.isTokenValid(token, userDetails)) {
                return new JwtAuthentication(true, null);
            }
        }
        throw new BadCredentialsException("User is not successfully authenticated");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.equals(authentication);
    }
}
