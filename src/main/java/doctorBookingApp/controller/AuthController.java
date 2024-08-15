package doctorBookingApp.controller;

import doctorBookingApp.dto.usersDTO.UserDTO;
import doctorBookingApp.entity.User;
import doctorBookingApp.entity.enums.Role;
import doctorBookingApp.exeption.RestException;
import doctorBookingApp.security.dto.AuthRequest;
import doctorBookingApp.security.dto.AuthResponse;
import doctorBookingApp.security.service.CustomUserDetailsService;
import doctorBookingApp.security.service.JwtTokenProvider;
import doctorBookingApp.service.userServices.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
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
//    private final CustomUserDetailsService customUserDetailsService;
    private final UserService userService;


    @Override
    public ResponseEntity<AuthResponse> authentication(AuthRequest request, HttpServletResponse response) {
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
        Cookie cookie = new Cookie("Access-Token", token);
        cookie.setPath("/");
        //http-only cookie
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        // Return the token in the response
        return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserDTO> profile() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName();
            System.out.println(userEmail);
            UserDTO userDTO = userService.getUserByEmail(userEmail);

            if (userDTO == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(userDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (RestException e) {
            throw new RuntimeException(e);
        }
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//           String userEmail =  authentication.getName();
//           System.out.println(userEmail);
//            return ResponseEntity.ok(userEmail);
    }

    public void logout(
           HttpServletResponse response
    ) {
        removeCookie(response);
    }
    private void removeCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("Access-Token", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }



}


