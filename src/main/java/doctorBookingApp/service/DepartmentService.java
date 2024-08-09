package doctorBookingApp.service;

import doctorBookingApp.dto.DepartmentDTO;

import java.util.List;

public interface DepartmentService {
    DepartmentDTO addDepartment(DepartmentDTO departmentDTO);
    List<DepartmentDTO> getAllDepartments();
}