package doctorBookingApp.service;

import doctorBookingApp.dto.DepartmentDTO;
import doctorBookingApp.entity.Department;
import doctorBookingApp.repository.DepartmentRepository;
import doctorBookingApp.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public DepartmentDTO addDepartment(DepartmentDTO departmentDTO) {
        Department department = new Department();
        department.setTitleDepartment(departmentDTO.getTitleDepartment());

        Department savedDepartment = departmentRepository.save(department);

        return new DepartmentDTO(savedDepartment.getId(), savedDepartment.getTitleDepartment());
    }
}