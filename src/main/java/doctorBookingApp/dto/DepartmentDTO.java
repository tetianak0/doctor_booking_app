package doctorBookingApp.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentDTO {
    private Long id;

    @NotBlank(message = "Название обязательно для заполнения")
    @Size(max = 200, message = "Название должно быть не длиннее 200 символов")
    private String titleDepartment;
}