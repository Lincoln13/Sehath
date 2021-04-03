package com.makeathon.sehad.services;

import com.makeathon.sehad.jwt.util.JwtUtil;
import com.makeathon.sehad.models.signin.AuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class LogInService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    public String generateJWT(AuthenticationRequest request) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(
                request.getUsername()
        );
        return jwtUtil.generateToken(userDetails);
    }
}
