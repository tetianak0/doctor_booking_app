package doctorBookingApp.controller;

import doctorBookingApp.dto.TimeSlotDTO;

import doctorBookingApp.dto.usersDTO.UserDTO;
import doctorBookingApp.entity.TimeSlot;
import doctorBookingApp.exeption.RestException;
import doctorBookingApp.service.TimeSlotService;

import doctorBookingApp.service.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private final UserService userService;

    @Autowired
    private final TimeSlotService timeSlotService;



    public AdminController(TimeSlotService timeSlotService, UserService userService) {
        this.timeSlotService = timeSlotService;
        this.userService = userService;
    }

//    // Поиск всех пользователей
//    @GetMapping("/users")
//    public ResponseEntity<List<UserDTO>> getAllUsers() {
//        List<UserDTO> users = userService.findAllUsers();
//        return ResponseEntity.ok(users);
//    }

    // Поиск пользователя по ID
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) throws RestException {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    // Обновление пользователя
    @PutMapping("/users/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) throws RestException {
        UserDTO updatedUser = userService.editUser(id, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    // Удаление пользователя
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws RestException {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    // Добавление таймслота
    @PostMapping("/timeslots")
    public ResponseEntity<TimeSlot> addTimeSlot(@RequestBody TimeSlotDTO timeSlotDTO) {
        TimeSlot addedTimeSlot = timeSlotService.addTimeSlot(timeSlotDTO);
        return ResponseEntity.ok(addedTimeSlot);
    }


    // Редактирование таймслота
    @PutMapping("/timeslots/{id}")
    public ResponseEntity<TimeSlot> editTimeSlot(@PathVariable Long id, @RequestBody TimeSlotDTO timeSlotDTO) throws RestException {
        TimeSlot updatedTimeSlot = timeSlotService.updateTimeSlot(id, timeSlotDTO);
        return ResponseEntity.ok(updatedTimeSlot);
    }

    // Удаление таймслота
    @DeleteMapping("/timeslots/{id}")
    public ResponseEntity<Void> deleteTimeSlot(@PathVariable Long id) throws RestException {
        timeSlotService.deleteTimeSlot(id);
        return ResponseEntity.noContent().build();
    }

    // Поиск пользователей по доктору
    @GetMapping("/users/doctor-profiles/{Id}")
    public ResponseEntity<List<UserDTO>> findUsersByDoctor(@PathVariable Long doctorId) throws RestException {
//        List<UserDTO> users = userService.findUsersByDoctor(doctorId);
//        return ResponseEntity.ok(users);
        return null;
    }

//    // Поиск пользователей по дате
//    @GetMapping("/users/search/date")
//    public ResponseEntity<List<UserDTO>> searchUsersByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
//        List<UserDTO> users = userService.findUsersByDate(date);
//        return ResponseEntity.ok(users);
//    }


//    // Поиск пользователей по страховке
//    @GetMapping("/users/search/insurance")
//    public ResponseEntity<List<UserDTO>> searchUsersByInsurance(@RequestParam String insurance) {
//        List<UserDTO> users = userService.findUsersByInsurance(insurance);
//        return ResponseEntity.ok(users);
//    }

}