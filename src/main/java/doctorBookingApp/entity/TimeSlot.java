package doctorBookingApp.entity;
import doctorBookingApp.entity.enums.TypeOfInsurance;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    @JoinColumn(name = "doctor_id", referencedColumnName = "id", nullable = false)
    private DoctorProfile doctor;

    @NotBlank
    private LocalDateTime dateTime;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private TypeOfInsurance insurance;

    @NotBlank
    private Boolean isBooked = false;

    @NotBlank
    @Version
    private Long version;


}