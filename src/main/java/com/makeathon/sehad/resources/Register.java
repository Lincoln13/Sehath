package com.makeathon.sehad.resources;

import com.makeathon.sehad.models.payload.Profile;
import com.makeathon.sehad.services.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class Register {

    @Autowired
    private RegisterService registerService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Profile request) throws Exception {

        return registerService.addToAuthentication(request);

    }
}
