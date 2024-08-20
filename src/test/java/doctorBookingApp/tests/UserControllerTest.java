package doctorBookingApp.tests;
import doctorBookingApp.controller.userControllers.UserController;
import doctorBookingApp.dto.usersDTO.UserDTO;
import doctorBookingApp.exeption.RestException;
import doctorBookingApp.service.userServices.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserById() throws RestException {
        // Arrange
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);
        userDTO.setEmail("user@example.com");

        when(userService.getUserById(anyLong())).thenReturn(userDTO);

        // Act
        ResponseEntity<UserDTO> responseEntity = userController.getUserById(userId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userDTO, responseEntity.getBody());
    }

    @Test
    void testGetUserByIdNotFound() throws RestException {
        // Arrange
        Long userId = 1L;

        when(userService.getUserById(anyLong())).thenReturn(null);

        // Act
        ResponseEntity<UserDTO> responseEntity = userController.getUserById(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testGetUserByEmail() throws RestException {
        // Arrange
        String email = "user@example.com";
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(email);

        when(userService.getUserByEmail(anyString())).thenReturn(userDTO);

        // Act
        ResponseEntity<UserDTO> responseEntity = userController.getUserByEmail(email);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userDTO, responseEntity.getBody());
    }

    @Test
    void testGetUserByEmailNotFound() throws RestException {
        // Arrange
        String email = "user@example.com";

        when(userService.getUserByEmail(anyString())).thenReturn(null);

        // Act
        ResponseEntity<UserDTO> responseEntity = userController.getUserByEmail(email);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testDeleteUserById() {
        // Arrange
        Long userId = 1L;

        // Act & Assert
        assertDoesNotThrow(() -> userController.deleteUserById(userId));

        // Verify
        verify(userService).deleteUserById(userId);
    }
}