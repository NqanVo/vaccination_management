package com.api.vaccinationmanagement.config.jwt;

import com.api.vaccinationmanagement.model.EmployeeModel;
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
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    JwtService jwtService;
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String headerAuthorization = request.getHeader("Authorization");
        if (!hasAuthorizationBearer(headerAuthorization)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = headerAuthorization.split(" ")[1].trim();

        if (!jwtService.validateToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        jwtService.setRoleAndRoleRegion(token);
        setAuthenticationContext(token, request);
        filterChain.doFilter(request, response);

    }

    private void setAuthenticationContext(String token, HttpServletRequest request) {
        String email = jwtService.getEmailFromJwt(token);
        EmployeeModel employeeModel = customUserDetailsService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken
                authentication = new UsernamePasswordAuthenticationToken(
                employeeModel,
                null,
                employeeModel.getAuthorities());
        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private boolean hasAuthorizationBearer(String headerAuthorization) {
        if (headerAuthorization == null) return false;
        if (!headerAuthorization.startsWith("Bearer")) return false;
        return true;
    }
}
