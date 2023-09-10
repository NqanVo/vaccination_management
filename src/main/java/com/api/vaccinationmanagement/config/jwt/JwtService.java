package com.api.vaccinationmanagement.config.jwt;

import com.api.vaccinationmanagement.model.EmployeeModel;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class JwtService {
    private static final String SECRET_KEY = "5395589b288e5394ea033ae17510719c09c40b291b01dd0f16cd8745d236a289";
    private static final long ACCESS_TOKEN_TIME = 10 * 60 * 1000;
    private static final long REFRESH_TOKEN_TIME = 24 * 60 * 60 * 1000;

    public enum TOKEN_TYPE {
        ACCESS_TOKEN,
        REFRESH_TOKEN
    }

    private String role;
    private String roleRegion;

    public void setRoleAndRoleRegion(String token) {
        this.role = getRoleFromJwt(token);
        this.roleRegion = getRoleRegionFromJwt(token);
    }

    public String getRole() {
        return role;
    }

    public String getRoleRegion() {
        return roleRegion;
    }

    public String generateToken(EmployeeModel employeeModel, TOKEN_TYPE tokenType) {
        long exp = tokenType == TOKEN_TYPE.ACCESS_TOKEN ? ACCESS_TOKEN_TIME : REFRESH_TOKEN_TIME;
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", employeeModel.getRoleModel().getCode());
        claims.put("roleRegion", employeeModel.getRoleRegion());
        return Jwts
                .builder()
                .setSubject(employeeModel.getEmail())
                .setIssuer("SERVER")
                .setIssuedAt(new Date())
                .addClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + exp))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature -> Message: {} ", e.getMessage()); // Trả về thông báo lỗi cho chữ ký không hợp lệ
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token -> Message: {}", e.getMessage()); // Trả về thông báo lỗi cho token không hợp lệ
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token -> Message: {}", e.getMessage()); // Trả về thông báo lỗi cho token hết hạn
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token -> Message: {}", e.getMessage()); // Trả về thông báo lỗi cho token không được hỗ trợ
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty -> Message: {}", e.getMessage()); // Trả về thông báo lỗi khi chuỗi JWT claims rỗng
        } catch (Exception e) {
            log.error("Error {}", e.getMessage()); // Trả về thông báo lỗi khi chuỗi JWT claims rỗng
        }
        return false;
    }

    public String getEmailFromJwt(String token) {
        return parseClaims(token).getSubject();
    }

    public Date getExpiredFromJwt(String token) {
        return parseClaims(token).getExpiration();
    }

    public String getRoleFromJwt(String token) {
        return (String) parseClaims(token).get("role");
    }

    public String getRoleRegionFromJwt(String token) {
        return (String) parseClaims(token).get("roleRegion");
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
