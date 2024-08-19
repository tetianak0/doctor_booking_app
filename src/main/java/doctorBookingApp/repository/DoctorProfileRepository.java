package doctorBookingApp.repository;


import doctorBookingApp.entity.DoctorProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorProfileRepository extends JpaRepository<DoctorProfile, Long> {
    List<DoctorProfile> findByDepartmentId(Long departmentId);
}