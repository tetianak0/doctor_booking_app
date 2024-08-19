package doctorBookingApp.tests;

import doctorBookingApp.controller.userControllers.UserController;
import doctorBookingApp.dto.usersDTO.NewUserDTO;
import doctorBookingApp.dto.usersDTO.UserDTO;
import doctorBookingApp.exeption.RestException;
import doctorBookingApp.service.authenticationServices.RegistrationUserService;
//import doctorBookingApp.controller.authenticationControllers.RegistrationControllers;
import doctorBookingApp.service.userServices.UserService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private RegistrationUserService registrationUserService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Тест для регистрации пользователя
    @Test
    void registerUserTest() throws RestException, MessagingException {
        doNothing().when(registrationUserService).registrationUser(any(NewUserDTO.class));

        //ResponseEntity<String> response = userController.registerUser(new NewUserDTO()); //методы управления регистрацией в специальном контроллере

//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("Регистрация практически завершена. Проверьте свой электронный почтовый ящик на наличие кода подтверждения.", response.getBody());
    }

    // Тест для обработки RestException при регистрации
//    @Test
//    void registerUserExceptionTest() throws RestException, MessagingException {
//        doThrow(new RestException("Пользователь с таким email уже существует")).when(registrationUserService).registrationUser(any(NewUserDTO.class));
//
//        //ResponseEntity<String> response = userController.registerUser(new NewUserDTO()); //методы управления регистрацией в специальном контроллере
//
////        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
////        assertEquals("Пользователь с таким email уже существует", response.getBody());
//    }

    // Тест для получения пользователя по ID
    @Test
    void getUserByIdTest() throws RestException {
        UserDTO mockUser = new UserDTO();
        mockUser.setId(1L);
        when(userService.getUserById(1L)).thenReturn(mockUser);

        ResponseEntity<UserDTO> response = userController.getUserById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUser, response.getBody());
    }

    // Тест для обработки RestException при получении пользователя по ID
//    @Test
//    void getUserByIdNotFoundTest() throws RestException {
//        when(userService.getUserById(1L)).thenThrow(new RestException("Пользователь не найден"));
//
//        ResponseEntity<UserDTO> response = userController.getUserById(1L);
//
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        assertEquals("Пользователь не найден", response.getBody());
//    }
}
