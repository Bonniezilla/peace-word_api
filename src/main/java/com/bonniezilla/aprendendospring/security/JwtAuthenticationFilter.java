package com.bonniezilla.aprendendospring.security;

import com.bonniezilla.aprendendospring.entities.User;
import com.bonniezilla.aprendendospring.services.CustomUserDetailsService;
import com.bonniezilla.aprendendospring.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService, CustomUserDetailsService customUserDetailsService) {
        this.jwtService = jwtService;
        System.out.println("JwtAuthFilter INSTANTIATED");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("-------- JWT FILTER CALLED --------");
        System.out.println("Path: " + request.getServletPath());
        System.out.println("Authorization header: " + request.getHeader("Authorization"));

        String token = recoverToken(request);

        try {
            if (jwtService.validateToken(token)) {
                String username = jwtService.getUsernameFromToken(token);
                List<String> roles = jwtService.getRolesFromToken(token);

                List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList();

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                authorities
                        );

                System.out.println("Token recebido: {}" + token);
                System.out.println("Usuário autenticado: {}" + username);
                System.out.println("Authorities: {}" + authorities);

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception e) {
            System.out.println("Error on validating JWT to {}: {}" + request.getServletPath() + e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;

        return authHeader.replace("Bearer ", "");
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        return path.startsWith("/auth/") || path.equals("/users/create");
    }
}
