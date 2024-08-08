package doctorBookingApp.entity;
import doctorBookingApp.entity.enums.TypeOfInsurance;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@EqualsAndHashCode
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
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private DoctorProfile doctor;

    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    private TypeOfInsurance insurance;

    private Boolean isBooked;
}