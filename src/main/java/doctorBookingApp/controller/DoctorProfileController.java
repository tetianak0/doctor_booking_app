package doctorBookingApp.controller;


import doctorBookingApp.dto.DoctorProfileDTO;
import doctorBookingApp.service.DoctorProfileService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor-profiles")

public class DoctorProfileController {

    private final DoctorProfileService doctorProfileService;

    @Autowired
    public DoctorProfileController(DoctorProfileService doctorProfileService) {
        this.doctorProfileService = doctorProfileService;
    }

    @PostMapping
    public ResponseEntity<DoctorProfileDTO> addDoctorProfile(@Validated @RequestBody DoctorProfileDTO doctorProfileDTO) {
        return ResponseEntity.ok(doctorProfileService.addDoctorProfile(doctorProfileDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorProfileDTO> updateDoctorProfile(
            @PathVariable Long id,
            @Validated @RequestBody DoctorProfileDTO doctorProfileDTO) {
        return ResponseEntity.ok(doctorProfileService.updateDoctorProfile(id, doctorProfileDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctorProfile(@PathVariable Long id) {
        doctorProfileService.deleteDoctorProfile(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorProfileDTO> getDoctorProfileById(@PathVariable Long id) {
        return ResponseEntity.ok(doctorProfileService.getDoctorProfileById(id));
    }

    @GetMapping
    public ResponseEntity<List<DoctorProfileDTO>> getAllDoctorProfiles() {
        return ResponseEntity.ok(doctorProfileService.getAllDoctorProfiles());
    }

    @GetMapping(value="api/department/{departmentId}")
    public ResponseEntity<List<DoctorProfileDTO>> findByDepartmentId(@PathVariable Long departmentId) {
        return ResponseEntity.ok(doctorProfileService.findByDepartmentId(departmentId));
    }
    

}
