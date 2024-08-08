package doctorBookingApp.controller;


import doctorBookingApp.security.dto.AuthRequest;
import doctorBookingApp.security.dto.AuthResponse;
import doctorBookingApp.security.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;


    @Override
    public ResponseEntity<AuthResponse> authentication(AuthRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication.getName());

        return new ResponseEntity<>(new AuthResponse(jwt), HttpStatus.OK);

    }
}