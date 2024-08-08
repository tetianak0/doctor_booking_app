package doctorBookingApp.dto;
import doctorBookingApp.entity.enums.TypeOfInsurance;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeSlotDTO {

    private Long doctorId;
    private Long dateTime;
    private TypeOfInsurance insurance;
    private Boolean isBooked;
}