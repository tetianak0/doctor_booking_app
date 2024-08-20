package doctorBookingApp.controller;

import doctorBookingApp.dto.DoctorProfileDTO;
import doctorBookingApp.service.DoctorProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor-profiles")

public class DoctorProfileController {

    private final DoctorProfileService doctorProfileService;

//    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorProfileController.class);

    @Autowired
    public DoctorProfileController(DoctorProfileService doctorProfileService) {
        this.doctorProfileService = doctorProfileService;
    }

    @Operation(summary = "Add a new doctor profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor profile successfully added",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json"))
    })

    @PostMapping
    public ResponseEntity<DoctorProfileDTO> addDoctorProfile(@Validated @RequestBody DoctorProfileDTO doctorProfileDTO) {
        return ResponseEntity.ok(doctorProfileService.addDoctorProfile(doctorProfileDTO));
    }


    @Operation(summary = "Update an existing doctor profile by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor profile successfully updated",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Doctor profile not found",
                    content = @Content(mediaType = "application/json"))
    })

    @PutMapping("/{id}")
    public ResponseEntity<DoctorProfileDTO> updateDoctorProfile(
            @PathVariable Long id,
            @Validated @RequestBody DoctorProfileDTO doctorProfileDTO) {
        return ResponseEntity.ok(doctorProfileService.updateDoctorProfile(id, doctorProfileDTO));
    }


    @Operation(summary = "Delete a doctor profile by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Doctor profile successfully deleted",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Doctor profile not found",
                    content = @Content(mediaType = "application/json"))
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctorProfile(@PathVariable Long id) {
        doctorProfileService.deleteDoctorProfile(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Retrieve a doctor profile by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor profile found",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Doctor profile not found",
                    content = @Content(mediaType = "application/json"))
    })

    @GetMapping("/{id}")
    public ResponseEntity<DoctorProfileDTO> getDoctorProfileById(@PathVariable Long id) {
        return ResponseEntity.ok(doctorProfileService.getDoctorProfileById(id));
    }


    @Operation(summary = "Retrieve all doctor profiles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of doctor profiles retrieved",
                    content = @Content(mediaType = "application/json"))
    })

    @GetMapping
    public ResponseEntity<List<DoctorProfileDTO>> getAllDoctorProfiles() {
        return ResponseEntity.ok(doctorProfileService.getAllDoctorProfiles());
    }

    /*@GetMapping(value="api/department/{departmentId}")//проверить
    public ResponseEntity<List<DoctorProfileDTO>> findByDepartmentId(@PathVariable Long departmentId) {
        return ResponseEntity.ok(doctorProfileService.findByDepartmentId(departmentId));
    }*/


    @Operation(summary = "Retrieve doctor profiles by department ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of doctor profiles for the department retrieved",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "No doctor profiles found for the given department ID",
                    content = @Content(mediaType = "application/json"))
    })

    @GetMapping("/department/{departmentId}")
    public List<DoctorProfileDTO> findByDepartmentId(@PathVariable Long departmentId) {
        return doctorProfileService.findByDepartmentId(departmentId);
    }

}
