package dev.rvincent.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager appAuthenticationManager;
    private final List<RequestMatcher> exclusionMatcher = List.of(
            new AntPathRequestMatcher("/api/v1/auth/**"),
            new AntPathRequestMatcher("/api/v1/movie/**"));

    public JwtAuthenticationFilter(AuthenticationManager appAuthenticationManager) {
        this.appAuthenticationManager = appAuthenticationManager;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        if (this.exclusionMatcher.stream().noneMatch(requestMatcher -> requestMatcher.matches(request))) {
            final String bearerToken = request.getHeader("Authorization");

            if (StringUtils.isBlank(bearerToken)) {
                throw new BadCredentialsException("Token is missing");
            }
            final String jwt = bearerToken.substring(7);
            Authentication authentication = new JwtAuthentication(false, jwt);

            authentication = appAuthenticationManager.authenticate(authentication);

            if (authentication.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            }
        } else
            filterChain.doFilter(request, response);
    }
}
