package demo_springjwt.demo.api;

import demo_springjwt.demo.entity.ERole;
import demo_springjwt.demo.entity.Role;
import demo_springjwt.demo.entity.User;
import demo_springjwt.demo.exception.RoleNotFoundException;
import demo_springjwt.demo.repository.RoleRepository;
import demo_springjwt.demo.repository.UserRepository;
import demo_springjwt.demo.request.LoginRequest;
import demo_springjwt.demo.request.SignupRequest;
import demo_springjwt.demo.response.JwtResponse;
import demo_springjwt.demo.response.MessageResponse;
import demo_springjwt.demo.response.Response;
import demo_springjwt.demo.security.JwtUtil;
import demo_springjwt.demo.security.UserPrincipal;
import demo_springjwt.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtil jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserPrincipal userPrincipals = (UserPrincipal) authentication.getPrincipal();
        List<String> roles = userPrincipals.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userPrincipals.getId(),
                userPrincipals.getUsername(),
                userPrincipals.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        List<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RoleNotFoundException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RoleNotFoundException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RoleNotFoundException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RoleNotFoundException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/{id}")
    public Response updateUser(@PathVariable long id, @RequestBody User user) {
        return Response.build().ok().data(userService.updateUser(id, user));
    }

    @DeleteMapping("/{id}")
    public Response deleteUserById(@PathVariable long id) {
        return Response.build().ok().data(userService.deleteById(id));
    }

    @GetMapping("/{id}")
    public Response findByUsername(@PathVariable long id) {
        User user = userRepository.findById(id).orElse(null);
        assert user != null;
        if (!userService.existsByUsername(user.getUsername())) {
            return Response.build().ok().data(new MessageResponse("Error: User not found!"));
        } else {
            return Response.build().ok().data(userService.findByUsername(user.getUsername()));
        }
    }
}
