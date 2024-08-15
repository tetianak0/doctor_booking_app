package doctorBookingApp.controller;



import doctorBookingApp.dto.usersDTO.UserDTO;
import doctorBookingApp.security.dto.AuthRequest;
import doctorBookingApp.security.dto.AuthResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/auth")
public interface AuthApi {

    @PostMapping("/login")
    ResponseEntity<AuthResponse> authentication(@RequestBody AuthRequest request, HttpServletResponse response);

    @GetMapping("/profile")
    ResponseEntity<UserDTO> profile();

    @GetMapping("/logout")
    void logout(HttpServletResponse response);
}
