package com.hexlindia.drool.user.business;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        this.jwtUtil = Mockito.spy(new JwtUtil("test"));
    }

    @Test
    void testGenerateToken() {
        UserDetails userDetails = new User("Shehnaaz@gmail.com", "password", new ArrayList<>());
        String token = jwtUtil.generateToken(userDetails);
        assertNotNull(token);
        assertEquals("Shehnaaz@gmail.com", jwtUtil.getUsernameFromToken(token));
    }

    @Test
    void testValidateToken_usernameInvalid() {
        UserDetails userDetails = new User("Shehnaaz@gmail.com", "password", new ArrayList<>());
        String token = Jwts.builder().setClaims(new HashMap<>()).setSubject("crystal@gmail.com").setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() * 1000))
                .signWith(SignatureAlgorithm.HS512, "test").compact();
        assertFalse(jwtUtil.validateToken(token, userDetails));
    }

    @Test
    void testValidateToken_usernameValidAndValidToken() {
        UserDetails userDetails = new User("Shehnaaz@gmail.com", "password", new ArrayList<>());
        String token = Jwts.builder().setClaims(new HashMap<>()).setSubject("Shehnaaz@gmail.com").setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() * 1000))
                .signWith(SignatureAlgorithm.HS512, "test").compact();
        assertTrue(jwtUtil.validateToken(token, userDetails));
    }

    @Test
    void testValidateToken_tokenExpired() {
        UserDetails userDetails = new User("Shehnaaz@gmail.com", "password", new ArrayList<>());
        String token = Jwts.builder().setClaims(new HashMap<>()).setSubject("Shehnaaz@gmail.com").setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date((System.currentTimeMillis() - 10)))
                .signWith(SignatureAlgorithm.HS512, "test").compact();

        Assertions.assertThrows(ExpiredJwtException.class, () -> jwtUtil.validateToken(token, userDetails));
    }
}