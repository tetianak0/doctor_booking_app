package doctorBookingApp.repository;

import doctorBookingApp.entity.ConfirmationCode;
import doctorBookingApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;



public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    void deleteByEmail(String email);



    Optional<User> findByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumber(String phoneNumber);

    void deleteByPhoneNumber(String phoneNumber);



    Optional<User> findByTokenForResetPassword(String tokenForResetPassword);

    boolean existsByTokenForResetPassword(String tokenForResetPassword);



    Optional<User> findFirstByCodesContains(ConfirmationCode confirmationCode);




}
