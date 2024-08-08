package doctorBookingApp.service;

import doctorBookingApp.dto.DoctorProfileDTO;

import java.util.List;

public interface DoctorProfileService {
    DoctorProfileDTO addDoctorProfile(DoctorProfileDTO doctorProfileDTO);
    DoctorProfileDTO updateDoctorProfile(Long id, DoctorProfileDTO doctorProfileDTO);
    void deleteDoctorProfile(Long id);
    DoctorProfileDTO getDoctorProfileById(Long id);
    List<DoctorProfileDTO> getAllDoctorProfiles();
    List<DoctorProfileDTO> findByDepartmentId(Long departmentId);
}