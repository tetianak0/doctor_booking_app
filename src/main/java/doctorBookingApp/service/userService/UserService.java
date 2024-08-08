package doctorBookingApp.service.userService;


import doctorBookingApp.dto.usersDTO.UserDTO;
import doctorBookingApp.entity.User;
import doctorBookingApp.exeption.RestException;
import doctorBookingApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;



    public UserDTO getUserById(Long userId) throws RestException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Пользователь с ID " + userId + " не найден."));
        return UserDTO.from(user);
    }


    public UserDTO getUserByEmail(String email) throws RestException {
        return UserDTO.from(userRepository.findByEmail(email)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Пользователь с email " + email + " не найден")));
    }


    public UserDTO getUserByPhoneNumber(String phoneNumber) throws RestException {
        return UserDTO.from(userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Пользователь с номером телефона " + phoneNumber + " не найден.")));
    }


    @Transactional
    public UserDTO editUser(Long userId, UserDTO userDTO) throws RestException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Пользователь с ID " + userId + " не найден."));
        user.setSurName(userDTO.getSurName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setEmail(userDTO.getEmail());
        userRepository.save(user);
        return UserDTO.from(user);

    }

    @Transactional
    public void deleteUserById(Long userId) throws RestException {
        if (!userRepository.existsById(userId)) {
            throw new RestException(HttpStatus.NOT_FOUND, "Пользователь с ID " + userId + " не найден.");
        }
        userRepository.deleteById(userId);
    }


    @Transactional
    public void deleteUserByEmail(String email) throws RestException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Пользователь с Email " + email + " не найден."));
        userRepository.deleteByEmail(user.getEmail());

    }

    @Transactional
    public void deleteUserByPhoneNumber(String phoneNumber) throws RestException {
        User user = userRepository.findByEmail(phoneNumber)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Пользователь с Email " + phoneNumber + " не найден."));
        userRepository.deleteByPhoneNumber(user.getPhoneNumber());

    }

}



