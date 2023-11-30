package com.tux.iam.controller;

import com.tux.iam.dto.AuthenticationRequestDTO;
import com.tux.iam.dto.AuthenticationResponseDTO;
import com.tux.iam.dto.RegisterRequestDTO;
import com.tux.iam.entity.Role;
import com.tux.iam.service.AuthenticationService;
import lombok.CustomLog;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@Log
@RequestMapping("/iam/v1/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDTO> authenticate(@RequestBody AuthenticationRequestDTO authenticationRequestDTO){
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequestDTO));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO registerRequestDTO) throws Exception {
        return ResponseEntity.ok(authenticationService.register(registerRequestDTO));
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validate(@RequestParam String token){
        return ResponseEntity.ok(authenticationService.validate(token));
    }
}
