package doctorBookingApp.entity;
import doctorBookingApp.entity.enums.TypeOfInsurance;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "time_slots")
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "doctor_id", referencedColumnName = "id", nullable = false)
    private DoctorProfile doctor;

    @NotNull
    private LocalDateTime dateTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TypeOfInsurance insurance;

    @NotNull
    private Boolean isBooked = false;


    @Version
    private Long version;

}
