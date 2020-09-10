package epicentr.api;

import epicentr.entities.User;
import epicentr.helpers.JwtTokenUtil;
import epicentr.models.CreateOrder;
import epicentr.models.JwtRequest;
import epicentr.models.JwtResponse;
import epicentr.repositories.RoleRepository;
import epicentr.repositories.UserRepository;
import epicentr.services.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthAPI {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    public PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<String> Register(@RequestBody User user)
    {
        String pass = passwordEncoder.encode(user.getPassword());
        user.setPassword(pass);
        User u = new User();
        u.setPassword(pass);
        u.setEmail(user.getEmail());
        u.setName(user.getName());
        u.setLastName(user.getLastName());
        u.setCity(user.getCity());
        u.setPostOffice(user.getPostOffice());
        //Role r = roleRepository.findByName("ROLE_USER");
//        Role r = new Role();
//        r.setId(3);

        u.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        userRepository.save(u);
        //messageRepository.save(message);
        return ResponseEntity.ok("ok");
    }
    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody CreateOrder model) {
        User ushka = userRepository.findByEmail(model.getUsername());
        ushka.setRoles(null);
        ushka.setOrders(null);
        return ResponseEntity.ok(ushka);
    }
    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
