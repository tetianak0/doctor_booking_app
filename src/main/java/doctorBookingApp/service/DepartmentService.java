package doctorBookingApp.service;

import doctorBookingApp.dto.DepartmentDTO;
import doctorBookingApp.exeption.RestException;

import java.util.List;

public interface DepartmentService {
    DepartmentDTO addDepartment(DepartmentDTO departmentDTO);
    List<DepartmentDTO> getAllDepartments();
    void deleteDepartmentById(Long departmentId);
}