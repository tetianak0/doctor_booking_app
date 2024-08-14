package doctorBookingApp.entity;

import doctorBookingApp.entity.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAppointment;

    private Long doctorId;

    private  Long userId;

    private Long timeSlotId;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;



}
