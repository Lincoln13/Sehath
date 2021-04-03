package com.makeathon.sehad.resources;

import com.makeathon.sehad.dao.AuthDao;
import com.makeathon.sehad.mapper.RequestToAuthModel;
import com.makeathon.sehad.models.payload.Profile;
import com.makeathon.sehad.models.signin.AuthenticationResponse;
import com.makeathon.sehad.services.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.makeathon.sehad.models.db.Authentication;
import org.springframework.web.client.RestTemplate;

@RestController
public class Register {

    @Value("${profile_url}")
    private String PROFILE_URI;
    @Value("${error_status}")
    private Integer FORBIDDEN;
    @Value("${email_exists}")
    private String EMAIL_EXISTS;

    @Autowired
    private AuthDao authDao;
    @Autowired
    private RequestToAuthModel mapper;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RegisterService registerService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Profile request) throws Exception {
        String email = request.getEmail();
        if (authDao.findByEmail(email) != null) {
            return ResponseEntity.status(FORBIDDEN).body(EMAIL_EXISTS);
        }

        Authentication auth = new Authentication();
        mapper.mapToAuthModel(auth, request);
        Authentication savedAuth = authDao.saveAndFlush(auth);
        request.setUserId(savedAuth.getUserId());

        final String jwtToken = registerService.generateJwtToken(savedAuth);

        if (jwtToken != null && !jwtToken.isEmpty()) {
            String token = "Bearer " + jwtToken;
            HttpHeaders headers = registerService.getHeaders(token);
            HttpEntity<?> profileHttpEntity = new HttpEntity<>(request, headers);
            System.out.println("Connecting to: " + PROFILE_URI);
            restTemplate.exchange(PROFILE_URI, HttpMethod.POST, profileHttpEntity, String.class);
        }
        return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
    }
}
