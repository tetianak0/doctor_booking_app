package doctorBookingApp.controller;



import doctorBookingApp.security.dto.AuthRequest;
import doctorBookingApp.security.dto.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/auth")
public interface AuthApi {

    @PostMapping
    ResponseEntity<AuthResponse> authentication(@RequestBody AuthRequest request);
}
