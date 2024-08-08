package doctorBookingApp.service;

import doctorBookingApp.dto.DoctorProfileDTO;
import doctorBookingApp.entity.Department;
import doctorBookingApp.entity.DoctorProfile;
import doctorBookingApp.repository.DepartmentRepository;
import doctorBookingApp.repository.DoctorProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorProfileServiceImpl implements DoctorProfileService {

    private final DoctorProfileRepository doctorProfileRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public DoctorProfileServiceImpl(DoctorProfileRepository doctorProfileRepository, DepartmentRepository departmentRepository) {
        this.doctorProfileRepository = doctorProfileRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public DoctorProfileDTO addDoctorProfile(DoctorProfileDTO doctorProfileDTO) {
        DoctorProfile doctorProfile = convertToEntity(doctorProfileDTO);
        doctorProfile = doctorProfileRepository.save(doctorProfile);
        return convertToDTO(doctorProfile);
    }

    @Override
    public DoctorProfileDTO updateDoctorProfile(Long id, DoctorProfileDTO doctorProfileDTO) {
        DoctorProfile existingDoctorProfile = doctorProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DoctorProfile not found"));
        existingDoctorProfile.setFirstName(doctorProfileDTO.getFirstName());
        existingDoctorProfile.setLastName(doctorProfileDTO.getLastName());
        Department department = departmentRepository.findById(doctorProfileDTO.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));
        existingDoctorProfile.setDepartment(department);
        existingDoctorProfile.setSpecialization(doctorProfileDTO.getSpecialization());
        existingDoctorProfile.setExperienceYears(doctorProfileDTO.getExperienceYears());
        existingDoctorProfile.setReviewId(doctorProfileDTO.getReviewId());
        existingDoctorProfile = doctorProfileRepository.save(existingDoctorProfile);
        return convertToDTO(existingDoctorProfile);
    }

    @Override
    public void deleteDoctorProfile(Long id) {
        doctorProfileRepository.deleteById(id);
    }

    @Override
    public DoctorProfileDTO getDoctorProfileById(Long id) {
        DoctorProfile doctorProfile = doctorProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DoctorProfile not found"));
        return convertToDTO(doctorProfile);
    }

    @Override
    public List<DoctorProfileDTO> getAllDoctorProfiles() {
        return doctorProfileRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DoctorProfileDTO> findByDepartmentId(Long departmentId) {
        return doctorProfileRepository.findByDepartmentId(departmentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private DoctorProfile convertToEntity(DoctorProfileDTO doctorProfileDTO) {
        Department department = departmentRepository.findById(doctorProfileDTO.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        return DoctorProfile.builder()
                .firstName(doctorProfileDTO.getFirstName())
                .lastName(doctorProfileDTO.getLastName())
                .department(department)
                .specialization(doctorProfileDTO.getSpecialization())
                .experienceYears(doctorProfileDTO.getExperienceYears())
                .reviewId(doctorProfileDTO.getReviewId())
                .build();
    }

    private DoctorProfileDTO convertToDTO(DoctorProfile doctorProfile) {
        return DoctorProfileDTO.builder()
                .id(doctorProfile.getId())
                .firstName(doctorProfile.getFirstName())
                .lastName(doctorProfile.getLastName())
                .departmentId(doctorProfile.getDepartment().getId())
                .specialization(doctorProfile.getSpecialization())
                .experienceYears(doctorProfile.getExperienceYears())
                .reviewId(doctorProfile.getReviewId())
                .build();
    }
}