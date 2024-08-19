package doctorBookingApp.controller;

import doctorBookingApp.dto.DepartmentDTO;
import doctorBookingApp.dto.StandardResponseDto;
import doctorBookingApp.exeption.RestException;
import doctorBookingApp.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }



    @Operation(summary = "Add a new department", description = "Creates a new department entry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created department",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DepartmentDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })

    @PostMapping("/add") //работает
    public ResponseEntity<DepartmentDTO> addDepartment(@RequestBody DepartmentDTO departmentDTO) {
        DepartmentDTO createdDepartment = departmentService.addDepartment(departmentDTO);
        return ResponseEntity.ok(createdDepartment);
    }



    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        List<DepartmentDTO> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }



    @Operation(summary = "Удаление департамента по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Департамент удален."),
            @ApiResponse(responseCode = "404", description = "Департамент не найден.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class)))
    })

    @DeleteMapping("/department/{id}")
    public void deleteUserById(@PathVariable Long id) throws RestException {
        departmentService.deleteDepartmentById(id);
    }


}