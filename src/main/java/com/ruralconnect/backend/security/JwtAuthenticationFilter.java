package com.ruralconnect.backend.security;

import com.ruralconnect.backend.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. Get the JWT token from the request header
        String jwt = getJwtFromRequest(request);

        // 2. Validate the token
        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {

            // 3. Get the user's email from the token
            String username = tokenProvider.getUsernameFromJWT(jwt);

            // 4. Load the user from the database
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            // 5. Create an authentication object
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // 6. Set the user in the SecurityContext (this "logs them in" for this request)
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Pass the request to the next filter in the chain
        filterChain.doFilter(request, response);
    }

    // Helper method to get the "Bearer <token>" string from the header
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}