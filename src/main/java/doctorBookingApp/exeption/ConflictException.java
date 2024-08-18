package doctorBookingApp.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT) // Привязываем исключение к статусу 409 Conflict
public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }
}