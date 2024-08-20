package doctorBookingApp.repository;


import doctorBookingApp.entity.DoctorProfile;
import doctorBookingApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorProfileRepository extends JpaRepository<DoctorProfile, Long> {
    List<DoctorProfile> findByDepartmentId(Long departmentId);
    Optional<DoctorProfile> getDoctorProfileById(Long id);
}
