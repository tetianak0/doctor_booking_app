package doctorBookingApp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;


@Accessors
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "doctor_profile")
public class DoctorProfile {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotBlank
        @Column(length = 40)
        private String firstName;

        @NotBlank
        @Column(length = 50)
        private String lastName;

        @NotNull
        @ManyToOne
        @JoinColumn(name = "department_id", nullable = false)
        private Department department;

        @NotBlank
        @Column(length = 255)
        private String specialization;

        @NotNull
        @Column(length = 3)
        private Integer experienceYears;

        @Column(length = 50)
        private Integer reviewId;


        public void setDepartment(Department department) {
                this.department = department;
        }

        public Department getDepartment() {
                return department;
        }
}
