package doctorBookingApp.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class RestException extends RuntimeException {

    private final HttpStatus status;

    public RestException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public RestException(Object p0, Object p1, HttpStatus status) {
        this.status = status;
    }

    public RestException(String error, HttpStatus status) {
        this.status = status;
    }

    public RestException(String userAlreadyExists) {
        this.status = HttpStatus.CONFLICT;
    }


    public HttpStatusCode getStatus() {
        return status;
    }
}
