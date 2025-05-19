package demo_springjwt.demo.api;

import demo_springjwt.demo.dto.UserDto;
import demo_springjwt.demo.entity.User;
import demo_springjwt.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class BaseController {

    @Autowired
    private UserRepository userRepository;

    protected Optional<UserDto> getCurrentUser() {
        Optional<User> user = this.getCurrentUserEntity();
        return Optional.of(UserDto.toDTO(user.get()));
    }

    protected Optional<User> getCurrentUserEntity() {
        Authentication userAuthentication = SecurityContextHolder.getContext().getAuthentication();
        if (userAuthentication == null) {
            return Optional.empty();
        }
        String principal = userAuthentication.getName();
        return this.userRepository.findByUsername(principal);

    }
}
