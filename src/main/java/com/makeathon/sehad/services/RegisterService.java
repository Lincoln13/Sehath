package com.makeathon.sehad.services;

import com.makeathon.sehad.models.db.Authentication;
import com.makeathon.sehad.models.signin.AuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    @Autowired
    private LogInService logInService;

    public String generateJwtToken(Authentication savedAuth) {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername(savedAuth.getEmail());
        request.setPassword(savedAuth.getPassword());
        return logInService.generateJWT(request);
    }

    public HttpHeaders getHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.AUTHORIZATION, token);
        return headers;
    }
}
