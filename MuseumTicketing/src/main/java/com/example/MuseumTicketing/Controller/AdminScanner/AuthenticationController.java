package com.example.MuseumTicketing.Controller.AdminScanner;


import com.example.MuseumTicketing.DTO.AdminScanner.JwtAuthenticationResponse;
import com.example.MuseumTicketing.DTO.AdminScanner.RefreshTokenRequest;
import com.example.MuseumTicketing.DTO.AdminScanner.SignInRequest;
import com.example.MuseumTicketing.DTO.AdminScanner.SignUpRequest;
import com.example.MuseumTicketing.Model.Users;
import com.example.MuseumTicketing.Service.AdminScanner.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping("/signup")
    public ResponseEntity<Users> signup(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authenticationService.signup(signUpRequest));
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest signInRequest){
        return ResponseEntity.ok(authenticationService.signin(signInRequest));
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }
}
