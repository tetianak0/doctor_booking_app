package doctorBookingApp.tests;

import doctorBookingApp.controller.AuthController;
import doctorBookingApp.dto.usersDTO.UserDTO;
import doctorBookingApp.entity.enums.Role;
import doctorBookingApp.exeption.RestException;
import doctorBookingApp.security.dto.AuthRequest;
import doctorBookingApp.security.dto.AuthResponse;
import doctorBookingApp.security.service.JwtTokenProvider;
import doctorBookingApp.service.userServices.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAuthentication() {
        // Arrange
        AuthRequest authRequest = new AuthRequest("user@example.com", "password");
        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("user@example.com");
        when(jwtTokenProvider.createToken("user@example.com", Role.PATIENT)).thenReturn("token");

        HttpServletResponse response = mock(HttpServletResponse.class);

        // Act
        ResponseEntity<AuthResponse> responseEntity = authController.authentication(authRequest, response);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("token", responseEntity.getBody().getToken());

        Cookie expectedCookie = new Cookie("Access-Token", "token");
        expectedCookie.setPath("/");
        expectedCookie.setHttpOnly(true);
        verify(response).addCookie(expectedCookie);
    }

    @Test
    void testProfile() throws RestException {
        // Arrange
        String userEmail = "user@example.com";
        UserDTO userDTO = new UserDTO();
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(userEmail);
        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authentication);
        when(userService.getUserByEmail(userEmail)).thenReturn(userDTO);

        // Act
        ResponseEntity<UserDTO> responseEntity = authController.profile();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userDTO, responseEntity.getBody());
    }

    @Test
    void testProfileNotFound() throws RestException {
        // Arrange
        String userEmail = "user@example.com";
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(userEmail);
        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authentication);
        when(userService.getUserByEmail(userEmail)).thenReturn(null);

        // Act
        ResponseEntity<UserDTO> responseEntity = authController.profile();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testProfileException() throws RestException {
        // Arrange
        String userEmail = "user@example.com";
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(userEmail);
        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authentication);
        when(userService.getUserByEmail(userEmail)).thenThrow(new RestException("Error"));

        // Act
        ResponseEntity<UserDTO> responseEntity = authController.profile();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void testLogout() {
        // Arrange
        HttpServletResponse response = mock(HttpServletResponse.class);

        // Act
        authController.logout(response);

        // Assert
        Cookie expectedCookie = new Cookie("Access-Token", null);
        expectedCookie.setPath("/");
        expectedCookie.setHttpOnly(true);
        expectedCookie.setMaxAge(0);
        verify(response).addCookie(expectedCookie);
    }
}
