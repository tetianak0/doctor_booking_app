package doctorBookingApp.tests;

import doctorBookingApp.controller.authenticationControllers.RegistrationControllers;
import doctorBookingApp.dto.usersDTO.NewUserDTO;
import doctorBookingApp.exeption.RestException;
import doctorBookingApp.service.authenticationServices.RegistrationUserService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

class RegistrationControllersTest {

    @Mock
    private RegistrationUserService registrationUserService;

    @InjectMocks
    private RegistrationControllers registrationControllers;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUserSuccess() throws RestException, MessagingException {
        // Arrange
        NewUserDTO newUser = new NewUserDTO();
        doNothing().when(registrationUserService).registrationUser(newUser);

        // Act
        ResponseEntity<String> response = registrationControllers.registerUser(newUser);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Die Registrierung ist fast abgeschlossen. Bitte überprüfen Sie Ihr E-Mail-Postfach auf den Bestätigungscode.", response.getBody());
    }

    @Test
    void testRegisterUserConflict() throws RestException, MessagingException {
        // Arrange
        NewUserDTO newUser = new NewUserDTO();
        doThrow(new RestException("User already exists"))
                .when(registrationUserService)
                .registrationUser(newUser);

        // Act & Assert
        try {
            registrationControllers.registerUser(newUser);
        } catch (RestException e) {
            assertEquals("User already exists", e.getMessage());
        }
    }
}