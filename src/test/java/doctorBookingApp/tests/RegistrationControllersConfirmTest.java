package doctorBookingApp.tests;

import doctorBookingApp.controller.authenticationControllers.RegistrationControllers;
import doctorBookingApp.exeption.RestException;
import doctorBookingApp.service.authenticationServices.RegistrationUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

class RegistrationControllersConfirmTest {

    @Mock
    private RegistrationUserService registrationUserService;

    @InjectMocks
    private RegistrationControllers registrationControllers;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConfirmUserSuccess() throws RestException {
        // Arrange
        String confirmationCode = "validCode";
        doNothing().when(registrationUserService).confirmUser(confirmationCode);

        // Act
        ResponseEntity<?> response = registrationControllers.confirmUser(confirmationCode);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Der Benutzer wurde erfolgreich bestätigt.", response.getBody());
    }

    @Test
    void testConfirmUserFailure() throws RestException {
        // Arrange
        String confirmationCode = "invalidCode";
        doThrow(new RestException("Invalid or expired code"))
                .when(registrationUserService)
                .confirmUser(confirmationCode);

        // Act
        ResponseEntity<?> response = registrationControllers.confirmUser(confirmationCode);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Der Code wurde nicht gefunden oder ist abgelaufen.", response.getBody());
    }
}