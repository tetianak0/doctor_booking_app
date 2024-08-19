package doctorBookingApp.dto;

import lombok.*;
import jakarta.validation.constraints.*;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorProfileDTO {

    private Long id;

    @NotBlank (message = "Поле 'Имя' обязательно для заполнения")
    @Size(max = 40, message = "Имя должно быть не длиннее 40 символов")
    private String firstName;

    @NotBlank(message = "Поле 'Фамилия' обязательно для заполнения")
    @Size(max = 50, message = "Фамилия должна быть не длиннее 50 символов")
    private String lastName;

    @NotNull(message = "Поле 'ID департамента' обязательно для заполнения")
    private Long departmentId;

    @NotBlank(message = "Поле 'Специализация' обязательно для заполнения")
    @Size(max = 255, message = "Специализация должна быть не длиннее 255 символов")
    private String specialization;

    @NotNull(message = "Поле 'Опыт работы' обязательно для заполнения")
    @Min(value = 0, message = "Количество лет опыта должно быть положительным")
    @Max(value = 100, message = "Количество лет опыта должно быть не больше 50")
    private Integer experienceYears;

    private Integer reviewId; // Это поле не обязательно для заполнения, потому нет валидации.
}