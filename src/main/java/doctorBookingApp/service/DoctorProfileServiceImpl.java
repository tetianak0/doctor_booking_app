package doctorBookingApp.service;

import doctorBookingApp.dto.DoctorProfileDTO;
import doctorBookingApp.entity.Department;
import doctorBookingApp.entity.DoctorProfile;
import doctorBookingApp.exeption.RestException;
import doctorBookingApp.repository.DepartmentRepository;
import doctorBookingApp.repository.DoctorProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
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
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public DoctorProfileDTO addDoctorProfile(DoctorProfileDTO doctorProfileDTO) {
        DoctorProfile doctorProfile = convertToEntity(doctorProfileDTO);
        doctorProfile = doctorProfileRepository.save(doctorProfile);
        return convertToDTO(doctorProfile);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
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
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteDoctorProfile(Long id) {
        doctorProfileRepository.deleteById(id);
    }


    @Override
    @Transactional
    public DoctorProfileDTO getDoctorProfileById(Long id) throws RestException {
        DoctorProfile doctorProfile = doctorProfileRepository.findById(id)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "DoctorProfile not found"));
        return DoctorProfileDTO.from(doctorProfile);
    }


    @Override
    @Transactional
    public List<DoctorProfileDTO> getAllDoctorProfiles() {
        return doctorProfileRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /*@Override
    public List<DoctorProfileDTO> findByDepartmentId(Long departmentId) {
        return doctorProfileRepository.findByDepartmentId(departmentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }*/

    @Transactional
    public List<DoctorProfileDTO> findByDepartmentId(Long departmentId) {
        // Получаем список профилей врачей по идентификатору отдела
        List<DoctorProfile> doctorProfiles = doctorProfileRepository.findByDepartmentId(departmentId);

        // Проверяем, что список не пустой и не null
        if (doctorProfiles == null || doctorProfiles.isEmpty()) {
            return Collections.emptyList(); // Возвращаем пустой список, если ничего не найдено
        }

        // Преобразуем список сущностей в список DTO
        return doctorProfiles.stream()
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

    /*private DoctorProfileDTO convertToDTO(DoctorProfile doctorProfile) {
        return DoctorProfileDTO.builder()
                .id(doctorProfile.getId())
                .firstName(doctorProfile.getFirstName())
                .lastName(doctorProfile.getLastName())
                .departmentId(doctorProfile.getDepartment().getId())
                .specialization(doctorProfile.getSpecialization())
                .experienceYears(doctorProfile.getExperienceYears())
                .reviewId(doctorProfile.getReviewId())
                .build();
    }*/

    private DoctorProfileDTO convertToDTO(DoctorProfile doctorProfile) {
        // Конвертация из модели DoctorProfile в DTO
        DoctorProfileDTO dto = new DoctorProfileDTO();
        dto.setId(doctorProfile.getId());
        dto.setFirstName(doctorProfile.getFirstName());
        dto.setLastName(doctorProfile.getLastName());
        dto.setDepartmentId(doctorProfile.getDepartment().getId());
        // Добавьте другие поля по необходимости
        return dto;
    }
}
