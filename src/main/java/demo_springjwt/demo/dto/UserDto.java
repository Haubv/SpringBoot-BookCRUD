package demo_springjwt.demo.dto;

import demo_springjwt.demo.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private Long id;
    private String name;

    public static UserDto toDTO(User user) {
        UserDto result = new UserDto();
        result.setId(user.getId());
        result.setName(user.getUsername());
        return result;
    }
}