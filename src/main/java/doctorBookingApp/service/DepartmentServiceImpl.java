package doctorBookingApp.service;

import doctorBookingApp.dto.DepartmentDTO;
import doctorBookingApp.entity.Department;
import doctorBookingApp.exeption.RestException;
import doctorBookingApp.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    @Transactional
    public DepartmentDTO addDepartment(DepartmentDTO departmentDTO) {
        Department department = new Department();
        department.setTitleDepartment(departmentDTO.getTitleDepartment());

        Department savedDepartment = departmentRepository.save(department);

        return new DepartmentDTO(savedDepartment.getId(), savedDepartment.getTitleDepartment());
    }


    @Override
    @Transactional
    @PreAuthorize("hasRole('PATIENT') or hasRole('ADMIN')")
    public List<DepartmentDTO> getAllDepartments() {
        // Получить все департаменты из базы данных
        List<Department> departments = departmentRepository.findAll();

        // Преобразовать сущности в DTO и вернуть список
        return departments.stream()
                .map(department -> new DepartmentDTO(department.getId(), department.getTitleDepartment()))
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public void deleteDepartmentById(Long departmentId) throws RestException {
        if (!departmentRepository.existsById(departmentId)) {
            throw new RestException(HttpStatus.NOT_FOUND, "Департамент с ID " + departmentId + " не найден.");
        }
        departmentRepository.deleteById(departmentId);
    }
}