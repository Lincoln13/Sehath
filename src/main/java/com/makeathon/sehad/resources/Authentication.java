package com.makeathon.sehad.resources;

import com.makeathon.sehad.models.signin.AuthenticationRequest;
import com.makeathon.sehad.models.signin.AuthenticationResponse;
import com.makeathon.sehad.services.LogInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Authentication {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private LogInService login;

    /**
     * authenticate method
     * @param authenticationRequest payload in post body containing username and password
     * @return if username and password are valid, it sends back a token
     * @throws Exception if incorrect creds
     */
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody AuthenticationRequest authenticationRequest
    ) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        final String jwt = login.generateJWT(authenticationRequest);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @RequestMapping(value = "/validate/jwt", method = RequestMethod.GET)
    public ResponseEntity<?> validateJwt() {
        return ResponseEntity.ok("Validated");
    }
}
