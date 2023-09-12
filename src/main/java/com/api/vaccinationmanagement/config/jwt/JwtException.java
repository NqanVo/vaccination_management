package com.api.vaccinationmanagement.config.jwt;

import com.api.vaccinationmanagement.response.ResponseModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

@Component
public class JwtException implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ResponseModel<String> errorDTO = new ResponseModel<>();
        errorDTO.setStatus(401);
        errorDTO.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        errorDTO.setMessage("UNAUTHORIZED");
        errorDTO.setData(authException.getMessage());

        ResponseEntity<ResponseModel<String>> responseEntity = new ResponseEntity<>(errorDTO, HttpStatus.UNAUTHORIZED);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS"));

        String json = objectMapper.writeValueAsString(responseEntity.getBody());

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
