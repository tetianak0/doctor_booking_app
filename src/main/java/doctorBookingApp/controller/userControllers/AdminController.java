package doctorBookingApp.controller.userControllers;

//import doctorBookingApp.dto.TimeSlotDTO;
//import doctorBookingApp.dto.usersDTO.UserDTO;
//import doctorBookingApp.entity.TimeSlot;
//import doctorBookingApp.service.bookingServices.TimeSlotService;
//import doctorBookingApp.service.userServices.UserService;
//import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin")
public class AdminController {

//    @Autowired
//    private final UserService userService;
//
//    @Autowired
//    private final TimeSlotService timeSlotService;
//
//
//    public AdminController(TimeSlotService timeSlotService, UserService userService) {
//        this.timeSlotService = timeSlotService;
//        this.userService = userService;
//    }

//    // Поиск всех пользователей
//    @GetMapping("/users")
//    public ResponseEntity<List<UserDTO>> getAllUsers() {
//        List<UserDTO> users = userService.findAllUsers();
//        return ResponseEntity.ok(users);
//    }


//    // Поиск пользователя по ID
//    @GetMapping("/users/{id}")//работает
//    public UserDTO getUserById(@PathVariable Long id) throws RestException {
//        return userService.getUserById(id);
//    }
//
//    // Обновление пользователя по ID
//    @PutMapping("/users/{id}")//работает
//    public UserDTO updateUserById(@PathVariable Long id, @RequestBody UserDTO userDTO) throws RestException {
//        return userService.editUser(id, userDTO);
//    }
//
//
//
//    @PostMapping("/timeslots")//ПРОВЕРЕН
//    public TimeSlot addTimeSlot(@RequestBody TimeSlotDTO timeSlotDTO) {
//        try {
//            return timeSlotService.addTimeSlot(timeSlotDTO);
//        } catch (Exception e) {
//            // В случае ошибки возвращаем null или пустое значение
//            return null;
//        }
//    }
//
//    @PutMapping("/timeslots/{id}")//ПРОВЕРЕН
//    public TimeSlot updateTimeSlot(@PathVariable Long id, @RequestBody TimeSlotDTO timeSlotDTO) {
//        try {
//            return timeSlotService.updateTimeSlot(id, timeSlotDTO);
//        } catch (Exception e) {
//
//            return null;
//        }
//    }
//
//    @DeleteMapping("/timeslots/{id}")//проверен
//    public void deleteTimeSlot(@PathVariable Long id) {
//        try {
//            timeSlotService.deleteTimeSlot(id);
//        } catch (Exception e) {
//            System.out.println("Error deleting time slot: ");
//        }
//    }



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