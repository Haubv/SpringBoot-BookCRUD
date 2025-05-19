package demo_springjwt.demo.service.impl;


import demo_springjwt.demo.entity.User;
import demo_springjwt.demo.repository.UserRepository;
import demo_springjwt.demo.response.Response;
import demo_springjwt.demo.security.UserPrincipal;
import demo_springjwt.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public User createUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public UserPrincipal findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        UserPrincipal userPrincipal = new UserPrincipal();
        if (user != null) {
            Set<String> authorities = new HashSet<>();
            userPrincipal.setId(user.get().getId());
            userPrincipal.setUsername(user.get().getUsername());
            userPrincipal.setPassword(user.get().getPassword());
            userPrincipal.setEmail(user.get().getEmail());
            if (null != user.get().getRoles())
                user.get().getRoles().forEach(r -> {
                    authorities.add(username);
                });
        }
        return userPrincipal;
    }

    @Override
    public boolean existsByUsername(String username) {
        if (userRepository.existsByUsername(username))
            return true;
        return false;
    }

    @Override
    public Response deleteById(long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return Response.build().message("Không tìm thấy user");
        } else {
            user.setDeleted(true);
            userRepository.save(user);
        }
        return Response.build().message("Deleted");
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
//		Optional<User> user = this.userRepository.findByUsername(username);
//        if (user == null) {
//            return null;
//        }
//        Set<String> authorities = new HashSet<>();
//        if (null != user.get().getRoles()) user.get().getRoles().forEach(r -> {
//            authorities.add(username);
//        });
//        // Get user detail
//        UserDetails userDetail = new UserPrin(user.get().getUsername()
//        		, user.get().getPassword()
//        		, Arrays.asList(new SimpleGrantedAuthority(authorities.stream().collect(Collectors.toList()).get(0))));
//		return userDetail;
        return null;
    }

    @Override
    public Response updateUser(long id, User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        User fromDB = userRepository.findById(id).orElse(null);
        if (fromDB == null) {
            return Response.build().message("Không tìm thấy user");
        }
        fromDB.setUsername(user.getUsername());
        fromDB.setPassword(user.getPassword());
        return Response.build().ok().data(userRepository.save(fromDB));

    }

    @Override
    public List<User> findAll() {
        List<User> user = userRepository.findAll();
        return user;
    }
//


//	@Override
//	public UserPrincipal findByUsername(String username) {
//		return null;
//	}

}
