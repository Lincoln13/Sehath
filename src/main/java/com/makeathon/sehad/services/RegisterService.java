package com.makeathon.sehad.services;

import com.makeathon.sehad.dao.AuthDao;
import com.makeathon.sehad.mapper.RequestToAuthModel;
import com.makeathon.sehad.models.db.Authentication;
import com.makeathon.sehad.models.payload.Profile;
import com.makeathon.sehad.models.signin.AuthenticationRequest;
import com.makeathon.sehad.models.signin.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RegisterService {

    @Value("${profile_url}")
    private String PROFILE_URI;
    @Value("${error_status}")
    private Integer FORBIDDEN;
    @Value("${email_exists}")
    private String EMAIL_EXISTS;

    @Autowired
    private AuthDao authDao;
    @Autowired
    private LogInService logInService;
    @Autowired
    private RequestToAuthModel mapper;
    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<AuthenticationResponse> addToAuthentication(Profile request) {
        String email = request.getEmail();
        if (authDao.findByEmail(email) != null) {
            return ResponseEntity.status(FORBIDDEN).body(new AuthenticationResponse(EMAIL_EXISTS));
        }

        Authentication auth = new Authentication();
        mapper.mapToAuthModel(auth, request);
        Authentication savedAuth = authDao.saveAndFlush(auth);
        request.setUserId(savedAuth.getUserId());

        return addToProfile(request, savedAuth);
    }

    private String generateJwtToken(Authentication savedAuth) {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername(savedAuth.getEmail());
        request.setPassword(savedAuth.getPassword());
        return logInService.generateJWT(request);
    }

    private HttpHeaders getHeaders(String token) {
        String jwtToken = "Bearer " + token;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.AUTHORIZATION, jwtToken);
        return headers;
    }


    private ResponseEntity<AuthenticationResponse> addToProfile
            (Profile request, Authentication auth) {

        final String jwtToken = generateJwtToken(auth);

        if (jwtToken != null && !jwtToken.isEmpty()) {
            HttpHeaders headers = getHeaders(jwtToken);
            HttpEntity<?> profileHttpEntity = new HttpEntity<>(request, headers);
            System.out.println("Connecting to: " + PROFILE_URI);
            ResponseEntity<String> response = restTemplate.exchange(PROFILE_URI,
                    HttpMethod.POST, profileHttpEntity, String.class);
        }
        return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
    }
}
