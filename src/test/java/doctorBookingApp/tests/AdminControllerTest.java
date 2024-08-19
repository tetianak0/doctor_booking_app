package doctorBookingApp.tests;

import doctorBookingApp.controller.userControllers.AdminController;
import doctorBookingApp.dto.TimeSlotDTO;
import doctorBookingApp.dto.usersDTO.UserDTO;
import doctorBookingApp.entity.TimeSlot;
import doctorBookingApp.exeption.RestException;
import doctorBookingApp.service.bookingServices.TimeSlotService;
import doctorBookingApp.service.userServices.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AdminControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private TimeSlotService timeSlotService;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserById() throws RestException {
        // Подготовка данных
        Long userId = 1L;
        UserDTO expectedUser = new UserDTO();
        when(userService.getUserById(userId)).thenReturn(expectedUser);

        // Вызов метода
      //  UserDTO actualUser = adminController.getUserById(userId); //надо переписать візов метода из другого контроллера

        // Проверка результата
       // assertEquals(expectedUser, actualUser);
    }

    @Test
    void testUpdateUserById() throws RestException {
        // Подготовка данных
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();
        UserDTO updatedUser = new UserDTO();
        when(userService.editUser(userId, userDTO)).thenReturn(updatedUser);

        // Вызов метода
       // UserDTO actualUser = adminController.updateUserById(userId, userDTO); //надо переписать візов метода из другого контроллера

        // Проверка результата
        //assertEquals(updatedUser, actualUser);
    }

    @Test
    void testDeleteUserById() throws RestException {
        // Подготовка данных
        Long userId = 1L;

        // Вызов метода
        //adminController.deleteUserById(userId); //надо переписать візов метода из другого контроллера

        // Проверка результата (метод не возвращает значения, проверка осуществляется на основе вызова метода сервиса)
    }

    @Test
    void testAddTimeSlot() {
        // Подготовка данных
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO();
        TimeSlot timeSlot = new TimeSlot();
        when(timeSlotService.addTimeSlot(any(TimeSlotDTO.class))).thenReturn(timeSlot);

        // Вызов метода
        //TimeSlot actualTimeSlot = adminController.addTimeSlot(timeSlotDTO); //надо переписать візов метода из другого контроллера

        // Проверка результата
        //assertEquals(timeSlot, actualTimeSlot);
    }

    @Test
    void testUpdateTimeSlot() {
        // Подготовка данных
        Long timeSlotId = 1L;
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO();
        TimeSlot updatedTimeSlot = new TimeSlot();
        when(timeSlotService.updateTimeSlot(timeSlotId, timeSlotDTO)).thenReturn(updatedTimeSlot);

        // Вызов метода
       // TimeSlot actualTimeSlot = adminController.updateTimeSlot(timeSlotId, timeSlotDTO);//надо переписать візов метода из другого контроллера

        // Проверка результата
        //assertEquals(updatedTimeSlot, actualTimeSlot);
    }

    @Test
    void testDeleteTimeSlot() {
        // Подготовка данных
        Long timeSlotId = 1L;

        // Вызов метода
       // adminController.deleteTimeSlot(timeSlotId); //надо переписать візов метода из другого контроллера

        // Проверка результата (метод не возвращает значения, проверка осуществляется на основе вызова метода сервиса)
    }
}
