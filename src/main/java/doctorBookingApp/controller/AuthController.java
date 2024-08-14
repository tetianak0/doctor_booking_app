package doctorBookingApp.controller;

import doctorBookingApp.entity.enums.Role;
import doctorBookingApp.security.dto.AuthRequest;
import doctorBookingApp.security.dto.AuthResponse;
import doctorBookingApp.security.service.CustomUserDetailsService;
import doctorBookingApp.security.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public ResponseEntity<AuthResponse> authentication(AuthRequest request) {
        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Set authentication in the context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Get the authenticated user's details
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Obtain the user's role from the UserDetails (assuming it's stored there)
        Role userRole = userDetails.getAuthorities().stream()
                .map(grantedAuthority -> Role.valueOf(grantedAuthority.getAuthority().replace("ROLE_", "")))
                .findFirst()
                .orElse(Role.PATIENT); // Default to PATIENT if no role found

        // Create a token
        String token = jwtTokenProvider.createToken(userDetails.getUsername(), userRole);

        // Return the token in the response
        return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);
    }
}
