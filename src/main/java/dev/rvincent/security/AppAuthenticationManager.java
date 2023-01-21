package dev.rvincent.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppAuthenticationManager implements AuthenticationManager {

    private final List<AuthenticationProvider> authenticationProviders;

    public AppAuthenticationManager(List<AuthenticationProvider> authenticationProviders) {
        this.authenticationProviders = authenticationProviders;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
         return authenticationProviders
                 .stream()
                 .filter(authenticationProvider -> authenticationProvider.supports(authentication.getClass()))
                 .findAny()
                 .map(authenticationProvider -> authenticationProvider.authenticate(authentication))
                 .orElseThrow(() ->
                         new ProviderNotFoundException("Supported provider not found for authentication of type: "
                                 + authentication.getClass()) {
                 });

    }
}
