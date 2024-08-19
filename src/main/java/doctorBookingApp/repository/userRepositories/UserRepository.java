package doctorBookingApp.repository.userRepositories;

import doctorBookingApp.entity.ConfirmationCode;
import doctorBookingApp.entity.User;
import doctorBookingApp.entity.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//наследование от JpaRepository дает доступ к множеству встроенных методов для работы с базой данных.

@Repository
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


    List<User> findByRole(Role role);


}
